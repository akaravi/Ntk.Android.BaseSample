package ntk.base.app;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import ntk.android.base.ApplicationParameter;
import ntk.android.base.ApplicationStaticParameter;
import ntk.android.base.BaseNtkApplication;


public class SampleApp extends BaseNtkApplication {

    public static boolean Inbox = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!new File(getCacheDir(), "image").exists()) {
            new File(getCacheDir(), "image").mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(getCacheDir(), "image")))
                .diskCacheFileNameGenerator(imageUri -> {
                    String[] Url = imageUri.split("/");
                    return Url[Url.length];
                })
                .build();
        ImageLoader.getInstance().init(config);

//        Toasty.Config.getInstance()
//                .setToastTypeface(FontManager.GetTypeface(getApplicationContext(), FontManager.IranSans))
//                .setTextSize(14).apply();
    }

    @Override
    public void bindFireBase() {
        //not implement
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public ApplicationParameter getApplicationParameter() {
        return new ApplicationParameter(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
    }

    @Override
    protected ApplicationStaticParameter getConfig() {
//        ApplicationStaticParameter.URL="http://24b3272b.ngrok.io/";;
        return new ApplicationStaticParameter();

    }
}
