package sksa.aa.tweaker;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import org.jpaste.exceptions.PasteException;
import org.jpaste.pastebin.Pastebin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sksa.aa.tweaker.CarRemoverActivity.CarRemover;
import sksa.aa.tweaker.Utils.BottomDialog;

@SuppressWarnings( "ALL" )
public class MainActivity extends AppCompatActivity {

    public static String appDirectory = new String();
    private static Context mContext;
    boolean suitableMethodFound;
    ProgressDialog progress;
    SharedPreferences accountsPrefs;
    private boolean temp;
    private ImageView noSpeedRestrictionsStatus;
    private ImageView taplimitstatus;
    private ImageView navstatus;
    private ImageView patchappstatus;
    private ImageView messageAutoReadStatus;
    private ImageView batteryOutlineStatus;
    private ImageView forceWideScreenStatus;
    private ImageView forcePortraitStatus;
    private ImageView messagesHunStatus;
    private ImageView mediaHunStatus;
    private ImageView intertialScrollStatus;
    private ImageView btstatus;
    private ImageView mdstatus;
    private ImageView batteryWarningStatus;
    private ImageView verticalBarStatus;
    private ImageView telemetryStatus;
    private ImageView forceNoWideScreenStatus;
    private ImageView usbBitrateStatus;
    private ImageView wifiBitrateStatus;
    private ImageView newSeekbarTweakStatus;
    private ImageView coolwalkTweakStatus;
    private ImageView nocoolwalkTweakStatus;
    private ImageView assistantTipsTweakStatus;
    private ImageView declineSmsTweakStatus;
    private ImageView uxprototypeTweakStatus;
    private ImageView materialYouTweakStatus;
    private TextView currentlySetHun;
    private TextView currentlySetMediaHun;
    private TextView currentlySetUSBSeekbar;
    private TextView currentlySetWiFiSeekbar;
    private Button rebootButton;
    private Button nospeed;
    private Button taplimitat;
    private Button coolwalkDayNightTweak;
    private Button patchapps;
    private Button messageAutoReadTweak;
    private Button batteryoutline;
    private Button forceNoWideScreen;
    private Button forceWideScreenButton;
    private Button forcePortrait;
    private Button messagesHunThrottling;
    private Button mediathrottlingbutton;
    private Button intertialScrollButton;
    private Button bluetoothoff;
    private Button mdbutton;
    private Button batteryWarning;
    private Button verticalBarTweakButton;
    private Button disableTelemetryButton;
    private Button tweakUSBBitrateButton;
    private Button tweakWiFiBitrateButton;
    private Button newSeekbarTweakButton;
    private Button coolwalkTweak;
    private Button nocoolwalkTweak;
    private Button deleteCarMode;
    private Button assistantTipsButton;
    private Button declineSmsTweak;
    private Button uxprototypeButton;
    private Button materialYouButton;
    private boolean animationRun;
    private boolean urlprototype;
    private URL url;
    private TweakManager tweakManager;


    public static Context getContext() {
        return mContext;
    }

    public static StreamLogs runSuWithCmd( String cmd ) {
        DataOutputStream outputStream = null;
        InputStream inputStream = null;
        InputStream errorStream = null;

        StreamLogs streamLogs = new StreamLogs();
        streamLogs.setOutputStreamLog( cmd );

        try {
            Process su = Runtime.getRuntime().exec( "su" );
            outputStream = new DataOutputStream( su.getOutputStream() );
            inputStream = su.getInputStream();
            errorStream = su.getErrorStream();
            outputStream.writeBytes( cmd + "\n" );
            outputStream.flush();

            outputStream.writeBytes( "exit\n" );
            outputStream.flush();

            try {
                su.waitFor();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            streamLogs.setInputStreamLog( readFully( inputStream ) );
            streamLogs.setErrorStreamLog( readFully( errorStream ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return streamLogs;
    }

    public static String readFully( InputStream is ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ( ( length = is.read( buffer ) ) != -1 ) {
            baos.write( buffer, 0, length );
        }
        return baos.toString( "UTF-8" );
    }

    public static void openApp( Context context, String packageName ) {
        if ( isAppInstalled( context, packageName ) ) if ( isAppEnabled( context, packageName ) ) {
            PackageManager pm = context.getPackageManager();
            Intent launchIntent = new Intent( "com.google.android.projection.gearhead.SETTINGS" );
            launchIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( launchIntent );
        } else
            Toast.makeText( context, context.getString( R.string.not_enabled_warning ), Toast.LENGTH_SHORT ).show();
        else
            Toast.makeText( context, context.getString( R.string.not_installed_warning ), Toast.LENGTH_SHORT ).show();
    }

    private static boolean isAppInstalled( Context context, String packageName ) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo( packageName, PackageManager.GET_ACTIVITIES );
            return true;
        } catch ( PackageManager.NameNotFoundException ignored ) {
        }
        return false;
    }

    private static boolean isAppEnabled( Context context, String packageName ) {
        Boolean appStatus = false;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo( packageName, 0 );
            if ( ai != null ) {
                appStatus = ai.enabled;
            }
        } catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
        return appStatus;
    }

    public static String replaceAll( StringBuilder builder, String from, String to ) {
        Pattern p = Pattern.compile( from );
        Matcher m = p.matcher( builder );
        StringBuffer sb = new StringBuffer();
        while ( m.find() ) {
            m.appendReplacement( sb, to );
        }
        m.appendTail( sb );
        return sb.toString();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );


        Bundle extras = new Bundle();

        try {
            extras = getIntent().getExtras();
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }


        final String path = getApplicationInfo().dataDir;
        appDirectory = path;
        loadStatus( path );


        if ( extras != null && extras.getString( "NewVersionName" ) != null ) {

            BottomDialog bd;

            final BottomDialog builder2 = new BottomDialog.Builder( this ).setTitle( R.string.new_version_available ).setContent( getString( R.string.go_to_new_version, extras.getString( "NewVersionName" ) ) ).setPositiveBackgroundColor( R.color.colorPrimary ).setPositiveText( R.string.go_to_download ).setNegativeText( R.string.ignore_for_now ).onPositive( new BottomDialog.ButtonCallback() {
                @Override
                public void onClick( @NonNull BottomDialog dialog ) {
                    startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "https://github.com/shmykelsa/AA-Tweaker/releases/" ) ) );
                }
            } ).onNegative( new BottomDialog.ButtonCallback() {
                @Override
                public void onClick( @NonNull BottomDialog dialog ) {

                }
            } ).setBackgroundColor( R.color.centercolor ).build();

            builder2.show();
        }


        setContentView( R.layout.activity_main );

        ImageView revertNotificationDuration = findViewById( R.id.revert_hun_throttling );
        ImageView revertMediaNotificationDuration = findViewById( R.id.revert_media_hun );
        ImageView revertWifiBitrate = findViewById( R.id.revert_bitrate_wifi );
        ImageView revertUsbBitrate = findViewById( R.id.revert_bitrate_usb );


        ViewPager viewPager = findViewById( R.id.viewpager );
        CommonPageAdapter adapter = new CommonPageAdapter();
        adapter.insertViewId( R.id.page_one );
        adapter.insertViewId( R.id.page_two );
        viewPager.setAdapter( adapter );


        Toolbar myToolbar = findViewById( R.id.toolbar );
        setSupportActionBar( myToolbar );
        Button toapp = findViewById( R.id.toapp_button );
        toapp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent( MainActivity.this, AppsList.class );
                startActivity( intent );
            }
        } );

        Button rebootbutton = findViewById( R.id.reboot_button );
        final DialogFragment rebootDialog = new RebootDialog();
        rebootbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                rebootDialog.show( getSupportFragmentManager(), "RebootDialog" );
            }
        } );

        rebootButton = findViewById( R.id.reboot_button );


        TextView logs = initiateLogsText();
        tweakManager = new TweakManager( this, logs );

        appendText( logs, runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT * FROM FlagOverrides;'" ).getStreamLogsWithLabels() );
        appendText( logs, runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT * FROM sqlite_master WHERE type='trigger';'" ).getStreamLogsWithLabels() );


        animationRun = false;
        final TextView upperTextView = findViewById( R.id.legend );
        upperTextView.setText( R.string.main_string );
        final AlphaAnimation legendAnim;
        legendAnim = new AlphaAnimation( 1.0f, 0.0f );
        legendAnim.setDuration( 100 );
        legendAnim.setRepeatCount( 1 );
        legendAnim.setRepeatMode( Animation.REVERSE );
        legendAnim.setAnimationListener( new Animation.AnimationListener() {

            @Override
            public void onAnimationStart( Animation animation ) {
            }

            @Override
            public void onAnimationEnd( Animation animation ) {
            }

            @Override
            public void onAnimationRepeat( Animation animation ) {
                if ( upperTextView.getText().toString().equals( getString( R.string.legend ) ) ) {
                    upperTextView.setText( R.string.main_string );
                } else {
                    upperTextView.setText( R.string.legend );
                }
            }
        } );


        Timer timer = new Timer();

        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        upperTextView.startAnimation( legendAnim );
                    }
                } );
            }
        }, 12000, 12000 );

