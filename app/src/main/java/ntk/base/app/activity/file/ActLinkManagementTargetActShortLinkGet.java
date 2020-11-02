package ntk.base.app.activity.file;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;

import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;
import ntk.base.app.dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.core.entity.CaptchaModel;
import ntk.android.base.api.core.interfase.ICoreGet;
import ntk.android.base.api.core.model.CaptchaResponce;
import ntk.android.base.api.linkManagemen.interfase.ILinkManagement;
import ntk.android.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetRequest;
import ntk.android.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;

import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;
import ntk.base.app.utill.AppUtill;

public class ActLinkManagementTargetActShortLinkGet extends AppCompatActivity implements CaptchaReadyListener {
    private CaptchaModel captchaModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_file_shortlink_get);
        findViewById(R.id.captchaImg).setOnClickListener(v -> getCaptchaApi(this));
        findViewById(R.id.generateBtn).setOnClickListener(v -> callApi());

    }

    public void getCaptchaApi(CaptchaReadyListener f) {

        //get captcha
        RetrofitManager retro = new RetrofitManager(this);
        ICoreGet iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(ICoreGet.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
        Observable<CaptchaResponce> Call = iTicket.GetCaptcha(headers);

        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CaptchaResponce>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CaptchaResponce captchaResponce) {

                        if (captchaResponce.IsSuccess) {
                            captchaModel = (captchaResponce.Item);
                            f.onCaptchaReady();
                        } else
                            Toast.makeText(ActLinkManagementTargetActShortLinkGet.this, "خطا در دریافت کپچا", Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Toast.makeText(ActLinkManagementTargetActShortLinkGet.this, "خطای سامانه", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    @Override
    public void onCaptchaReady() {
        CaptchaModel lastCaptcha = captchaModel;
        if (lastCaptcha != null) {

            ImageLoader.getInstance().displayImage(lastCaptcha.image, (ImageView) findViewById(R.id.captchaImg));

        }
    }

    private void callApi() {
        String keyText = ((EditText) findViewById(R.id.shortLinkEt)).getText().toString();
        String captchaText = ((EditText) findViewById(R.id.captchaEt)).getText().toString();
        if (keyText.trim().equals(""))
            Toast.makeText(this, "کلید وارد نشده است", Toast.LENGTH_LONG).show();
        else if (captchaText.trim().equals(""))
            Toast.makeText(this, "متن کپچا وارد نشده است", Toast.LENGTH_LONG).show();
        else {

            LinkManagementTargetActShortLinkGetRequest req = new LinkManagementTargetActShortLinkGetRequest();
            req.CaptchaText = captchaText;
            req.Key = keyText;
            req.CaptchaKey = captchaModel.Key;
            callShortLinkGetApi(req);
        }
    }

    protected void callShortLinkGetApi(LinkManagementTargetActShortLinkGetRequest req) {
        if (AppUtill.isNetworkAvailable(this)) {
            //get captcha
            RetrofitManager retro = new RetrofitManager(this);
            ILinkManagement iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(ILinkManagement.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            Observable<LinkManagementTargetActShortLinkGetResponce> Call = iTicket.LinkManagementTargetActShortLinkGet(headers, req);

            Call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<LinkManagementTargetActShortLinkGetResponce>() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull LinkManagementTargetActShortLinkGetResponce linkResponse) {
                            JsonDialog cdd = new JsonDialog(ActLinkManagementTargetActShortLinkGet.this, linkResponse);
                            cdd.setCanceledOnTouchOutside(false);
                            cdd.show();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                            Toast.makeText(ActLinkManagementTargetActShortLinkGet.this, "خطای سامانه", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {

            Toast.makeText(this, "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
        }
    }

}
