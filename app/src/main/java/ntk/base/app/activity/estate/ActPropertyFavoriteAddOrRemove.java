package ntk.base.app.activity.estate;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
import ntk.android.base.api.estate.interfase.IEstate;
import ntk.android.base.api.estate.model.EstatePropertyFavoriteAddRequest;
import ntk.android.base.api.estate.model.EstatePropertyFavoriteAddResponse;
import ntk.android.base.api.estate.model.EstatePropertyFavoriteRemoveRequest;
import ntk.android.base.api.estate.model.EstatePropertyFavoriteRemoveResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;
//ok 0.34
public class ActPropertyFavoriteAddOrRemove extends AppCompatActivity {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtIdAddOrRemove)
    EditText txtIdAddOrRemove;
    @BindView(R.id.progressBarAdd)
    ProgressBar progressBarAdd;
    @BindView(R.id.progressBarRemove)
    ProgressBar progressBarRemove;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_estate_property_favorite_add_or_remove);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("EstatePropertyFavoriteAdd/EstatePropertyFavoriteRemove");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("EstatePropertyFavoriteAddOrRemove");
    }

    @OnClick(R.id.btnRemove)
    public void remove() {
        progressBarRemove.setVisibility(View.VISIBLE);
        removeFavorite();
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        progressBarAdd.setVisibility(View.VISIBLE);
        addFavorite();
    }

    private void addFavorite() {
        EstatePropertyFavoriteAddRequest request = new EstatePropertyFavoriteAddRequest();
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarAdd.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Integer.valueOf(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarAdd.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActPropertyFavoriteAddOrRemove.this);
        IEstate iEstate = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IEstate.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);


        Observable<EstatePropertyFavoriteAddResponse> call = iEstate.SetEstatePropertyFavoriteActAdd(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<EstatePropertyFavoriteAddResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(EstatePropertyFavoriteAddResponse response) {
                        JsonDialog cdd = new JsonDialog(ActPropertyFavoriteAddOrRemove.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBarAdd.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActPropertyFavoriteAddOrRemove.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBarAdd.setVisibility(View.GONE);
                    }
                });
    }

    private void removeFavorite() {
        EstatePropertyFavoriteRemoveRequest request = new EstatePropertyFavoriteRemoveRequest();
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarRemove.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.valueOf(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarRemove.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActPropertyFavoriteAddOrRemove.this);
        IEstate iEstate = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IEstate.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);


        Observable<EstatePropertyFavoriteRemoveResponse> call = iEstate.SetEstatePropertyFavoriteActRemove(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<EstatePropertyFavoriteRemoveResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(EstatePropertyFavoriteRemoveResponse response) {
                        JsonDialog cdd = new JsonDialog(ActPropertyFavoriteAddOrRemove.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBarRemove.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActPropertyFavoriteAddOrRemove.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBarRemove.setVisibility(View.GONE);
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
