package com.company.kendamaruntimepermission;

/**
 * Created by shimeikou on 2018/02/18.
 */
import android.content.pm.PackageManager;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class OverrideUnityActivity extends UnityPlayerActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SendRequestResultToUnity("OnAllow");
                } else {
                    if(shouldShowRequestPermissionRationale(permissions[0])){
                        SendRequestResultToUnity("OnDeny");
                    } else {
                        SendRequestResultToUnity("OnDenyAndNeverAskAgain");
                    }
                }
                break;
            }
        }
    }

    private void SendRequestResultToUnity(String result){
        UnityPlayer.UnitySendMessage("UniAndroidPermission", result, "");
    }
}
