package ntk.base.app.activity.object;

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
import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;
import ntk.base.app.dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.object.interfase.IObject;
import ntk.android.base.api.object.model.ObjectPropertyActViewByJoinIdRequest;
import ntk.android.base.api.object.model.ObjectPropertyResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActGetPropertyViewByJoinId extends AppCompatActivity {

    @BindView(R.id.linkMemberPropertyId)
    EditText linkMemberPropertyId;
    @BindView(R.id.linkMemberUserId)
    EditText linkMemberUserId;
    @BindView(R.id.joinId)
    EditText joinId;

    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_object_get_property_view_by_join_id);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("ObjectPropertyViewByJoinId");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ObjectPropertyViewByJoinId");

    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        ObjectPropertyActViewByJoinIdRequest request = new ObjectPropertyActViewByJoinIdRequest();
        request.LinkMemberPropertyId = Long.parseLong(linkMemberPropertyId.getText().toString());
        request.LinkMemberUserId = Long.parseLong(linkMemberUserId.getText().toString());
        request.JoinId = ((joinId.getText().toString()));
        RetrofitManager manager = new RetrofitManager(ActGetPropertyViewByJoinId.this);
        IObject iObject = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IObject.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<ObjectPropertyResponse> call = iObject.GetPropertyActViewByJoinId(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ObjectPropertyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ObjectPropertyResponse response) {
                        JsonDialog cdd = new JsonDialog(ActGetPropertyViewByJoinId.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetPropertyViewByJoinId.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
