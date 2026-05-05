package sksa.aa.tweaker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShellUtils {

    public static StreamLogs runSuWithCmd( String cmd ) {
        java.io.DataOutputStream outputStream = null;
        InputStream inputStream = null;
        InputStream errorStream = null;

        StreamLogs streamLogs = new StreamLogs();
        streamLogs.setOutputStreamLog( cmd );

        try {
            Process su = Runtime.getRuntime().exec( "su" );
            outputStream = new java.io.DataOutputStream( su.getOutputStream() );
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

    public static boolean isAppInstalled( Context context, String packageName ) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo( packageName, PackageManager.GET_ACTIVITIES );
            return true;
        } catch ( PackageManager.NameNotFoundException e ) {
            return false;
        }
    }

    public static boolean isAppEnabled( Context context, String packageName ) {
        Boolean appStatus = false;
        try {
            android.content.pm.ApplicationInfo ai = context.getPackageManager().getApplicationInfo( packageName, 0 );
            if ( ai != null ) {
                appStatus = ai.enabled;
            }
        } catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
        return appStatus;
    }
}