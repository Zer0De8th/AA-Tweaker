package sksa.aa.tweaker;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TweakUtils {

    private static final String LOG_TAG = "AA_TWEAKER_LOGS";

    public static void appendText( AppCompatActivity activity, final TextView textView, final String s ) {
        android.util.Log.d( LOG_TAG, s );
        activity.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                textView.append( s );
            }
        } );
    }

    public static void changeStatus( AppCompatActivity activity, ImageView resource, int status, boolean doAnimation ) {
        if ( resource == null ) {
            return;
        }
        final RotateAnimation rotate = new RotateAnimation( 0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );
        rotate.setDuration( 400 );
        rotate.setInterpolator( new LinearInterpolator() );
        activity.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                switch ( status ) {
                    case 2: {
                        resource.setImageDrawable( activity.getDrawable( R.drawable.ic_baseline_check_circle_24 ) );
                        resource.setColorFilter( Color.argb( 255, 0, 255, 0 ) );
                        break;
                    }
                    case 0: {
                        resource.setImageDrawable( activity.getDrawable( R.drawable.ic_baseline_remove_circle_24 ) );
                        resource.setColorFilter( Color.argb( 255, 255, 0, 0 ) );
                        break;
                    }
                    case 1: {
                        resource.setImageDrawable( activity.getDrawable( R.drawable.ic_baseline_remove_circle_24 ) );
                        resource.setColorFilter( Color.argb( 255, 255, 255, 0 ) );
                        break;
                    }
                }
                if ( doAnimation ) {
                    resource.startAnimation( rotate );
                }
            }
        } );
    }

    public static void killps( AppCompatActivity activity, final TextView logs ) {
        appendText( activity, logs, "\n\n--  Force stopping Google Play Services   --" );
        appendText( activity, logs, ShellUtils.runSuWithCmd( "am kill all com.google.android.gms" ).getStreamLogsWithLabels() );
    }

    public static String gainOwnership( AppCompatActivity activity, final TextView logs ) {
        appendText( activity, logs, "\n\n--  Gaining ownership of the database   --" );
        appendText( activity, logs, ShellUtils.runSuWithCmd( "chown root /data/data/com.google.android.gms/databases/phenotype.db" ).getStreamLogsWithLabels() );

        String currentPolicy = ShellUtils.runSuWithCmd( "getenforce" ).getInputStreamLog();
        appendText( activity, logs, "\n\n--  Setting SELINUX to permissive   --" );
        appendText( activity, logs, ShellUtils.runSuWithCmd( "setenforce 0" ).getStreamLogsWithLabels() );
        return currentPolicy;
    }

    public static void showRebootButton( AppCompatActivity activity ) {
        activity.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                android.widget.Button rebootButton = activity.findViewById( R.id.reboot_button );
                rebootButton.setVisibility( android.view.View.VISIBLE );
            }
        } );
    }

    public static void save( AppCompatActivity activity, final boolean isChecked, String key ) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences( "MainActivity", 0 );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean( key, isChecked );
        editor.commit();
    }

    public static void saveValue( AppCompatActivity activity, final int value, String key ) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences( "MainActivity", 0 );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt( key, value );
        editor.commit();
    }

    public static void saveFloat( AppCompatActivity activity, final float value, String key ) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences( "MainActivity", 0 );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat( key, value );
        editor.commit();
    }

    public static void initiateLogsText( AppCompatActivity activity, final TextView logs ) {
        activity.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                logs.setText( "" );
            }
        } );
    }
}