package ntk.base.app.activity.coretoken;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.ApplicationStaticParameter;
import ntk.android.base.dtomodel.core.TokenDeviceClientInfoDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.TokenInfoModel;
import ntk.android.base.services.core.CoreAuthService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractActivity;
import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;


public class ActTokenDevice extends AbstractActivity {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtSecurityKey)
    EditText txtSecurityKey;
    @BindView(R.id.txtDeviceType)
    EditText txtDeviceType;
    @BindView(R.id.txtOsType)
    EditText txtOsType;
    @BindView(R.id.txtClientMacAddress)
    EditText txtClientMacAddress;
    @BindView(R.id.txtPackageName)
    EditText txtpackageName;

    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);

    @Override
    protected int layout() {
        return R.layout.act_core_token_device;
    }


    @Override
    protected String getTitleName() {
        return "CoreTokenDevice";
    }


    @Override
    public void getData() {
        TokenDeviceClientInfoDtoModel req = new TokenDeviceClientInfoDtoModel();
        if (!txtSecurityKey.getText().toString().matches("")) {
            req.SecurityKey = txtSecurityKey.getText().toString();
        }
        if (!txtDeviceType.getText().toString().equals("")) {
            req.DeviceType = Integer.valueOf(txtDeviceType.getText().toString());
        }
        if (!txtOsType.getText().toString().equals("")) {
            Integer.valueOf(txtOsType.getText().toString());
        }
        if (!txtClientMacAddress.getText().toString().equals("")) {
            req.ClientMACAddress = (txtClientMacAddress.getText().toString());
        }
        if (!txtpackageName.getText().toString().equals("")) {
            req.PackageName = (txtpackageName.getText().toString());
        }
        new CoreAuthService(this).getTokenDevice(req).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<ErrorException<TokenInfoModel>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ErrorException<TokenInfoModel> tokenInfoModelErrorException) {
                showResult(tokenInfoModelErrorException);
                ApplicationStaticParameter.DEVICE_TOKEN = tokenInfoModelErrorException.Item.DeviceToken;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                showError(e);
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}