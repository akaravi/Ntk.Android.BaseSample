package activity.core;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import ntk.android.base.api.core.interfase.ICore;
import ntk.android.base.api.core.model.CoreUserConfirmEmailRequest;
import ntk.android.base.api.core.model.CoreUserConfirmResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActUserEmailConfirm extends AppCompatActivity {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtEmailActUserEmailConfirm)
    EditText Email;
    @BindView(R.id.txtCodeActUserEmailConfirm)
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
        setContentView(R.layout.act_core_user_email_confirm);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("CoreUserEmailConfirm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("CoreUserEmailConfirm");
    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        CoreUserConfirmEmailRequest request = new CoreUserConfirmEmailRequest();
        if (!Email.getText().toString().matches("")) {
            request.Email = Email.getText().toString();
        } else {
            Email.setError("Required Info !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!Code.getText().toString().matches("")) {
            request.Code = Code.getText().toString();
        } else {
            Code.setError("Required Info !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActUserEmailConfirm.this);
        ICore iCore = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(ICore.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<CoreUserConfirmResponse> call = iCore.userEmailConfirm(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CoreUserConfirmResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CoreUserConfirmResponse response) {
                        JsonDialog cdd = new JsonDialog(ActUserEmailConfirm.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActUserEmailConfirm.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
