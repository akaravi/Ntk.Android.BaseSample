package activity.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.ConfigRestHeader;
import config.ConfigStaticValue;
import dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.base.api.core.interfase.ICore;
import ntk.base.api.core.model.CoreUserLoginByMobileRequest;
import ntk.base.api.core.model.CoreUserLoginRequest;
import ntk.base.api.core.model.CoreUserResponse;
import ntk.base.api.utill.RetrofitManager;
import ntk.base.app.R;

public class ActUserLoginByMobile extends AppCompatActivity {

    @BindView(R.id.txtPackageName)
    EditText txtPackageName;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtlangActUserLoginByMobile)
    EditText lang;
    @BindView(R.id.txtMobileActUserLoginByMobile)
    EditText Mobile;
    @BindView(R.id.txtCodeActUserLoginByMobile)
    EditText Code;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_core_user_login_by_mobile);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("CoreUserLoginByMobile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("CoreUserLoginByMobile");
    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        CoreUserLoginByMobileRequest request = new CoreUserLoginByMobileRequest();
        if (!lang.getText().toString().matches("")) {
            request.lang = lang.getText().toString();
        }
        if (!Mobile.getText().toString().matches("")) {
            request.Mobile = Mobile.getText().toString();
        } else {
            Mobile.setError("Required Info !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!Code.getText().toString().matches("")) {
            request.Code = Code.getText().toString();
        }
        RetrofitManager manager = new RetrofitManager(ActUserLoginByMobile.this);
        ICore iCore = manager.getRetrofit(configStaticValue.ApiBaseUrl).create(ICore.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);
        headers.put("PackageName", txtPackageName.getText().toString());

        Observable<CoreUserResponse> call = iCore.userLoginByMobile(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CoreUserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CoreUserResponse response) {
                        JsonDialog cdd = new JsonDialog(ActUserLoginByMobile.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActUserLoginByMobile.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this, ActCore.class));
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, ActCore.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}