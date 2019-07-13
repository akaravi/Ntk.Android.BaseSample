package activity.estate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import ntk.base.api.estate.interfase.IEstate;
import ntk.base.api.estate.model.EstatePropertyAddRequest;
import ntk.base.api.estate.model.EstatePropertyAddResponse;
import ntk.base.api.estate.model.EstatePropertyDetailGroupListRequest;
import ntk.base.api.estate.model.EstatePropertyDetailGroupListResponse;
import ntk.base.api.utill.RetrofitManager;
import ntk.base.app.R;
import utill.EasyPreference;

public class ActPropertyDetailGroupList extends AppCompatActivity {

    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtPropertyTypeId)
    EditText PropertyTypeId;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_estate_property_detail_group_list);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("EstatePropertyDetailGroupList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("EstatePropertyDetailGroupList");
    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        EstatePropertyDetailGroupListRequest request = new EstatePropertyDetailGroupListRequest();
        if (!PropertyTypeId.getText().toString().matches("")) {
            if (PropertyTypeId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                progressBar.setVisibility(View.GONE);
                PropertyTypeId.setError("InValid Info !!");
                return;
            } else {
                request.PropertyTypeId = Integer.valueOf(PropertyTypeId.getText().toString());
            }
        }
        RetrofitManager manager = new RetrofitManager(ActPropertyDetailGroupList.this);
        IEstate iEstate = manager.getRetrofit(configStaticValue.ApiBaseUrl).create(IEstate.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);
        headers.put("PackageName", EasyPreference.with(this).getString("packageName",""));
        Observable<EstatePropertyDetailGroupListResponse> call = iEstate.GetPropertyDetailGroupList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<EstatePropertyDetailGroupListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(EstatePropertyDetailGroupListResponse response) {
                        JsonDialog cdd = new JsonDialog(ActPropertyDetailGroupList.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActPropertyDetailGroupList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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