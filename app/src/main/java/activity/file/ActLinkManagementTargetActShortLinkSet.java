package activity.file;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;

import config.ConfigRestHeader;
import config.ConfigStaticValue;
import dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.core.interfase.ICoreGet;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.linkManagemen.interfase.ILinkManagement;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetRequest;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetRequest;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetResponce;
import ntk.base.config.RetrofitManager;
import ntk.base.app.R;
import utill.AppUtill;

public class ActLinkManagementTargetActShortLinkSet extends AppCompatActivity  implements CaptchaReadyListener {
    private CaptchaModel captchaModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_file_shortlink_set);
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
                            Toast.makeText(ActLinkManagementTargetActShortLinkSet.this, "خطا در دریافت کپچا", Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Toast.makeText(ActLinkManagementTargetActShortLinkSet.this, "خطای سامانه", Toast.LENGTH_LONG).show();
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
        String url = ((EditText) findViewById(R.id.urlAddressEt)).getText().toString();
        String captchaText = ((EditText) findViewById(R.id.captchaEt)).getText().toString();
        if (url.trim().equals(""))
            Toast.makeText(ActLinkManagementTargetActShortLinkSet.this, "آدرس وارد نشده است", Toast.LENGTH_LONG).show();
        else if (captchaText.trim().equals(""))
            Toast.makeText(ActLinkManagementTargetActShortLinkSet.this, "متن کپچا وارد نشده است", Toast.LENGTH_LONG).show();
        else {
            LinkManagementTargetActShortLinkSetRequest req = new LinkManagementTargetActShortLinkSetRequest();
            req.CaptchaText = captchaText;
            req.UrlAddress = url;
            req.CaptchaKey = captchaModel.Key;
            callShortLinkSetApi(req);
        }
    }
        public void callShortLinkSetApi(LinkManagementTargetActShortLinkSetRequest req) {
            if (AppUtill.isNetworkAvailable(this)) {
                //get captcha
                RetrofitManager retro = new RetrofitManager(ActLinkManagementTargetActShortLinkSet.this);
                ILinkManagement iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(ActLinkManagementTargetActShortLinkSet.this).GetApiBaseUrl()).create(ILinkManagement.class);
                Map<String, String> headers = new ConfigRestHeader().GetHeaders(ActLinkManagementTargetActShortLinkSet.this);
                Observable<LinkManagementTargetActShortLinkSetResponce> Call = iTicket.LinkManagementTargetActShortLinkSet(headers, req);
                Call.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<LinkManagementTargetActShortLinkSetResponce>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull LinkManagementTargetActShortLinkSetResponce linkResponse) {
                                JsonDialog cdd = new JsonDialog(ActLinkManagementTargetActShortLinkSet.this, linkResponse);
                                cdd.setCanceledOnTouchOutside(false);
                                cdd.show();                           }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                Toast.makeText(ActLinkManagementTargetActShortLinkSet.this, "خطای سامانه", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } else {

                Toast.makeText(ActLinkManagementTargetActShortLinkSet.this, "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
            }
        }

}
