package sksa.aa.tweaker;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;

/**
 * Orchestrates the application of tweaks to the Android Auto phenotype database.
 * Handles threading, progress dialogs, and coordination between shell commands and UI updates.
 */
public class TweakManager {

    private final AppCompatActivity activity;
    private final TextView logsView;

    /**
     * Callback interface for tweak application results.
     */
    public interface TweakCallback {
        void onSuccess();
        void onFailure(String logs);
        default void onUiUpdate(Runnable action) {
            action.run();
        }
    }

    public TweakManager(AppCompatActivity activity, TextView logsView) {
        this.activity = activity;
        this.logsView = logsView;
    }

    /**
     * Apply a simple tweak with predefined SQL commands.
     *
     * @param tweakKey    The preference key for this tweak (e.g., "aa_activate_coolwalk")
     * @param sqlCommands Array of SQL commands to execute
     * @param callback    Callback for success/failure/updates
     */
    public void applyTweak(String tweakKey, String[] sqlCommands, TweakCallback callback) {
        final ProgressDialog dialog = ProgressDialog.show(activity, "", 
                activity.getString(R.string.tweak_loading), true);

        final TextView logs = logsView;
        TweakUtils.initiateLogsText(activity, logs);

        new Thread() {
            @Override
            public void run() {
                boolean suitableMethodFound = true;
                String path = activity.getApplicationInfo().dataDir;

                // Phase 1: Prepare
                TweakUtils.killps(activity, logs);
                String currentOwner = ShellUtils.runSuWithCmd(
                        "stat -c '%U' /data/data/com.google.android.gms/databases/phenotype.db"
                ).getInputStreamLog();
                String currentPolicy = TweakUtils.gainOwnership(activity, logs);

                // Phase 2: Execute SQL
                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append("DROP TRIGGER IF EXISTS ").append(tweakKey).append(";\n");
                for (String cmd : sqlCommands) {
                    sqlBuilder.append(cmd).append("\n");
                }

                TweakUtils.appendText(activity, logs, "\n\n--  run SQL method   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db '" +
                        sqlBuilder.toString().replace("'", "'\"'\"'") + "'"
                ).getStreamLogsWithLabels());

                // Phase 3: Create trigger
                String triggerSql = "CREATE TRIGGER " + tweakKey + " AFTER DELETE\n" +
                        "On FlagOverrides\n" +
                        "BEGIN\n" + String.join("\n", sqlCommands) + "\nEND;";

                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db '" +
                        triggerSql.replace("'", "'\"'\"'") + "'"
                ).getStreamLogsWithLabels());

