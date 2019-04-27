package config;

import android.content.Context;



public class ConfigStaticValue {
    public ConfigStaticValue(Context context) {
        privateContext=context;
        ApiBaseAppId = 0;
        ApiBaseUrl = null;
        ApiBaseUrl = "http://c4b57f02.ngrok.io/";
        //ApiBaseUrl = "http://9c782c46.ngrok.io";


    }
    private Context privateContext;
    public String ApiBaseUrl;

    public int ApiBaseAppId;
}
