package sksa.aa.tweaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PatchReceiver extends BroadcastReceiver {
    @Override
    public void onReceive( Context context, Intent intent ) {
        ( (MainActivity) context ).patchforapps();
    }
}