package ntk.base.app.activity.blog;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;
import ntk.base.app.dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.blog.interfase.IBlog;
import ntk.android.base.api.blog.model.BlogContentFavoriteAddRequest;
import ntk.android.base.api.blog.model.BlogContentFavoriteAddResponse;
import ntk.android.base.api.blog.model.BlogContentFavoriteRemoveRequest;
import ntk.android.base.api.blog.model.BlogContentFavoriteRemoveResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActContentFavoriteAddOrRemove extends AppCompatActivity {

    EditText txtPackageName;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtIdAddOrRemove)
    EditText txtIdAddOrRemove;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.progressBarAdd)
    ProgressBar progressBarAdd;
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.progressBarRemove)
    ProgressBar progressBarRemove;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_blog_content_favorite_add_or_remove);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("BlogContentFavoriteAdd/BlogContentFavoriteRemove");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("BlogContentFavoriteAddOrRemove");
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
        BlogContentFavoriteAddRequest request = new BlogContentFavoriteAddRequest();
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarAdd.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.valueOf(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarAdd.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActContentFavoriteAddOrRemove.this);
        IBlog iBlog = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IBlog.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);
        headers.put("PackageName", txtPackageName.getText().toString());

        Observable<BlogContentFavoriteAddResponse> call = iBlog.SetContentFavoriteAdd(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BlogContentFavoriteAddResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BlogContentFavoriteAddResponse response) {
                        JsonDialog cdd = new JsonDialog(ActContentFavoriteAddOrRemove.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBarAdd.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActContentFavoriteAddOrRemove.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBarAdd.setVisibility(View.GONE);
                    }
                });
    }

    private void removeFavorite() {
        BlogContentFavoriteRemoveRequest request = new BlogContentFavoriteRemoveRequest();
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
        RetrofitManager manager = new RetrofitManager(ActContentFavoriteAddOrRemove.this);
        IBlog iBlog = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IBlog.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);
        headers.put("PackageName", txtPackageName.getText().toString());

        Observable<BlogContentFavoriteRemoveResponse> call = iBlog.SetContentFavoriteRemove(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BlogContentFavoriteRemoveResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BlogContentFavoriteRemoveResponse response) {
                        JsonDialog cdd = new JsonDialog(ActContentFavoriteAddOrRemove.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBarRemove.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActContentFavoriteAddOrRemove.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
            startActivity(new Intent(this, ActBlog.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
