package sksa.aa.tweaker;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Process-wide uncaught exception handler.
 *
 * Captures any crash on any thread (including activity lifecycle callbacks such
 * as MainActivity.onCreate, which run after SplashActivity's click handler has
 * already returned and therefore cannot be wrapped in a local try-catch). The
 * full stack trace is persisted to SharedPreferences so it survives the process
 * death, and SplashActivity surfaces it in a copyable dialog on the next launch.
 *
 * This is a diagnostic aid for reproducing on-device crashes (e.g. Android 16 /
 * OnePlus 13) without needing adb logcat.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    static final String PREFS = "crash_log";
    static final String KEY_TRACE = "last_trace";
    static final String KEY_THREAD = "last_thread";
    static final String KEY_TIME = "last_time";

    private final Context appContext;
    private final Thread.UncaughtExceptionHandler previousHandler;

    private CrashHandler(Context context, Thread.UncaughtExceptionHandler previous) {
        this.appContext = context.getApplicationContext();
        this.previousHandler = previous;
    }

    /** Installs the handler. Safe to call multiple times; only installs once. */
    public static void install(Context context) {
        Thread.UncaughtExceptionHandler current = Thread.getDefaultUncaughtExceptionHandler();
        if (current instanceof CrashHandler) {
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context, current));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));

            SharedPreferences prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            prefs.edit()
                    .putString(KEY_TRACE, sw.toString())
                    .putString(KEY_THREAD, thread.getName())
                    .putLong(KEY_TIME, System.currentTimeMillis())
                    .commit();
        } catch (Throwable ignored) {
            // Never let the crash reporter mask the original crash.
        }

        // Hand off to the platform handler so the process still dies normally.
        if (previousHandler != null) {
            previousHandler.uncaughtException(thread, throwable);
        } else {
            System.exit(2);
        }
    }
}