/*        nospeed = findViewById(R.id.nospeed);
        noSpeedRestrictionsStatus = findViewById(R.id.speedhackstatus);
        if (load("aa_speed_hack")) {
            nospeed.setText(getString(R.string.re_enable_tweak_string) + getString(R.string.unlimited_scrolling_when_driving));
            changeStatus(noSpeedRestrictionsStatus, 2, false);
        } else {
            nospeed.setText(getString(R.string.disable_tweak_string) + getString(R.string.unlimited_scrolling_when_driving));
            changeStatus(noSpeedRestrictionsStatus, 0, false);
        }

        nospeed.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (load("aa_speed_hack")) {
                            revert("aa_speed_hack");
                            nospeed.setText(getString(R.string.disable_tweak_string) + getString(R.string.unlimited_scrolling_when_driving));
                            changeStatus(noSpeedRestrictionsStatus, 0, true);
                            showRebootButton();
                        } else {
                            patchforspeed(UserCount);
                        }
                    }
                });

        setOnLongClickListener(nospeed, R.string.tutorial_nospeed, R.drawable.tutorial_nospeed);*/


        taplimitat = findViewById( R.id.taplimit );
        taplimitstatus = findViewById( R.id.sixtapstatus );
        if ( load( "aa_six_tap" ) ) {
            taplimitat.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.disable_speed_limitations ) );
            changeStatus( taplimitstatus, 2, false );

        } else {
            taplimitat.setText( getString( R.string.disable_tweak_string ) + getString( R.string.disable_speed_limitations ) );
            changeStatus( taplimitstatus, 0, false );

        }

        setOnLongClickListener( taplimitat, R.string.tutorial_sixtap, R.drawable.tutorial_sixtap );


        taplimitat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_six_tap" ) ) {
                    revert( "aa_six_tap" );
                    taplimitat.setText( getString( R.string.disable_tweak_string ) + getString( R.string.disable_speed_limitations ) );
                    changeStatus( taplimitstatus, 0, true );
                    showRebootButton();
                } else {
                    patchfortouchlimit();
                }
            }
        } );

        coolwalkDayNightTweak = findViewById( R.id.coolwalkdaynighttweak );
        navstatus = findViewById( R.id.coolwalkdaynightstatus );
        if ( load( "coolwalk_daynight_tweak" ) ) {
            coolwalkDayNightTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.coolwalk_daynight_tweak ) );
            changeStatus( navstatus, 2, false );
        } else {
            coolwalkDayNightTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.coolwalk_daynight_tweak ) );
            changeStatus( navstatus, 0, false );
        }
        coolwalkDayNightTweak.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "coolwalk_daynight_tweak" ) ) {
                    revert( "coolwalk_daynight_tweak" );
                    coolwalkDayNightTweak.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.coolwalk_daynight_tweak ) );
                    changeStatus( navstatus, 0, true );
                    showRebootButton();
                } else {
                    coolwalkdaynightpatch();
                }
            }
        } );

        setOnLongClickListener( coolwalkDayNightTweak, R.string.coolwalk_daynight_tutorial, R.drawable.tutorial_coolwalkdaynight );

        patchapps = findViewById( R.id.patchapps );
        patchappstatus = findViewById( R.id.patchedappstatus );


        if ( load( "aa_patched_apps" ) ) {
            patchapps.setText( getString( R.string.unpatch ) + getString( R.string.patch_custom_apps ) );
            changeStatus( patchappstatus, 2, false );
        } else {
            patchapps.setText( getString( R.string.patch_app ) + getString( R.string.patch_custom_apps ) );
            changeStatus( patchappstatus, 0, false );
        }

        patchapps.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick( View view ) {
                if ( load( "aa_patched_apps" ) ) {
                    revert( "aa_patched_apps" );
                    patchapps.setText( getString( R.string.patch_app ) + getString( R.string.patch_custom_apps ) );
                    changeStatus( patchappstatus, 0, true );
                    showRebootButton();
                } else {
                    SharedPreferences appsListPref = getApplicationContext().getSharedPreferences( "appsListPref", 0 );
                    Map<String, ?> allEntries = appsListPref.getAll();
                    if ( allEntries.isEmpty() ) {
                        Intent intent = new Intent( MainActivity.this, AppsList.class );
                        startActivity( intent );
                        Toast.makeText( getApplicationContext(), getString( R.string.choose_apps_warning ), Toast.LENGTH_LONG ).show();
                    } else {
                        temp = true;
                        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( MainActivity.this );
                        builder.setTitle( getString( R.string.warning_title ) );
                        builder.setMessage( getResources().getString( R.string.warning_patch_apps ) );
                        builder.setNeutralButton( getString( android.R.string.ok ), new DialogInterface.OnClickListener() {
                            public void onClick( DialogInterface dialog, int which ) {
                                temp = false;
                                patchforapps();

                            }
                        } );
                        builder.setNegativeButton( android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick( DialogInterface dialog, int which ) {
                                dialog.dismiss();
                            }
                        } );
                        builder.setCancelable( false );
                        builder.show();

                    }
                }
            }
        } );

        setOnLongClickListener( patchapps, R.string.tutorial_patchapps );


        messageAutoReadTweak = findViewById( R.id.message_autoread_tweak_button );
        messageAutoReadStatus = findViewById( R.id.message_autoread_tweak_status );
        if ( load( "aa_message_autoread" ) ) {
            messageAutoReadTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.message_auto_read ) );
            changeStatus( messageAutoReadStatus, 2, false );

        } else {
            messageAutoReadTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.message_auto_read ) );
            changeStatus( messageAutoReadStatus, 0, false );
        }

        messageAutoReadTweak.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_message_autoread" ) ) {
                    revert( "aa_message_autoread" );
                    messageAutoReadTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.message_auto_read ) );
                    changeStatus( messageAutoReadStatus, 0, true );
                    showRebootButton();
                } else {
                    messageAutoRead();
                }
            }
        } );

        setOnLongClickListener( messageAutoReadTweak, R.string.tutorial_autoplay_message );


        uxprototypeButton = findViewById( R.id.uxprototypetweak );
        uxprototypeTweakStatus = findViewById( R.id.uxptototypestatus );
        if ( load( "uxprototype_tweak" ) ) {
            uxprototypeButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.uxprototype_tweak ) );
            changeStatus( uxprototypeTweakStatus, 2, false );

        } else {
            uxprototypeButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.uxprototype_tweak ) );
            changeStatus( uxprototypeTweakStatus, 0, false );
        }

        uxprototypeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {


                if ( load( "uxprototype_tweak" ) ) {
                    revert( "uxprototype_tweak" );
                    uxprototypeButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.uxprototype_tweak ) );
                    changeStatus( uxprototypeTweakStatus, 0, true );
                    showRebootButton();
                } else {
                    final Dialog uxprototypeDialog;
                    uxprototypeDialog = new Dialog( MainActivity.this );
                    uxprototypeDialog.setContentView( R.layout.dialog_layout );
                    uxprototypeDialog.setCancelable( false );


                    WindowManager.LayoutParams lp = setDialogLayoutParams( uxprototypeDialog );

                    final EditText readURL = uxprototypeDialog.findViewById( R.id.textuxprototype );
                    readURL.setVisibility( View.VISIBLE );

                    TextView acceptButton = uxprototypeDialog.findViewById( R.id.yes );
                    TextView cancelButton = uxprototypeDialog.findViewById( R.id.no );

                    acceptButton.setVisibility( View.VISIBLE );
                    cancelButton.setVisibility( View.VISIBLE );
                    acceptButton.setOnClickListener( new View.OnClickListener() {

                        public void onClick( View v ) {

                            String url = readURL.getText().toString();
                            if ( !url.contains( "http://" ) && !url.contains( "https://" ) ) {
                                url = "http://" + url;
                            }


                            try {
                                uxprototypeTweak( new URL( readURL.getText().toString() ) );
                                uxprototypeDialog.dismiss();
                            } catch ( MalformedURLException e ) {
                                e.printStackTrace();
                            }

                            if ( uxprototypeDialog.isShowing() ) {
                                Toast.makeText( uxprototypeDialog.getContext(), R.string.uxprototype_dialog, Toast.LENGTH_LONG ).show();
                            }


                        }
                    } );
                    cancelButton.setOnClickListener( new View.OnClickListener() {

                        public void onClick( View v ) {
                            uxprototypeDialog.dismiss();
                        }
                    } );
                    uxprototypeDialog.show();
                    uxprototypeDialog.getWindow().setAttributes( lp );

                }
            }


        } );


        setOnLongClickListener( uxprototypeButton, R.string.uxprototype_tutorial );


        materialYouButton = findViewById( R.id.materialyoutweak );
        materialYouTweakStatus = findViewById( R.id.materialyoutweakstatus );
        if ( load( "aa_material_you" ) ) {
            materialYouButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.materialyou_tweak ) );
            changeStatus( materialYouTweakStatus, 2, false );

        } else {
            materialYouButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.materialyou_tweak ) );
            changeStatus( materialYouTweakStatus, 0, false );
        }

        materialYouButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                if ( load( "uxprototype_tweak" ) ) {
                    revert( "uxprototype_tweak" );
                    materialYouButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.materialyou_tweak ) );
                    changeStatus( materialYouTweakStatus, 0, true );
                    showRebootButton();
                } else {
                    activateMaterialYou();
                }
            }


        } );


        setOnLongClickListener( materialYouButton, R.string.tutorial_materialyou, R.drawable.tutorial_materialyou );


        batteryoutline = findViewById( R.id.battoutline );
        batteryOutlineStatus = findViewById( R.id.batterystatus );
        if ( load( "aa_battery_outline" ) ) {
            batteryoutline.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.battery_outline_string ) );
            changeStatus( batteryOutlineStatus, 2, false );

        } else {
            batteryoutline.setText( getString( R.string.disable_tweak_string ) + getString( R.string.battery_outline_string ) );
            changeStatus( batteryOutlineStatus, 0, false );
        }

        batteryoutline.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_battery_outline" ) ) {
                    revert( "aa_battery_outline" );
                    batteryoutline.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.battery_outline_string ) );
                    changeStatus( batteryOutlineStatus, 0, true );
                    showRebootButton();
                } else {
                    battOutline();
                }
            }
        } );

        setOnLongClickListener( batteryoutline, R.string.tutorial_battery_outline, R.drawable.tutorial_outline );


        forceNoWideScreen = findViewById( R.id.force__no_ws_button );
        forceNoWideScreenStatus = findViewById( R.id.force_no_ws_status );


        forceWideScreenButton = findViewById( R.id.force_ws_button );
        forceWideScreenStatus = findViewById( R.id.force_ws_status );

        forcePortrait = findViewById( R.id.force_portrait_button );
        forcePortraitStatus = findViewById( R.id.force_portrait_status );


        if ( load( "force_ws" ) ) {
            forceWideScreenButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.force_widescreen_text ) );
            changeStatus( forceWideScreenStatus, 2, false );
        } else {
            forceWideScreenButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.force_widescreen_text ) );
            changeStatus( forceWideScreenStatus, 0, false );
        }

        forceWideScreenButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "force_ws" ) ) {
                    revert( "force_ws" );
                    forceWideScreenButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.force_widescreen_text ) );
                    changeStatus( forceWideScreenStatus, 0, true );
                    showRebootButton();
                } else {
                    forceWideScreen( view, 470 );
                    forceWideScreenButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.force_widescreen_text ) );
                    if ( load( "force_no_ws" ) || load( "force_portrait" ) ) {
                        Toast.makeText( getApplicationContext(), getString( R.string.force_disable_widescreen_warning ), Toast.LENGTH_LONG ).show();
                        revert( "force_no_ws" );
                        revert( "force_portrait" );
                        save( false, "force_no_ws" );
                        save( false, "force_portrait" );
                    }
                }
            }
        } );

        setOnLongClickListener( forceWideScreenButton, R.string.tutorial_widescreen, R.drawable.tutorial_widescreen );


        if ( load( "force_no_ws" ) ) {
            forceNoWideScreen.setText( getString( R.string.reset_tweak ) + getString( R.string.base_no_ws ) );
            changeStatus( forceNoWideScreenStatus, 2, false );

        } else {
            forceNoWideScreen.setText( getString( R.string.force_disable_tweak ) + getString( R.string.base_no_ws ) );
            changeStatus( forceNoWideScreenStatus, 0, false );
        }

        forceNoWideScreen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "force_no_ws" ) ) {
                    revert( "force_no_ws" );
                    forceNoWideScreen.setText( getString( R.string.force_disable_tweak ) + getString( R.string.base_no_ws ) );
                    changeStatus( forceNoWideScreenStatus, 0, true );
                    showRebootButton();
                } else {
                    forceWideScreen( view, 1920 );
                    forceNoWideScreen.setText( getString( R.string.reset_tweak ) + getString( R.string.base_no_ws ) );
                    if ( load( "force_portrait" ) || load( "force_ws" ) ) {
                        revert( "force_portrait" );
                        revert( "force_ws" );
                        save( false, "force_portrait" );
                        save( false, "force_ws" );
                    }
                }
            }
        } );

        setOnLongClickListener( forceNoWideScreen, R.string.tutorial_no_widescreen, R.drawable.tutorial_nowidescreen );

        if ( load( "force_portrait" ) ) {
            forcePortrait.setText( getString( R.string.reset_tweak ) + getString( R.string.portrait_layout ) );
            changeStatus( forcePortraitStatus, 2, false );
        } else {
            forcePortrait.setText( getString( R.string.enable_tweak_string ) + getString( R.string.portrait_layout ) );
            changeStatus( forcePortraitStatus, 0, false );
        }

        forcePortrait.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "force_portrait" ) ) {
                    revert( "force_portrait" );
                    forcePortrait.setText( getString( R.string.enable_tweak_string ) + getString( R.string.portrait_layout ) );
                    changeStatus( forcePortraitStatus, 0, true );
                    showRebootButton();
                } else {
                    forceWideScreen( view, 10 );
                    forcePortrait.setText( getString( R.string.disable_tweak_string ) + getString( R.string.portrait_layout ) );
                    if ( load( "force_no_ws" ) || load( "force_ws" ) ) {
                        Toast.makeText( getApplicationContext(), getString( R.string.force_disable_widescreen_warning ), Toast.LENGTH_LONG ).show();
                        revert( "force_no_ws" );
                        revert( "force_ws" );
                        save( false, "force_no_ws" );
                        save( false, "force_ws" );
                    }
                }
            }
        } );

        setOnLongClickListener( forcePortrait, R.string.tutorial_portrait, R.string.restricted_coolwalk, R.drawable.tutorial_portrait );

        messagesHunThrottling = findViewById( R.id.hunthrottlingbutton );
        final int[] messagesHunScrollbarValue = { 0 };
        final TextView displayValue = findViewById( R.id.seekbar_text );
        final SeekBar hunSeekbar = findViewById( R.id.messages_hun_seekbar );
        hunSeekbar.setProgress( 8000 );
        displayValue.setText( hunSeekbar.getProgress() + "ms" );
        hunSeekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                progress = ( (int) Math.round( progress / 100 ) ) * 100;
                seekBar.setProgress( progress );
                messagesHunScrollbarValue[0] = hunSeekbar.getProgress();
                displayValue.setText( hunSeekbar.getProgress() + "ms" );
                if ( hunSeekbar.getProgress() == 8000 ) {
                    messagesHunThrottling.setText( getString( R.string.reset_tweak ) + getString( R.string.set_notification_duration_to ) + getString( R.string.default_string ) );
                } else {
                    messagesHunThrottling.setText( getString( R.string.set_value ) + getString( R.string.set_notification_duration_to ) + " " + hunSeekbar.getProgress() + " ms" );
                }
            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
                displayValue.setText( hunSeekbar.getProgress() + "ms" );
                messagesHunThrottling.setText( getString( R.string.set_value ) + getString( R.string.set_notification_duration_to ) + " " + hunSeekbar.getProgress() + " ms" );
            }

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                messagesHunScrollbarValue[0] = hunSeekbar.getProgress();
                displayValue.setText( hunSeekbar.getProgress() + "ms" );
                if ( hunSeekbar.getProgress() == 8000 ) {
                    messagesHunThrottling.setText( getString( R.string.reset_tweak ) + getString( R.string.set_notification_duration_to ) + getString( R.string.default_string ) );
                } else {
                    messagesHunThrottling.setText( getString( R.string.set_value ) + getString( R.string.set_notification_duration_to ) + " " + hunSeekbar.getProgress() + " ms" );
                }
            }
        } );

        revertNotificationDuration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                hunSeekbar.setProgress( 8000 );
            }
        } );


        messagesHunStatus = findViewById( R.id.huntrottlingstatus );

        currentlySetHun = findViewById( R.id.notification_currently_set );
        if ( load( "aa_hun_ms" ) ) {
            messagesHunThrottling.setText( getString( R.string.reset_tweak ) + getString( R.string.set_notification_duration_to ) + getString( R.string.default_string ) );
            changeStatus( messagesHunStatus, 2, false );
            if ( loadValue( "messaging_hun_value" ) == 0 ) {
                try {
                    saveValue( Integer.parseInt( runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT DISTINCT intVal FROM Flags WHERE name='SystemUi__hun_default_heads_up_timeout_ms';'" ).getInputStreamLog() ), "messaging_hun_value" );
                } catch ( RuntimeException e ) {
                    e.printStackTrace();
                }
            }
            currentlySetHun.setText( getString( R.string.currently_set ) + loadValue( "messaging_hun_value" ) );
        } else {
            messagesHunThrottling.setText( getString( R.string.set_value ) + getString( R.string.set_notification_duration_to ) + "..." );
            changeStatus( messagesHunStatus, 0, false );
        }


        messagesHunThrottling.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( hunSeekbar.getProgress() == 8000 ) {
                    if ( load( "aa_hun_ms" ) ) {
                        revert( "aa_hun_ms" );
                        saveValue( 0, "messaging_hun_value" );
                        changeStatus( messagesHunStatus, 0, true );
                        currentlySetHun.setText( "" );
                        showRebootButton();
                    } else {
                        Toast.makeText( getApplicationContext(), getString( R.string.choose_value_first ), Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    setHunDuration( view, hunSeekbar.getProgress() );
                }
            }
        } );

        setOnLongClickListener( messagesHunThrottling, R.string.tutorial_hun, R.drawable.tutorial_hun );

        mediathrottlingbutton = findViewById( R.id.media_throttling_button );
        final int[] secondScrollBarStatus = { 0 };
        final TextView secondDisplayValue = findViewById( R.id.second_seekbar_text );
        final SeekBar mediaSeekbar = findViewById( R.id.media_hun_seekbar );
        mediaSeekbar.setProgress( 8000 );
        secondDisplayValue.setText( mediaSeekbar.getProgress() + "ms" );
        mediaSeekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                progress = ( (int) Math.round( progress / 1000 ) ) * 1000;
                mediaSeekbar.setProgress( progress );
                secondDisplayValue.setText( mediaSeekbar.getProgress() + "ms" );
                if ( mediaSeekbar.getProgress() == 8000 ) {
                    mediathrottlingbutton.setText( getString( R.string.reset_tweak ) + getString( R.string.media_notification_duration_to ) + getString( R.string.default_string ) );
                } else {
                    mediathrottlingbutton.setText( getString( R.string.set_value ) + getString( R.string.media_notification_duration_to ) + " " + mediaSeekbar.getProgress() + " ms" );
                }
            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
                secondDisplayValue.setText( mediaSeekbar.getProgress() + "ms" );
                mediathrottlingbutton.setText( getString( R.string.set_value ) + getString( R.string.media_notification_duration_to ) + " " + mediaSeekbar.getProgress() + " ms" );
            }

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                secondScrollBarStatus[0] = mediaSeekbar.getProgress();
                secondDisplayValue.setText( mediaSeekbar.getProgress() + "ms" );
                if ( mediaSeekbar.getProgress() == 8000 ) {
                    mediathrottlingbutton.setText( getString( R.string.reset_tweak ) + getString( R.string.media_notification_duration_to ) + getString( R.string.default_string ) );
                } else {
                    mediathrottlingbutton.setText( getString( R.string.set_value ) + getString( R.string.media_notification_duration_to ) + " " + mediaSeekbar.getProgress() + " ms" );
                }
            }
        } );

        revertMediaNotificationDuration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mediaSeekbar.setProgress( 8000 );
            }
        } );

        currentlySetMediaHun = findViewById( R.id.media_notification_currently_set );
        mediaHunStatus = findViewById( R.id.media_trhrottling_status );
        if ( load( "aa_media_hun" ) ) {
            mediathrottlingbutton.setText( getString( R.string.reset_tweak ) + getString( R.string.media_notification_duration_to ) + getString( R.string.default_string ) );
            changeStatus( mediaHunStatus, 2, false );
            if ( loadValue( "media_hun_value" ) == 0 ) {
                try {
                    saveValue( Integer.parseInt( runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT DISTINCT intVal FROM Flags WHERE name='SystemUi__media_hun_in_rail_widget_timeout_ms';'" ).getInputStreamLog() ), "media_hun_value" );
                } catch ( RuntimeException e ) {
                    e.printStackTrace();
                }
            }
            currentlySetMediaHun.setText( getString( R.string.currently_set ) + loadValue( "media_hun_value" ) );
        } else {
            mediathrottlingbutton.setText( getString( R.string.set_value ) + getString( R.string.media_notification_duration_to ) + "..." );
            changeStatus( mediaHunStatus, 0, false );
        }

        mediathrottlingbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_media_hun" ) ) {
                    if ( mediaSeekbar.getProgress() == 8000 ) {
                        revert( "aa_media_hun" );
                        saveValue( 0, "media_hun_value" );
                        changeStatus( mediaHunStatus, 0, true );
                        currentlySetMediaHun.setText( "" );
                    } else {
                        setMediaHunDuration( view, mediaSeekbar.getProgress() );
                    }
                    showRebootButton();
                } else {
                    setMediaHunDuration( view, mediaSeekbar.getProgress() );
                }
            }
        } );

        setOnLongClickListener( mediathrottlingbutton, R.string.tutorial_media_hun, R.drawable.tutorial_media_hun );

        intertialScrollButton = findViewById( R.id.inertial_scroll_button );



        /*final int[] calendarSeekbarStatus = {0};
        final TextView calendarSeekbarTextView = findViewById(R.id.calendar_days_seekbar_text);
        final SeekBar calendarSeekbar = findViewById(R.id.calendar_days_seekbar);
        calendarSeekbar.setProgress(1);
        calendarSeekbarTextView.setText("1");
        calendarSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                calendarSeekbar.setProgress(progress);
                calendarSeekbarTextView.setText(calendarSeekbar.getProgress() + "");
                if (progress == 1 || progress == 0) {
                    moreCalendarButton.setText(getString(R.string.calendar_tweak_single, calendarSeekbar.getProgress()));
                } else {
                    moreCalendarButton.setText(getString(R.string.calendar_tweak, calendarSeekbar.getProgress()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                calendarSeekbarTextView.setText(calendarSeekbar.getProgress() + "");
                moreCalendarButton.setText(getString(R.string.calendar_tweak, calendarSeekbar.getProgress()));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calendarSeekbarStatus[0] = calendarSeekbar.getProgress();
                calendarSeekbarTextView.setText(calendarSeekbar.getProgress() + "");
            }
        });

        revertCalendarDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarSeekbar.setProgress(1);
            }
        });*/


        intertialScrollStatus = findViewById( R.id.inertial_scroll_status );

        if ( load( "aa_inertial_scroll" ) ) {
            changeStatus( intertialScrollStatus, 2, false );
            intertialScrollButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.inertial_scroll_tweak ) );
        } else {
            intertialScrollButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.inertial_scroll_tweak ) );
            changeStatus( intertialScrollStatus, 0, false );
        }

        intertialScrollButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                if ( load( "aa_inertial_scroll" ) ) {
                    revert( "aa_inertial_scroll" );
                    changeStatus( intertialScrollStatus, 0, true );
                    showRebootButton();
                } else {
                    inertialScrollTweak();
                }
            }
        } );

        setOnLongClickListener( intertialScrollButton, R.string.tutorial_inertial_scroll );

        bluetoothoff = findViewById( R.id.bluetooth_disable_button );
        btstatus = findViewById( R.id.bt_disable_status );
        if ( load( "bluetooth_pairing_off" ) ) {
            bluetoothoff.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.bluetooth_auto_connect ) );
            changeStatus( btstatus, 2, false );
        } else {
            bluetoothoff.setText( getString( R.string.disable_tweak_string ) + getString( R.string.bluetooth_auto_connect ) );
            changeStatus( btstatus, 0, false );

        }

        verticalBarStatus = findViewById( R.id.vertical_bar_tweak_status );

        verticalBarTweakButton = findViewById( R.id.vertical_bar_tweak );
        if ( load( "aa_vertical_bar" ) ) {
            verticalBarTweakButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.vertical_bar_tweak ) );
            changeStatus( verticalBarStatus, 2, false );
        } else {
            verticalBarTweakButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.vertical_bar_tweak ) );
            changeStatus( verticalBarStatus, 0, false );
        }

        verticalBarTweakButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_vertical_bar" ) ) {
                    revert( "aa_vertical_bar" );
                    changeStatus( verticalBarStatus, 0, true );
                    showRebootButton();
                } else {
                    verticalBarTweak();
                }
            }
        } );

        setOnLongClickListener( verticalBarTweakButton, R.string.tutorial_vertical_bar_tweak );

        bluetoothoff.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "bluetooth_pairing_off" ) ) {
                    revert( "bluetooth_pairing_off" );
                    bluetoothoff.setText( getString( R.string.disable_tweak_string ) + getString( R.string.bluetooth_auto_connect ) );
                    changeStatus( btstatus, 0, true );
                    showRebootButton();
                } else {
                    forceNoBt();
                }
            }
        } );

        setOnLongClickListener( bluetoothoff, R.string.tutorial_bluetooth );


        mdbutton = findViewById( R.id.multi_display_button );
        mdstatus = findViewById( R.id.multi_display_status );
        if ( load( "multi_display" ) ) {
            mdbutton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.multi_display_string ) );
            changeStatus( mdstatus, 2, false );
        } else {
            mdbutton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.multi_display_string ) );
            changeStatus( mdstatus, 0, false );
        }

        mdbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "multi_display" ) ) {
                    revert( "multi_display" );
                    mdbutton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.multi_display_string ) );
                    changeStatus( mdstatus, 0, true );
                    showRebootButton();
                } else {
                    multiDisplay();
                }
            }
        } );

        setOnLongClickListener( mdbutton, R.string.tutorial_multidisplay, R.drawable.tutorial_md1, R.drawable.tutorial_md2, R.drawable.tutorial_md3 );

        batteryWarning = findViewById( R.id.battery_warning_button );
        batteryWarningStatus = findViewById( R.id.battery_warning_status );
        if ( load( "battery_saver_warning" ) ) {
            batteryWarning.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.battery_warning ) );
            changeStatus( batteryWarningStatus, 2, false );
        } else {
            batteryWarning.setText( getString( R.string.disable_tweak_string ) + getString( R.string.battery_warning ) );
            changeStatus( batteryWarningStatus, 0, false );
        }

        batteryWarning.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "battery_saver_warning" ) ) {
                    revert( "battery_saver_warning" );
                    batteryWarning.setText( getString( R.string.disable_tweak_string ) + getString( R.string.battery_warning ) );
                    changeStatus( batteryWarningStatus, 0, true );
                    showRebootButton();
                } else {
                    disableBatteryWarning();
                }
            }
        } );

        setOnLongClickListener( batteryWarning, R.string.tutorial_battery_saver_warning, R.drawable.tutorial_battery_saver );


        disableTelemetryButton = findViewById( R.id.telemetry_disable_tweak );
        telemetryStatus = findViewById( R.id.telemetry_disable_status );
        if ( load( "kill_telemetry" ) ) {
            disableTelemetryButton.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.telemetry_string ) );
            changeStatus( telemetryStatus, 2, false );
        } else {
            disableTelemetryButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.telemetry_string ) );
            changeStatus( telemetryStatus, 0, false );
        }

        disableTelemetryButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "kill_telemetry" ) ) {
                    revert( "kill_telemetry" );
                    disableTelemetryButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.telemetry_string ) );
                    changeStatus( telemetryStatus, 0, true );
                    showRebootButton();
                } else {
                    disableTelemetry();

                }
            }
        } );

        setOnLongClickListener( disableTelemetryButton, R.string.tutorial_telemetry );

        tweakUSBBitrateButton = findViewById( R.id.tweak_bitrate_usb );
        final int[] usbBitrateValue = { 0 };
        final TextView currentSeekbarUSB = findViewById( R.id.usb_bitrate_currently_set );
        final TextView toBeSetSeekbarUSB = findViewById( R.id.usb_bitrate_to_be_set );
        final SeekBar usbBitrateSeekbar = findViewById( R.id.usb_bitrate_seekbar );
        final Double[] valueUSB = new Double[1];
        usbBitrateSeekbar.setProgress( 10 );
        toBeSetSeekbarUSB.setText( "1.0" + "X" );
        usbBitrateSeekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                valueUSB[0] = ( Double.valueOf( progress ) / 10.0 );
                toBeSetSeekbarUSB.setText( valueUSB[0] + "X" );
                if ( usbBitrateSeekbar.getProgress() == 10 ) {
                    tweakUSBBitrateButton.setText( getString( R.string.reset_tweak ) + getString( R.string.set_usb_bitrate ) + " " + getString( R.string.default_string ) );
                    toBeSetSeekbarUSB.setText( valueUSB[0] + "X" );
                } else {
                    tweakUSBBitrateButton.setText( getString( R.string.set_value ) + getString( R.string.set_usb_bitrate ) + " " + valueUSB[0] + " X" );
                    toBeSetSeekbarUSB.setText( valueUSB[0] + "X" );
                }
            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
            }

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                if ( usbBitrateSeekbar.getProgress() == 10 ) {
                    tweakUSBBitrateButton.setText( getString( R.string.reset_tweak ) + getString( R.string.set_usb_bitrate ) + " " + getString( R.string.default_string ) );
                    toBeSetSeekbarUSB.setText( valueUSB[0] + "X" );
                } else {
                    tweakUSBBitrateButton.setText( getString( R.string.set_value ) + getString( R.string.set_usb_bitrate ) + " " + valueUSB[0] + " X" );
                    toBeSetSeekbarUSB.setText( valueUSB[0] + "X" );
                }
            }
        } );

        revertUsbBitrate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                usbBitrateSeekbar.setProgress( 10 );
            }
        } );


        usbBitrateStatus = findViewById( R.id.tweak_bitrate_usb_status );

        currentlySetUSBSeekbar = findViewById( R.id.usb_bitrate_currently_set );
        if ( load( "aa_bitrate_usb" ) ) {
            tweakUSBBitrateButton.setText( getString( R.string.reset_tweak ) + getString( R.string.set_usb_bitrate ) + " " + getString( R.string.default_string ) );
            changeStatus( usbBitrateStatus, 2, false );
            currentlySetUSBSeekbar.setText( getString( R.string.currently_set ) + loadFloat( "usb_bitrate_value" ) );
        } else {
            tweakUSBBitrateButton.setText( getString( R.string.set_value ) + getString( R.string.set_usb_bitrate ) + "..." );
            changeStatus( usbBitrateStatus, 0, false );
        }

        tweakUSBBitrateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( usbBitrateSeekbar.getProgress() == 10 ) {
                    if ( load( "aa_bitrate_usb" ) ) {
                        revert( "aa_bitrate_usb" );
                        saveFloat( 0, "usb_bitrate_value" );
                        changeStatus( usbBitrateStatus, 0, true );
                        currentlySetUSBSeekbar.setText( "" );
                        showRebootButton();
                    } else {
                        Toast.makeText( getApplicationContext(), getString( R.string.choose_value_first ), Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    setUSBbitrate( valueUSB[0] );

                }
            }
        } );

        setOnLongClickListener( tweakUSBBitrateButton, R.string.tutorial_bitrate );


        tweakWiFiBitrateButton = findViewById( R.id.tweak_bitrate_wifi );
        final int[] wifiBitrateValue = { 0 };
        final TextView currentSeekbarWiFi = findViewById( R.id.wifi_bitrate_currently_set );
        final TextView toBeSetSeekbarWiFi = findViewById( R.id.wifi_bitrate_to_be_set );
        final SeekBar WiFiBitrateSeekbar = findViewById( R.id.wifi_bitrate_seekbar );
        final Double[] valueWiFi = new Double[1];
        WiFiBitrateSeekbar.setProgress( 10 );
        toBeSetSeekbarWiFi.setText( "1.0" + "X" );
        WiFiBitrateSeekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                valueWiFi[0] = ( Double.valueOf( progress ) / 10.0 );
                toBeSetSeekbarWiFi.setText( valueWiFi[0] + "X" );
                if ( WiFiBitrateSeekbar.getProgress() == 10 ) {
                    tweakWiFiBitrateButton.setText( getString( R.string.reset_tweak ) + getString( R.string.set_wifi_tweak ) + " " + getString( R.string.default_string ) );
                } else {
                    tweakWiFiBitrateButton.setText( getString( R.string.set_value ) + getString( R.string.set_wifi_tweak ) + " " + valueWiFi[0] + " X" );
                }
            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
            }

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                if ( WiFiBitrateSeekbar.getProgress() == 10 ) {
                    tweakWiFiBitrateButton.setText( getString( R.string.reset_tweak ) + getString( R.string.set_wifi_tweak ) + " " + getString( R.string.default_string ) );
                } else {
                    tweakWiFiBitrateButton.setText( getString( R.string.set_value ) + getString( R.string.set_wifi_tweak ) + " " + valueWiFi[0] + " X" );
                }
            }
        } );

        setOnLongClickListener( tweakWiFiBitrateButton, R.string.tutorial_bitrate );

        revertWifiBitrate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                WiFiBitrateSeekbar.setProgress( 10 );
            }
        } );


        wifiBitrateStatus = findViewById( R.id.tweak_bitrate_wifi_status );
        currentlySetWiFiSeekbar = findViewById( R.id.wifi_bitrate_currently_set );
        if ( load( "aa_bitrate_wireless" ) ) {
            tweakWiFiBitrateButton.setText( getString( R.string.reset_tweak ) + getString( R.string.set_wifi_tweak ) + " " + getString( R.string.default_string ) );
            changeStatus( wifiBitrateStatus, 2, false );
            currentlySetWiFiSeekbar.setText( getString( R.string.currently_set ) + loadFloat( "wifi_bitrate_value" ) );
        } else {
            tweakWiFiBitrateButton.setText( getString( R.string.set_value ) + getString( R.string.set_wifi_tweak ) + "..." );
            changeStatus( wifiBitrateStatus, 0, false );
        }

        tweakWiFiBitrateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( WiFiBitrateSeekbar.getProgress() == 10 ) {
                    if ( load( "aa_bitrate_wireless" ) ) {
                        revert( "aa_bitrate_wireless" );
                        saveFloat( 0, "wifi_bitrate_value" );
                        changeStatus( wifiBitrateStatus, 0, true );
                        currentlySetWiFiSeekbar.setText( "" );
                        showRebootButton();
                    } else {
                        Toast.makeText( getApplicationContext(), getString( R.string.choose_value_first ), Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    setWiFiBitrate( valueWiFi[0] );

                }
            }
        } );


        newSeekbarTweakButton = findViewById( R.id.new_seekbar_tweak_button );
        newSeekbarTweakStatus = findViewById( R.id.newseekbar_tweak_status );


        if ( load( "aa_new_seekbar" ) ) {
            newSeekbarTweakButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.tappable_progress ) );
            changeStatus( newSeekbarTweakStatus, 2, false );
        } else {
            newSeekbarTweakButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.tappable_progress ) );
            changeStatus( newSeekbarTweakStatus, 0, false );
        }

        newSeekbarTweakButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_new_seekbar" ) ) {
                    revert( "aa_new_seekbar" );
                    newSeekbarTweakButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.tappable_progress ) );
                    changeStatus( newSeekbarTweakStatus, 0, true );
                    showRebootButton();
                } else {
                    newSeekbar();
                }
            }
        } );

        setOnLongClickListener( newSeekbarTweakButton, R.string.tutorial_new_seekbar );

        coolwalkTweak = findViewById( R.id.coolwalk_tweak_button );
        coolwalkTweakStatus = findViewById( R.id.coolwalk_tweak_status );
        nocoolwalkTweak = findViewById( R.id.nocoolwalk_tweak_button );
        nocoolwalkTweakStatus = findViewById( R.id.nocoolwalk_tweak_status );

        if ( load( "aa_activate_coolwalk" ) ) {
            coolwalkTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
            changeStatus( coolwalkTweakStatus, 2, false );
        } else {
            coolwalkTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
            changeStatus( coolwalkTweakStatus, 0, false );
        }

        coolwalkTweak.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                if ( load( "aa_activate_coolwalk" ) ) {

                    coolwalkTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
                    changeStatus( coolwalkTweakStatus, 0, true );
                    revert( "aa_activate_coolwalk" );
                } else {
                    if ( load( "aa_deactivate_coolwalk" ) ) {
                        revert( "aa_deactivate_coolwalk" );
                        nocoolwalkTweak.setText( getString( R.string.force_disable_tweak ) + getString( R.string.coolwalk_tweak ) );
                        changeStatus( nocoolwalkTweakStatus, 0, false );
                    }
                    activateCoolwalk();
                }

            }
        } );

        setOnLongClickListener( coolwalkTweak, R.string.tutorial_coolwalk, R.drawable.cw5, R.drawable.tutorial_coolwalk_1, R.drawable.tutorial_coolwalk_3 );


        if ( load( "aa_deactivate_coolwalk" ) ) {
            nocoolwalkTweak.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
            changeStatus( nocoolwalkTweakStatus, 2, false );
        } else {
            nocoolwalkTweak.setText( getString( R.string.force_disable_tweak ) + getString( R.string.coolwalk_tweak ) );
            changeStatus( nocoolwalkTweakStatus, 0, false );
        }

        nocoolwalkTweak.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                if ( load( "aa_deactivate_coolwalk" ) ) {

                    nocoolwalkTweak.setText( getString( R.string.force_disable_tweak ) + getString( R.string.coolwalk_tweak ) );
                    changeStatus( nocoolwalkTweakStatus, 2, true );
                    revert( "aa_deactivate_coolwalk" );
                } else {
                    if ( load( "aa_activate_coolwalk" ) ) {
                        revert( "aa_activate_coolwalk" );
                        coolwalkTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
                        changeStatus( coolwalkTweakStatus, 0, false );
                    }
                    deactivateCoolwalk();
                }

            }
        } );

        setOnLongClickListener( nocoolwalkTweak, R.string.tutorial_nocoolwalk );


        assistantTipsButton = findViewById( R.id.assistanttips_tweak_button );
        assistantTipsTweakStatus = findViewById( R.id.assistanttips_tweak_status );


        if ( load( "aa_activate_assistant_tips" ) ) {
            assistantTipsButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.assistant_tips ) );
            changeStatus( assistantTipsTweakStatus, 2, false );
        } else {
            assistantTipsButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.assistant_tips ) );
            changeStatus( assistantTipsTweakStatus, 0, false );
        }

        assistantTipsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_activate_assistant_tips" ) ) {
                    revert( "aa_activate_assistant_tips" );
                    assistantTipsButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.assistant_tips ) );
                    changeStatus( assistantTipsTweakStatus, 0, true );
                    showRebootButton();
                } else {
                    activateAssistantTips();
                }
            }
        } );

        setOnLongClickListener( assistantTipsButton, R.string.tutorial_assistant_suggestions );

        declineSmsTweak = findViewById( R.id.declinesms_tweak_button );
        declineSmsTweakStatus = findViewById( R.id.declinesms_tweak_status );


        if ( load( "aa_activate_declinesms" ) ) {
            declineSmsTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.decline_message_tweak ) );
            changeStatus( declineSmsTweakStatus, 2, false );
        } else {
            declineSmsTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.decline_message_tweak ) );
            changeStatus( declineSmsTweakStatus, 0, false );
        }

        declineSmsTweak.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if ( load( "aa_activate_declinesms" ) ) {
                    revert( "aa_activate_declinesms" );
                    declineSmsTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.decline_message_tweak ) );
                    changeStatus( declineSmsTweakStatus, 0, true );
                    showRebootButton();
                } else {
                    activatesmsdecline();
                }
            }
        } );

        setOnLongClickListener( declineSmsTweak, R.string.tutorial_decline_message );


        deleteCarMode = findViewById( R.id.car_remover );
        deleteCarMode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent( MainActivity.this, CarRemover.class );
                intent.putExtra( "path", path );
                startActivity( intent );
            }
        } );

        deleteCarMode.setOnLongClickListener( new View.OnLongClickListener() {
            public boolean onLongClick( View arg0 ) {
                final Dialog dialog = new Dialog( MainActivity.this );
                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
                dialog.setCanceledOnTouchOutside( true );
                dialog.setCancelable( true );
                View view = getLayoutInflater().inflate( R.layout.dialog_layout, null );

                TextView tutorial = view.findViewById( R.id.dialog_content );
                tutorial.setText( getString( R.string.tutorial_carremover ) );

                dialog.setContentView( view );

                dialog.show();

                Window window = dialog.getWindow();
                window.setLayout( ViewPager.LayoutParams.MATCH_PARENT, WRAP_CONTENT );

                return true;
            }
        } );

    }

    private void setOnLongClickListener( final Button button, final int... p ) {
        button.setOnLongClickListener( new View.OnLongClickListener() {
            public boolean onLongClick( View arg0 ) {

                final Dialog dialog = new Dialog( MainActivity.this );
                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
                dialog.setCanceledOnTouchOutside( true );
                dialog.setCancelable( true );
                View view = getLayoutInflater().inflate( R.layout.dialog_layout, null );

                TextView tutorial = view.findViewById( R.id.dialog_content );
                tutorial.setText( getString( p[0] ) );

                ImageView img1 = view.findViewById( R.id.tutorialimage1 );

                if ( p.length > 1 ) {
                    try {
                        img1.setImageDrawable( getDrawable( p[1] ) );
                    } catch ( Exception e ) {
                        tutorial.setText( getString( p[0] ) + getString( p[1] ) );
                        e.printStackTrace();
                    }
                }

                ImageView img2 = view.findViewById( R.id.tutorialimage2 );
                if ( p.length > 2 ) {
                    img2.setImageDrawable( getDrawable( p[2] ) );
                }

                ImageView img3 = view.findViewById( R.id.tutorialimage3 );
                if ( p.length > 3 ) {
                    img3.setImageDrawable( getDrawable( p[3] ) );
                }

                dialog.setContentView( view );

                dialog.show();

                Window window = dialog.getWindow();
                window.setLayout( ViewPager.LayoutParams.MATCH_PARENT, WRAP_CONTENT );

                return true;
            }
        } );
    }

    private void revert( final String toRevert ) {

        final TextView logs = initiateLogsText();


        new Thread() {
            @Override
            public void run() {
                String path = getApplicationInfo().dataDir;

                save( false, toRevert );

                appendText( logs, "\n\n-- Reverting the hack  --" );
                appendText( logs, runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'DROP TRIGGER IF EXISTS " + toRevert + "; DELETE FROM FlagOverrides;'\n" ).getStreamLogsWithLabels() );
            }


        }.start();


    }

    @Override
    public boolean onPrepareOptionsMenu( Menu menu ) {
        MenuItem item = menu.findItem( R.id.version );
        item.setTitle( "V." + BuildConfig.VERSION_NAME );
        return super.onPrepareOptionsMenu( menu );
    }

    public WindowManager.LayoutParams setDialogLayoutParams( Dialog dialog ) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( dialog.getWindow().getAttributes() );
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WRAP_CONTENT;
        return lp;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();
        if ( id == R.id.copy ) {
            final String title = "log";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy( policy );
            final URL[] string = { null };
            new Handler( Looper.getMainLooper() ).post( new Runnable() {
                @Override
                public void run() {
                    try {
                        final ClipboardManager clipboard = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE );
                        TextView textView = findViewById( R.id.logs );
                        URL newstring = Pastebin.pastePaste( BuildConfig.PASTEBIN_API_KEY, String.valueOf( textView.getText() ), title );
                        Toast.makeText( getApplicationContext(), getString( R.string.copied_pastebin ), Toast.LENGTH_LONG ).show();
                        ClipData clip = ClipData.newPlainText( "logs", newstring.toString() );
                        clipboard.setPrimaryClip( clip );
                    } catch ( PasteException e ) {
                        e.printStackTrace();
                        final ClipboardManager clipboard = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE );
                        TextView textView = findViewById( R.id.logs );
                        Toast.makeText( getApplicationContext(), getString( R.string.log_copied ), Toast.LENGTH_LONG ).show();
                        ClipData clip = ClipData.newPlainText( "logs", textView.getText() );
                        clipboard.setPrimaryClip( clip );
                    } catch ( RuntimeException e ) {
                        e.printStackTrace();
                        final ClipboardManager clipboard = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE );

                        Toast.makeText( getApplicationContext(), getString( R.string.log_copied ), Toast.LENGTH_LONG ).show();
                        Toast.makeText( getApplicationContext(), getString( R.string.log_copied ), Toast.LENGTH_LONG ).show();
                        TextView textView = findViewById( R.id.logs );

                        ClipData clip = ClipData.newPlainText( "logs", textView.getText() );
                        clipboard.setPrimaryClip( clip );
                    }
                }
            } );
        } else if ( id == R.id.about ) {
            DialogFragment aboutDialog = new AboutDialog();
            aboutDialog.show( getSupportFragmentManager(), "AboutDialog" );
        } else if ( id == R.id.revert_everything ) {
            final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( MainActivity.this );
            builder.setMessage( getString( R.string.revert_everything_dialog ) ).setPositiveButton( getString( android.R.string.ok ), new DialogInterface.OnClickListener() {
                public void onClick( DialogInterface dialog, int id ) {
                    getAndRemoveOptionsSelected();
                }
            } ).setNegativeButton( getString( android.R.string.cancel ), new DialogInterface.OnClickListener() {
                public void onClick( DialogInterface dialog, int id ) {
                    dialog.cancel();
                }
            } );
            builder.setCancelable( true );
            androidx.appcompat.app.AlertDialog Alert1 = builder.create();
            Alert1.show();
        } else if ( id == R.id.aa_settings ) {
            String packageName = "com.google.android.projection.gearhead";
            openApp( getApplicationContext(), packageName );
        } else {
            return super.onOptionsItemSelected( item );
        }
        return true;
    }

    public void save( final boolean isChecked, String key ) {
        SharedPreferences sharedPreferences = getPreferences( getContext().MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean( key, isChecked );
        editor.apply();
    }

    public void saveValue( final int value, String key ) throws RuntimeException {
        SharedPreferences sharedPreferences = getPreferences( getContext().MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt( key, value );
        editor.apply();
    }

    public void saveFloat( final float value, String key ) {
        SharedPreferences sharedPreferences = getPreferences( getContext().MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat( key, value );
        editor.apply();
    }

    public boolean load( String key ) {
        SharedPreferences sharedPreferences = getPreferences( Context.MODE_PRIVATE );
        return sharedPreferences.getBoolean( key, false );
    }

    public int loadValue( String key ) {
        SharedPreferences sharedPreferences = getPreferences( Context.MODE_PRIVATE );
        return sharedPreferences.getInt( key, 0 );
    }

    public float loadFloat( String key ) {
        SharedPreferences sharedPreferences = getPreferences( Context.MODE_PRIVATE );
        return sharedPreferences.getFloat( key, 0 );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    public void patchforapps() {

        if ( temp ) {
            return;
        }



        final ProgressDialog dialog = ProgressDialog.show( MainActivity.this, "", getString( R.string.tweak_loading ), true );


        new Thread() {
            @Override
            public void run() {
                final TextView logs = initiateLogsText();
                SharedPreferences appsListPref = getApplicationContext().getSharedPreferences( "appsListPref", 0 );

                Map<String, ?> allEntries = appsListPref.getAll();

                appendText( logs,  "--  Apps which will be added to whitelist: --\n" );

                String whiteListString = "";

                for ( Map.Entry<String, ?> entry : allEntries.entrySet() ) {

                appendText( logs,  "\t\t- " + entry.getValue() + " (" + entry.getKey() + ")\n" );

                whiteListString += "," + entry.getKey();

                
                String pathResult = runSuWithCmd( "pm path " + entry.getKey() ).getInputStreamLogWithLabel();

                String actualPath = pathResult.substring( pathResult.lastIndexOf( ":" ) + 1 );

                
                appendText( logs, runSuWithCmd( "mv " + actualPath + " /data/local/tmp/tmpapk" + entry.getKey() + ".apk" ).getStreamLogsWithLabels() );

                appendText( logs, runSuWithCmd( "pm uninstall " + entry.getKey() ).getStreamLogsWithLabels() );

                appendText( logs, runSuWithCmd( "pm install -t -i \"com.android.vending\" -r" + " /data/local/tmp/tmpapk" + entry.getKey() + ".apk" ).getStreamLogsWithLabels() );

                
                
                }

                
                whiteListString = whiteListString.replaceFirst( ",", "" );

                final String whiteListStringFinal = whiteListString;

                final StringBuilder finalCommand = new StringBuilder();

                
                
                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, stringVal, committed) VALUES (\'com.google.android.gms.car\',  0,\'app_white_list\', '',\'" );

                finalCommand.append( whiteListStringFinal );

                finalCommand.append( "\',0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, stringVal, committed) VALUES (\'com.google.android.gms.car\',  0,\'car_connect_broadcast_whitelist\', '',\'" );

                finalCommand.append( whiteListStringFinal );

                finalCommand.append( "\',0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, stringVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'AppValidation__allowed_package_list\',  '' ,'',0);" );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, stringVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'AppValidation__blocked_packages_by_installer\', '' ,'',0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'AppValidation__should_bypass_validation\', '' ,1,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'AppValidation__play_install_api\', '' ,0,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'AppValidation__swallow_play_api_exception\', '' ,1,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'AppValidation__swallow_play_api_exception_return_value\', '' ,1,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.gms.car\',  0,\'should_bypass_validation\', '' ,1,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'CarProjectionValidator__filter_disabled_packages_in_ispackageallowed_method\', '' ,0,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "INSERT OR REPLACE INTO FlagOverrides (packageName, flagType, name, user, boolVal, committed) VALUES (\'com.google.android.projection.gearhead\',  0,\'UnknownSources__allow_full_screen_apps\', '' ,1,0);" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                finalCommand.append( "DELETE FROM Flags WHERE name='app_black_list';" );

                finalCommand.append( "DELETE FROM Flags WHERE name='app_white_list';" );

                finalCommand.append( System.getProperty( "line.separator" ) );

                
                
                String path = getApplicationInfo().dataDir;
                suitableMethodFound = true;
                killps( logs );
                String currentOwner = runSuWithCmd( "stat -c \'%U\' /data/data/com.google.android.gms/databases/phenotype.db" ).getInputStreamLog();
                String currentPolicy = gainOwnership( logs );

                appendText( logs, "\n\n--  run SQL method   --" );
                appendText( logs, runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'DROP TRIGGER IF EXISTS aa_patched_apps;\n DROP TRIGGER IF EXISTS after_delete;\n" + "DROP TRIGGER IF EXISTS aa_patched_apps_fix;" + finalCommand + "'" ).getStreamLogsWithLabels() );

                try {
                    this.sleep( 5000 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }

                if ( suitableMethodFound ) {


                    appendText( logs, runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'CREATE TRIGGER aa_patched_apps AFTER DELETE\n" + "On FlagOverrides\n" + "BEGIN\n" + finalCommand + "END;'\n" ).getStreamLogsWithLabels() );
                    if ( runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT name FROM sqlite_master WHERE type='trigger' AND name='aa_patched_apps';'" ).getInputStreamLog().length() <= 4 ) {
                        suitableMethodFound = false;
                    } else {
                        appendText( logs, "\n--  end SQL method   --" );
                        save( true, "aa_patched_apps" );
                        new Handler( Looper.getMainLooper() ).post( new Runnable() {
                            @Override
                            public void run() {
                                changeStatus( patchappstatus, 1, true );
                                showRebootButton();
                                patchapps.setText( getString( R.string.unpatch ) + getString( R.string.patch_custom_apps ) );
                            }
                        } );
                    }
                }
                dialog.dismiss();

                appendText( logs, "\n\n--  restoring Google Play Services   --" );
                appendText( logs, runSuWithCmd( "pm enable com.google.android.gms" ).getStreamLogsWithLabels() );


                appendText( logs, "\n\n--  Restoring ownership of the database   --" );
                appendText( logs, runSuWithCmd( "chown " + currentOwner + " /data/data/com.google.android.gms/databases/phenotype.db" ).getStreamLogsWithLabels() );

                if ( currentPolicy.toLowerCase().equals( "permissive" ) ) {
                    appendText( logs, "\n\n--  Restoring SELINUX   --" );
                    appendText( logs, runSuWithCmd( "setenforce 1" ).getStreamLogsWithLabels() );
                }
                if ( !suitableMethodFound ) {
                    final DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "custom_apps" );
                    bundle.putString( "log", logs.getText().toString() );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            }
        }.start();
    }

    private String gainOwnership( final TextView logs ) {
        appendText( logs, "\n\n--  Gaining ownership of the database   --" );
        appendText( logs, runSuWithCmd( "chown root /data/data/com.google.android.gms/databases/phenotype.db" ).getStreamLogsWithLabels() );

        String currentPolicy = runSuWithCmd( "getenforce" ).getInputStreamLog();
        appendText( logs, "\n\n--  Setting SELINUX to permessive   --" );
        appendText( logs, runSuWithCmd( "setenforce 0" ).getStreamLogsWithLabels() );
        return currentPolicy;
    }

    public void messageAutoRead() {
        tweakManager.applyTweak( "aa_message_autoread", TweakDefinitions.MESSAGE_AUTOREAD_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( messageAutoReadStatus, 1, true );
                showRebootButton();
                messageAutoReadTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.message_auto_read ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_message_autoread" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void patchforspeed( int usercount ) {
        tweakManager.applyTweak( "aa_speed_hack", TweakDefinitions.SPEED_HACK_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( noSpeedRestrictionsStatus, 1, true );
                showRebootButton();
                nospeed.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.unlimited_scrolling_when_driving ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_speed_hack" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void multiDisplay() {
        tweakManager.applyTweak( "multi_display", TweakDefinitions.MULTI_DISPLAY_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( mdstatus, 1, true );
                showRebootButton();
                mdbutton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.multi_display_string ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "multi_display" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void patchfortouchlimit() {
        tweakManager.applyTweakWithPreCommands( "aa_six_tap",
            TweakDefinitions.SIX_TAP_PRE_COMMANDS,
            TweakDefinitions.SIX_TAP_ENABLE,
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( taplimitstatus, 1, true );
                    showRebootButton();
                    taplimitat.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.disable_speed_limitations ) );
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "aa_six_tap" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    public void coolwalkdaynightpatch() {
        tweakManager.applyTweak( "coolwalk_daynight_tweak", TweakDefinitions.COOLWALK_DAYNIGHT_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( navstatus, 1, true );
                showRebootButton();
                coolwalkDayNightTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.coolwalk_daynight_tweak ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "coolwalk_daynight_tweak" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void disableBatteryWarning() {
        tweakManager.applyTweak( "battery_saver_warning", TweakDefinitions.BATTERY_WARNING_DISABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( batteryWarningStatus, 1, true );
                showRebootButton();
                batteryWarning.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.battery_warning ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "battery_saver_warning" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void battOutline() {
        tweakManager.applyTweak( "aa_battery_outline", TweakDefinitions.BATTERY_OUTLINE_DISABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( batteryOutlineStatus, 1, true );
                showRebootButton();
                batteryoutline.setText( getString( R.string.disable_tweak_string ) + getString( R.string.battery_outline_string ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_battery_outline" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void activateCoolwalk() {
        // Also drop the deactivate trigger if it exists
        String[] sqlCommands = TweakDefinitions.COOLWALK_ENABLE;
        
        tweakManager.applyTweak( "aa_activate_coolwalk", sqlCommands, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                if ( load( "aa_activate_coolwalk" ) ) {
                    coolwalkTweak.setText( getString( R.string.enable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
                    changeStatus( coolwalkTweakStatus, 0, true );
                }
                changeStatus( coolwalkTweakStatus, 1, true );
                showRebootButton();
                coolwalkTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_activate_coolwalk" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void deactivateCoolwalk() {
        tweakManager.applyTweak( "aa_deactivate_coolwalk", TweakDefinitions.COOLWALK_DISABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( nocoolwalkTweakStatus, 1, true );
                showRebootButton();
                nocoolwalkTweak.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.coolwalk_tweak ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_deactivate_coolwalk" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void activateMaterialYou() {
        tweakManager.applyTweak( "aa_material_you", TweakDefinitions.MATERIAL_YOU_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( materialYouTweakStatus, 1, true );
                showRebootButton();
                materialYouButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.materialyou_tweak ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_material_you" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void activateAssistantTips() {
        tweakManager.applyTweak( "aa_activate_assistant_tips", TweakDefinitions.ASSISTANT_TIPS_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( assistantTipsTweakStatus, 1, true );
                showRebootButton();
                assistantTipsButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.assistant_tips ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_activate_assistant_tips" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void activatesmsdecline() {
        tweakManager.applyTweak( "aa_activate_declinesms", TweakDefinitions.DECLINE_SMS_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( declineSmsTweakStatus, 1, true );
                showRebootButton();
                declineSmsTweak.setText( getString( R.string.disable_tweak_string ) + getString( R.string.decline_message_tweak ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_activate_declinesms" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void newSeekbar() {
        tweakManager.applyTweak( "aa_new_seekbar", TweakDefinitions.NEW_SEEKBAR_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( newSeekbarTweakStatus, 1, true );
                showRebootButton();
                newSeekbarTweakButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.tappable_progress ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_new_seekbar" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void forceNoBt() {
        tweakManager.applyTweak( "bluetooth_pairing_off", TweakDefinitions.BLUETOOTH_AUTOCONNECT_DISABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( btstatus, 1, true );
                showRebootButton();
                bluetoothoff.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.bluetooth_auto_connect ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "bluetooth_pairing_off" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    @NonNull
    private TextView initiateLogsText() {
        final TextView logs = findViewById( R.id.logs );
        logs.setHorizontallyScrolling( true );
        logs.setMovementMethod( new ScrollingMovementMethod() );
        return logs;
    }

    public void disableTelemetry() {
        tweakManager.applyTweakWithPreCommands( "kill_telemetry", 
            TweakDefinitions.TELEMETRY_PRE_COMMANDS,
            TweakDefinitions.TELEMETRY_DISABLE, 
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( telemetryStatus, 1, true );
                    showRebootButton();
                    disableTelemetryButton.setText( getString( R.string.re_enable_tweak_string ) + getString( R.string.telemetry_string ) );
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "kill_telemetry" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    public void uxprototypeTweak( URL url ) {
        String[] sqlCommands = TweakDefinitions.buildUxPrototypeSql( url.toString() );
        tweakManager.applyTweak( "uxprototype_tweak", sqlCommands,
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( uxprototypeTweakStatus, 1, true );
                    showRebootButton();
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "uxprototype_tweak" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    public void setHunDuration( View view, final int value ) {
        String[] sqlCommands = new String[] { TweakDefinitions.buildHunDurationSql( value ) };
        tweakManager.applyTweakWithIntValue( "aa_hun_ms", sqlCommands, value, "messaging_hun_value",
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( messagesHunStatus, 1, true );
                    showRebootButton();
                    currentlySetHun.setText( getString( R.string.currently_set ) + value );
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "aa_hun_ms" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    public void setMediaHunDuration( View view, final int value ) {
        String[] sqlCommands = new String[] { TweakDefinitions.buildMediaHunDurationSql( value ) };
        tweakManager.applyTweakWithIntValue( "aa_media_hun", sqlCommands, value, "media_hun_value",
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( mediaHunStatus, 1, true );
                    showRebootButton();
                    currentlySetMediaHun.setText( getString( R.string.currently_set ) + value );
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "aa_media_hun" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    public void setUSBbitrate( final double value ) {
        String[] sqlCommands = TweakDefinitions.buildUSBBitrateSql( value );
        tweakManager.applyTweakWithPreCommands( "aa_bitrate_usb",
            TweakDefinitions.BITRATE_PRE_COMMANDS,
            sqlCommands,
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( usbBitrateStatus, 1, true );
                    showRebootButton();
                    saveFloat( (float) value, "usb_bitrate_value" );
                    currentlySetUSBSeekbar.setText( getString( R.string.currently_set ) + value );
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "aa_bitrate_usb" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    public void setWiFiBitrate( final double value ) {
        String[] sqlCommands = TweakDefinitions.buildWiFiBitrateSql( value );
        tweakManager.applyTweakWithPreCommands( "aa_bitrate_wireless",
            TweakDefinitions.BITRATE_PRE_COMMANDS,
            sqlCommands,
            new TweakManager.TweakCallback() {
                @Override
                public void onSuccess() {
                    changeStatus( wifiBitrateStatus, 1, true );
                    showRebootButton();
                    saveFloat( (float) value, "wifi_bitrate_value" );
                    currentlySetWiFiSeekbar.setText( getString( R.string.currently_set ) + value );
                }

                @Override
                public void onFailure( String logs ) {
                    DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString( "tweak", "aa_bitrate_wireless" );
                    bundle.putString( "log", logs );
                    notSuccessfulDialog.setArguments( bundle );
                    notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
                }
            } );
    }

    private void inertialScrollTweak() {
        tweakManager.applyTweak( "aa_inertial_scroll", TweakDefinitions.INERTIAL_SCROLL_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                changeStatus( intertialScrollStatus, 1, true );
                showRebootButton();
                intertialScrollButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.inertial_scroll_tweak ) );
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_inertial_scroll" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    private void verticalBarTweak() {
        tweakManager.applyTweak( "aa_vertical_bar", TweakDefinitions.VERTICAL_BAR_ENABLE, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                verticalBarTweakButton.setText( getString( R.string.disable_tweak_string ) + getString( R.string.vertical_bar_tweak ) );
                changeStatus( verticalBarStatus, 1, true );
                showRebootButton();
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", "aa_vertical_bar" );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    public void forceWideScreen( View view, final int value ) {
        tweakManager.applyForceWideScreenTweak( value, new TweakManager.TweakCallback() {
            @Override
            public void onSuccess() {
                switch ( value ) {
                    case 470:
                        forceNoWideScreen.setText( getString( R.string.force_disable_tweak ) + getString( R.string.base_no_ws ) );
                        forcePortrait.setText( getString( R.string.enable_tweak_string ) + getString( R.string.portrait_layout ) );
                        changeStatus( forceWideScreenStatus, 1, true );
                        changeStatus( forceNoWideScreenStatus, 0, false );
                        changeStatus( forcePortraitStatus, 0, false );
                        showRebootButton();
                        break;
                    case 1920:
                        forceWideScreenButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.base_no_ws ) );
                        forcePortrait.setText( getString( R.string.enable_tweak_string ) + getString( R.string.portrait_layout ) );
                        changeStatus( forceNoWideScreenStatus, 1, true );
                        changeStatus( forceWideScreenStatus, 0, false );
                        changeStatus( forcePortraitStatus, 0, false );
                        showRebootButton();
                        break;
                    case 10:
                        forceNoWideScreen.setText( getString( R.string.force_disable_tweak ) + getString( R.string.base_no_ws ) );
                        forceWideScreenButton.setText( getString( R.string.enable_tweak_string ) + getString( R.string.base_no_ws ) );
                        changeStatus( forcePortraitStatus, 1, true );
                        changeStatus( forceNoWideScreenStatus, 0, false );
                        changeStatus( forceWideScreenStatus, 0, false );
                        showRebootButton();
                        break;
                }
            }

            @Override
            public void onFailure( String logs ) {
                DialogFragment notSuccessfulDialog = new NotSuccessfulDialog();
                Bundle bundle = new Bundle();
                bundle.putString( "tweak", TweakDefinitions.getForceWideScreenTriggerName( value ) );
                bundle.putString( "log", logs );
                notSuccessfulDialog.setArguments( bundle );
                notSuccessfulDialog.show( getSupportFragmentManager(), "NotSuccessfulDialog" );
            }
        } );
    }

    private void killps( final TextView logs ) {
        appendText( logs, "\n\n--  Force stopping Google Play Services   --" );
        appendText( logs, runSuWithCmd( "am kill all com.google.android.gms" ).getStreamLogsWithLabels() );
    }

    private static final String LOG_TAG = "AA_TWEAKER_LOGS";

    private void appendText( final TextView textView, final String s ) {
        android.util.Log.d( LOG_TAG, s );
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                textView.append( s );
            }
        } );
    }

    public void loadStatus( final String path ) {

        final ProgressDialog dialog = ProgressDialog.show( MainActivity.this, "", getString( R.string.loading ), true );

        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                String get_names = runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT name FROM sqlite_master WHERE type='trigger' AND tbl_name='FlagOverrides';" + "SELECT name FROM sqlite_master WHERE type='trigger' AND tbl_name='Flags';" +

                        "SELECT name FROM sqlite_master WHERE type='trigger' AND tbl_name='Flags' AND name='after_delete';" + "SELECT name FROM sqlite_master WHERE type='trigger' AND tbl_name='Flags' AND name='aa_patched_apps';'" ).getInputStreamLog();
                String[] lines = get_names.split( System.getProperty( "line.separator" ) );
                for ( int i = 0; i < lines.length; i++ ) {
                    save( true, lines[i] );
                }
                dialog.dismiss();
            }
        } );

    }

    public void getAndRemoveOptionsSelected() {
        final TextView log = findViewById( R.id.logs );
        final String[] allTriggerString = { new String() };
        final ProgressDialog dialog = ProgressDialog.show( MainActivity.this, "", getString( R.string.loading ), true );
        new Thread() {
            @Override
            public void run() {

                String path = appDirectory;
                allTriggerString[0] = path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'";
                String get_names = runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'SELECT name FROM sqlite_master WHERE type='trigger' AND tbl_name='Flags';'" ).getInputStreamLog();
                appendText( log, get_names );
                String[] lines = get_names.split( System.getProperty( "line.separator" ) );
                final StringBuilder finalCommand = new StringBuilder();
                appendText( log, runSuWithCmd( path + "/sqlite3 /data/data/com.google.android.gms/databases/phenotype.db " + "'DROP TABLE FlagOverrides;'" ).getOutputStreamLog() );
                appendText( log, runSuWithCmd( path + "/sqlite3 /data/data/com.google.android.gms/databases/phenotype.db " + "'DELETE FROM Flags WHERE name='com.google.android.projection.gearhead';'" ).getOutputStreamLog() );
                appendText( log, runSuWithCmd( path + "/sqlite3 /data/data/com.google.android.gms/databases/phenotype.db " + "'DELETE FROM Flags WHERE name='com.google.android.gms.car';'" ).getOutputStreamLog() );

                for ( int i = 0; i < lines.length; i++ ) {
                    finalCommand.append( "DROP TRIGGER IF EXISTS " + lines[i] + ";" );
                    finalCommand.append( "\n" );
                }

                for ( int i = 0; i < lines.length; i++ ) {
                    appendText( log, runSuWithCmd( path + "/sqlite3 -batch /data/data/com.google.android.gms/databases/phenotype.db " + "'" + finalCommand + "'" ).getOutputStreamLog() );
                }
                appendText( log, runSuWithCmd( path + "/sqlite3 /data/data/com.google.android.gms/databases/phenotype.db " + "'CREATE TABLE FlagOverrides (packageName TEXT NOT NULL, user TEXT NOT NULL, name TEXT NOT NULL, flagType INTEGER NOT NULL, intVal INTEGER, boolVal INTEGER, floatVal REAL, stringVal TEXT, extensionVal BLOB, committed, PRIMARY KEY(packageName, user, name, committed));'" ).getOutputStreamLog() );
                dialog.dismiss();
            }

        }.start();

        return;
    }

    public void showRebootButton() {
        runOnUiThread( new Thread() {
            @Override
            public void run() {
                final Animation anim = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.reboot_button_anim );

                if ( !animationRun ) {
                    rebootButton.setVisibility( View.VISIBLE );
                    rebootButton.startAnimation( anim );
                    animationRun = true;
                }
            }
        } );

    }

    private void changeStatus( ImageView resource, int status, boolean doAnimation ) {
        final RotateAnimation rotate = new RotateAnimation( 0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );
        rotate.setDuration( 400 );
        rotate.setInterpolator( new LinearInterpolator() );
        switch ( status ) {
            case 2: {
                resource.setImageDrawable( getDrawable( R.drawable.ic_baseline_check_circle_24 ) );
                resource.setColorFilter( Color.argb( 255, 0, 255, 0 ) );
                break;
            }
            case 0: {
                resource.setImageDrawable( getDrawable( R.drawable.ic_baseline_remove_circle_24 ) );
                resource.setColorFilter( Color.argb( 255, 255, 0, 0 ) );
                break;
            }
            case 1: {
                resource.setImageDrawable( getDrawable( R.drawable.ic_baseline_remove_circle_24 ) );
                resource.setColorFilter( Color.argb( 255, 255, 255, 0 ) );
                break;
            }
        }
        if ( doAnimation ) {
            resource.startAnimation( rotate );
        }
    }


}