                // Phase 4: Verify
                String verifyResult = ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " +
                        "'SELECT name FROM sqlite_master WHERE type='trigger' AND name='" + tweakKey + "';'"
                ).getInputStreamLog();

                if (verifyResult == null || verifyResult.trim().length() <= 4) {
                    suitableMethodFound = false;
                } else {
                    TweakUtils.appendText(activity, logs, "\n--  end SQL method   --");
                    TweakUtils.save(activity, true, tweakKey);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onSuccess();
                    });
                }

                // Phase 5: Cleanup
                dialog.dismiss();

                TweakUtils.appendText(activity, logs, "\n\n--  restoring Google Play Services   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        "pm enable com.google.android.gms"
                ).getStreamLogsWithLabels());

                TweakUtils.appendText(activity, logs, "\n\n--  Restoring ownership of the database   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        "chown " + currentOwner + " /data/data/com.google.android.gms/databases/phenotype.db"
                ).getStreamLogsWithLabels());

                if (currentPolicy.toLowerCase().equals("permissive")) {
                    TweakUtils.appendText(activity, logs, "\n\n--  Restoring SELINUX   --");
                    TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                            "setenforce 1"
                    ).getStreamLogsWithLabels());
                }

                if (!suitableMethodFound) {
                    final String logsText = logs.getText().toString();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onFailure(logsText);
                    });
                }
            }
        }.start();
    }

    /**
     * Apply a tweak with pre-commands (e.g., DELETE statements) that run before the main SQL.
     *
     * @param tweakKey      The preference key
     * @param preCommands   Commands to run before trigger creation (e.g., DELETE statements)
     * @param sqlCommands   Main SQL commands for the tweak
     * @param callback      Callback for results
     */
    public void applyTweakWithPreCommands(String tweakKey, String[] preCommands,
                                           String[] sqlCommands, TweakCallback callback) {
        final ProgressDialog dialog = ProgressDialog.show(activity, "",
                activity.getString(R.string.tweak_loading), true);

        final TextView logs = logsView;
        TweakUtils.initiateLogsText(activity, logs);

        new Thread() {
            @Override
            public void run() {
                boolean suitableMethodFound = true;
                String path = activity.getApplicationInfo().dataDir;

                // Phase 1: Prepare
                TweakUtils.killps(activity, logs);
                String currentOwner = ShellUtils.runSuWithCmd(
                        "stat -c '%U' /data/data/com.google.android.gms/databases/phenotype.db"
                ).getInputStreamLog();
                String currentPolicy = TweakUtils.gainOwnership(activity, logs);

                // Phase 2: Execute pre-commands and SQL
                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append("DROP TRIGGER IF EXISTS ").append(tweakKey).append(";\n");
                
                // Add pre-commands (e.g., DELETE statements)
                for (String preCmd : preCommands) {
                    sqlBuilder.append(preCmd).append("\n");
                }
                
                for (String cmd : sqlCommands) {
                    sqlBuilder.append(cmd).append("\n");
                }

                TweakUtils.appendText(activity, logs, "\n\n--  run SQL method   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db '" +
                        sqlBuilder.toString().replace("'", "'\"'\"'") + "'"
                ).getStreamLogsWithLabels());

                // Phase 3: Create trigger
                String triggerSql = "CREATE TRIGGER " + tweakKey + " AFTER DELETE\n" +
                        "On FlagOverrides\n" +
                        "BEGIN\n" + String.join("\n", sqlCommands) + "\nEND;";

                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db '" +
                        triggerSql.replace("'", "'\"'\"'") + "'"
                ).getStreamLogsWithLabels());

                // Phase 4: Verify
                String verifyResult = ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " +
                        "'SELECT name FROM sqlite_master WHERE type='trigger' AND name='" + tweakKey + "';'"
                ).getInputStreamLog();

                if (verifyResult == null || verifyResult.trim().length() <= 4) {
                    suitableMethodFound = false;
                } else {
                    TweakUtils.appendText(activity, logs, "\n--  end SQL method   --");
                    TweakUtils.save(activity, true, tweakKey);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onSuccess();
                    });
                }

                // Phase 5: Cleanup
                dialog.dismiss();

                TweakUtils.appendText(activity, logs, "\n\n--  restoring Google Play Services   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        "pm enable com.google.android.gms"
                ).getStreamLogsWithLabels());

                TweakUtils.appendText(activity, logs, "\n\n--  Restoring ownership of the database   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        "chown " + currentOwner + " /data/data/com.google.android.gms/databases/phenotype.db"
                ).getStreamLogsWithLabels());

                if (currentPolicy.toLowerCase().equals("permissive")) {
                    TweakUtils.appendText(activity, logs, "\n\n--  Restoring SELINUX   --");
                    TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                            "setenforce 1"
                    ).getStreamLogsWithLabels());
                }

                if (!suitableMethodFound) {
                    final String logsText = logs.getText().toString();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onFailure(logsText);
                    });
                }
            }
        }.start();
    }

    /**
     * Apply a tweak with an integer value parameter.
     *
     * @param tweakKey      The preference key
     * @param sqlCommands   SQL commands (may be generated dynamically)
     * @param value         The integer value to save
     * @param valuePrefKey  The preference key for the value
     * @param callback      Callback for results
     */
    public void applyTweakWithIntValue(String tweakKey, String[] sqlCommands, 
                                       int value, String valuePrefKey, 
                                       TweakCallback callback) {
        applyTweak(tweakKey, sqlCommands, new TweakCallback() {
            @Override
            public void onSuccess() {
                TweakUtils.saveValue(activity, value, valuePrefKey);
                callback.onSuccess();
            }

            @Override
            public void onFailure(String logs) {
                callback.onFailure(logs);
            }
        });
    }

    /**
     * Apply a tweak with a float/double value parameter.
     *
     * @param tweakKey      The preference key
     * @param sqlCommands   SQL commands
     * @param value         The float value to save
     * @param valuePrefKey  The preference key for the value
     * @param callback      Callback for results
     */
    public void applyTweakWithFloatValue(String tweakKey, String[] sqlCommands,
                                         float value, String valuePrefKey,
                                         TweakCallback callback) {
        applyTweak(tweakKey, sqlCommands, new TweakCallback() {
            @Override
            public void onSuccess() {
                TweakUtils.saveFloat(activity, value, valuePrefKey);
                callback.onSuccess();
            }

            @Override
            public void onFailure(String logs) {
                callback.onFailure(logs);
            }
        });
    }

    /**
     * Revert a tweak by removing its trigger.
     *
     * @param tweakKey The preference key / trigger name to revert
     */
    public void revertTweak(String tweakKey) {
        final TextView logs = logsView;
        TweakUtils.initiateLogsText(activity, logs);

        new Thread() {
            @Override
            public void run() {
                String path = activity.getApplicationInfo().dataDir;

                TweakUtils.save(activity, false, tweakKey);

                TweakUtils.appendText(activity, logs, "\n\n-- Reverting the hack  --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotypedb " +
                        "'DROP TRIGGER IF EXISTS " + tweakKey + "; DELETE FROM FlagOverrides;'"
                ).getStreamLogsWithLabels());
            }
        }.start();
    }

    /**
     * Apply the patched apps tweak with a dynamic whitelist.
     *
     * @param whiteListString Comma-separated list of package names
     * @param callback        Callback for results
     */
    public void applyPatchedAppsTweak(String whiteListString, TweakCallback callback) {
        String[] sqlCommands = TweakDefinitions.buildPatchedAppsSql(whiteListString);
        applyTweak("aa_patched_apps", sqlCommands, callback);
    }

    /**
     * Apply HUN duration tweak.
     *
     * @param durationMs Duration in milliseconds
     * @param callback   Callback for results
     */
    public void applyHunDurationTweak(int durationMs, TweakCallback callback) {
        String[] sqlCommands = { TweakDefinitions.buildHunDurationSql(durationMs) };
        applyTweakWithIntValue("aa_hun_ms", sqlCommands, durationMs, 
                "messaging_hun_value", callback);
    }

    /**
     * Apply media HUN duration tweak.
     *
     * @param durationMs Duration in milliseconds
     * @param callback   Callback for results
     */
    public void applyMediaHunDurationTweak(int durationMs, TweakCallback callback) {
        String[] sqlCommands = { TweakDefinitions.buildMediaHunDurationSql(durationMs) };
        applyTweakWithIntValue("aa_media_hun", sqlCommands, durationMs,
                "media_hun_value", callback);
    }

    /**
     * Apply USB bitrate tweak.
     *
     * @param multiplier Bitrate multiplier (e.g., 1.5 for 1.5x)
     * @param callback   Callback for results
     */
    public void applyUSBitrateTweak(double multiplier, TweakCallback callback) {
        String[] sqlCommands = TweakDefinitions.buildUSBitrateSql(multiplier);
        applyTweakWithFloatValue("aa_bitrate_usb", sqlCommands, (float) multiplier,
                "usb_bitrate_value", callback);
    }

    /**
     * Apply WiFi bitrate tweak.
     *
     * @param multiplier Bitrate multiplier (e.g., 1.5 for 1.5x)
     * @param callback   Callback for results
     */
    public void applyWiFiBitrateTweak(double multiplier, TweakCallback callback) {
        String[] sqlCommands = TweakDefinitions.buildWiFiBitrateSql(multiplier);
        applyTweakWithFloatValue("aa_bitrate_wireless", sqlCommands, (float) multiplier,
                "wifi_bitrate_value", callback);
    }

    /**
     * Apply force widescreen tweak with value-based trigger selection.
     * Drops conflicting triggers before applying.
     *
     * @param value    One of: 10 (portrait), 470 (widescreen), 1920 (no widescreen)
     * @param callback Callback for results
     */
    public void applyForceWideScreenTweak(int value, TweakCallback callback) {
        final ProgressDialog dialog = ProgressDialog.show(activity, "",
                activity.getString(R.string.tweak_loading), true);

        final TextView logs = logsView;
        TweakUtils.initiateLogsText(activity, logs);

        String[] sqlCommands = TweakDefinitions.getForceWideScreenSql(value);
        String triggerName = TweakDefinitions.getForceWideScreenTriggerName(value);

        new Thread() {
            @Override
            public void run() {
                boolean suitableMethodFound = true;
                String path = activity.getApplicationInfo().dataDir;

                // Phase 1: Prepare
                TweakUtils.killps(activity, logs);
                String currentOwner = ShellUtils.runSuWithCmd(
                        "stat -c '%U' /data/data/com.google.android.gms/databases/phenotype.db"
                ).getInputStreamLog();
                String currentPolicy = TweakUtils.gainOwnership(activity, logs);

                // Phase 2: Drop conflicting triggers and execute SQL
                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append("DROP TRIGGER IF EXISTS force_ws;\n");
                sqlBuilder.append("DROP TRIGGER IF EXISTS force_no_ws;\n");
                sqlBuilder.append("DROP TRIGGER IF EXISTS force_portrait;\n");
                for (String cmd : sqlCommands) {
                    sqlBuilder.append(cmd).append("\n");
                }

                TweakUtils.appendText(activity, logs, "\n\n--  run SQL method   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db '" +
                        sqlBuilder.toString().replace("'", "'\"'\"'") + "'"
                ).getStreamLogsWithLabels());

                // Phase 3: Create trigger with correct name
                String triggerSql = "CREATE TRIGGER " + triggerName + " AFTER DELETE\n" +
                        "On FlagOverrides\n" +
                        "BEGIN\n" + String.join("\n", sqlCommands) + "\nEND;";

                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db '" +
                        triggerSql.replace("'", "'\"'\"'") + "'"
                ).getStreamLogsWithLabels());

                // Phase 4: Verify
                String verifyResult = ShellUtils.runSuWithCmd(
                        path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " +
                        "'SELECT name FROM sqlite_master WHERE type='trigger' AND name='" + triggerName + "';'"
                ).getInputStreamLog();

                if (verifyResult == null || verifyResult.trim().length() <= 4) {
                    suitableMethodFound = false;
                } else {
                    TweakUtils.appendText(activity, logs, "\n--  end SQL method   --");
                    TweakUtils.save(activity, true, triggerName);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onSuccess();
                    });
                }

                // Phase 5: Cleanup
                dialog.dismiss();

                TweakUtils.appendText(activity, logs, "\n\n--  restoring Google Play Services   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        "pm enable com.google.android.gms"
                ).getStreamLogsWithLabels());

                TweakUtils.appendText(activity, logs, "\n\n--  Restoring ownership of the database   --");
                TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                        "chown " + currentOwner + " /data/data/com.google.android.gms/databases/phenotype.db"
                ).getStreamLogsWithLabels());

                if (currentPolicy.toLowerCase().equals("permissive")) {
                    TweakUtils.appendText(activity, logs, "\n\n--  Restoring SELINUX   --");
                    TweakUtils.appendText(activity, logs, ShellUtils.runSuWithCmd(
                            "setenforce 1"
                    ).getStreamLogsWithLabels());
                }

                if (!suitableMethodFound) {
                    final String logsText = logs.getText().toString();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onFailure(logsText);
                    });
                }
            }
        }.start();
    }
}
