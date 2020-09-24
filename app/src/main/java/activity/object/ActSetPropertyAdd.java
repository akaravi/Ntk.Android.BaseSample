package activity.object;

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
import ntk.base.api.object.interfase.IObject;
import ntk.base.api.object.model.ObjectPropertyActAddRequest;
import ntk.base.api.object.model.ObjectPropertyResponse;
import ntk.base.api.utill.RetrofitManager;
import ntk.base.app.R;

public class ActSetPropertyAdd extends AppCompatActivity {

    @BindView(R.id.id)
    EditText id;
    @BindView(R.id.linkObjectUserId)
    EditText linkObjectUserId;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.linkCmsUserId)
    EditText linkCmsUserId;
    @BindView(R.id.linkPropertyTypeId)
    EditText linkPropertyTypeId;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.linkMainImageId)
    EditText linkMainImageId;
    @BindView(R.id.linkExtraImageIds)
    EditText linkExtraImageIds;
    @BindView(R.id.linkFileIds)
    EditText linkFileIds;
    @BindView(R.id.mainImageSrc)
    EditText mainImageSrc;
    @BindView(R.id.api_test_submit_button)
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
        setContentView(R.layout.act_object_set_property_add);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("ObjectPropertyAdd");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ObjectPropertyAdd");

    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        ObjectPropertyActAddRequest request = new ObjectPropertyActAddRequest();
        request.Id = Long.parseLong(id.getText().toString());
        request.LinkObjectUserId = Long.parseLong(linkObjectUserId.getText().toString());
        request.Title = title.getText().toString();
        request.Description = description.getText().toString();
        request.LinkCmsUserId = Long.valueOf(linkCmsUserId.getText().toString());
        request.LinkPropertyTypeId = Long.valueOf(linkPropertyTypeId.getText().toString());
        request.Address = address.getText().toString();
        request.LinkMainImageId = Long.valueOf(linkMainImageId.getText().toString());
        request.LinkExtraImageIds = linkExtraImageIds.getText().toString();
        request.LinkFileIds = linkFileIds.getText().toString();
        request.MainImageSrc = mainImageSrc.getText().toString();
                RetrofitManager manager = new RetrofitManager(ActSetPropertyAdd.this);
        IObject iObject = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IObject.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<ObjectPropertyResponse> call = iObject.SetPropertyActAdd(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ObjectPropertyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ObjectPropertyResponse response) {
                        JsonDialog cdd = new JsonDialog(ActSetPropertyAdd.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActSetPropertyAdd.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
