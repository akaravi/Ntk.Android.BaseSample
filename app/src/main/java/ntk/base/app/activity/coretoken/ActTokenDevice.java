package ntk.base.app.activity.coretoken;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.core.entity.TokenInfoModel;
import ntk.android.base.dtomodel.core.TokenDeviceClientInfoDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.services.core.CoreAuthService;
import ntk.base.app.R;
import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;
import ntk.base.app.dialog.JsonDialog;


public class ActTokenDevice extends AppCompatActivity {

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

    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_core_token_device);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("CoreTokenDevice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("CoreTokenDevice");
    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        TokenDeviceClientInfoDtoModel req = new TokenDeviceClientInfoDtoModel();
        if (!txtSecurityKey.getText().toString().matches("")) {
            req.SecurityKey = txtSecurityKey.getText().toString();
        }
        if (!txtDeviceType.getText().toString().equals("")) {
//          todo  req.DeviceType=Integer.valueOf(txtDeviceType.getText().toString());
        }
        if (!txtOsType.getText().toString().equals("")) {

        }
        if (!txtClientMacAddress.getText().toString().equals("")) {
//          todo     req.ClientMACAddress = req.DeviceType = Integer.valueOf(txtClientMacAddress.getText().toString());
        }
        new CoreAuthService(this).getTokenDevice(req).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<ErrorException<TokenInfoModel>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ErrorException<TokenInfoModel> tokenInfoModelErrorException) {
                JsonDialog cdd = new JsonDialog(ActTokenDevice.this, tokenInfoModelErrorException);
                cdd.setCanceledOnTouchOutside(false);
                cdd.show();

            }

            @Override
            public void onError(@NonNull Throwable e) {
                progressBar.setVisibility(View.GONE);
                Log.i("Error", e.getMessage());
                Toast.makeText(ActTokenDevice.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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