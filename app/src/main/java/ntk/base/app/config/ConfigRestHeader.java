package ntk.base.app.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import ntk.base.app.BuildConfig;
import ntk.base.app.utill.AppUtill;
import ntk.base.app.utill.EasyPreference;

public class ConfigRestHeader {
    final String tokenKey="DeviceToken";
    @SuppressLint("HardwareIds")
    public Map<String, String> GetHeaders(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Map<String, String> headers = new HashMap<>();
        headers.put("Token", "");
        String prevToken = EasyPreference.with(context).getString(tokenKey, "");
        if (!prevToken.equalsIgnoreCase(""))
            headers.put("DeviceToken", prevToken);
        headers.put("LocationLong", "0");
        headers.put("LocationLat", "0");
        headers.put("DeviceId", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        headers.put("DeviceBrand", AppUtill.GetDeviceName());
        headers.put("Country", "IR");
        headers.put("Language", "FA");
        headers.put("SimCard", manager.getSimOperatorName());
        headers.put("PackageName", BuildConfig.APPLICATION_ID);
        headers.put("AppBuildVer", String.valueOf(BuildConfig.VERSION_CODE));
        headers.put("AppSourceVer", BuildConfig.VERSION_NAME);
        String NotId = FirebaseInstanceId.getInstance().getToken();



        if (NotId != null && !NotId.isEmpty() && !NotId.toLowerCase().equals("null")) {
            headers.put("NotificationId", NotId);
            FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.APPLICATION_ID);
        }
        return headers;
    }
}
