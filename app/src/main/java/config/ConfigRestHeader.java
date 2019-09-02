package config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;


import java.util.HashMap;
import java.util.Map;

import ntk.base.app.BuildConfig;
import utill.EasyPreference;

public class ConfigRestHeader {

    @SuppressLint("HardwareIds")
    public Map<String, String> GetHeaders(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Map<String, String> headers = new HashMap<>();
        headers.put("Token", "");
        headers.put("LocationLong", "0");
        headers.put("LocationLat", "0");
        headers.put("DeviceId", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        headers.put("DeviceBrand", "BaseSimple");
        headers.put("NotificationId", "123");
        headers.put("Country", context.getResources().getConfiguration().locale.getDisplayCountry());
        headers.put("Language", context.getResources().getConfiguration().locale.getLanguage());
        headers.put("SimCard", manager.getSimOperatorName());
        headers.put("AppBuildVer", "1");
        headers.put("AppSourceVer", "2");
        //not use default//headers.put("PackageName", BuildConfig.APPLICATION_ID);
        headers.put("PackageName", EasyPreference.with(context).getString("packageName",""));

        return headers;
    }
}
