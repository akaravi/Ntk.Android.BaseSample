package ntk.base.app.activity.article;

import android.os.Bundle;
import androidx.annotation.Nullable;
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
import ntk.android.base.api.article.interfase.IArticle;
import ntk.android.base.api.article.model.ArticleContentResponse;
import ntk.android.base.api.article.model.ArticleContentViewRequest;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActGetArticleContentView extends AppCompatActivity {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    @BindView(R.id.id)
    EditText id;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article_get_content_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        lblLayout.setText("ArticleContentView");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ActArticle.LAYOUT_VALUE));
    }

    @OnClick(R.id.api_test_submit_button)
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        ArticleContentViewRequest request = new ArticleContentViewRequest();
        if (!id.getText().toString().matches("")) {
            request.Id = Integer.valueOf(id.getText().toString());
        } else {
            id.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActGetArticleContentView.this);
        IArticle iArticle = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IArticle.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<ArticleContentResponse> call = iArticle.GetContentView(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArticleContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArticleContentResponse articleContentResponse) {
                        JsonDialog cdd = new JsonDialog(ActGetArticleContentView.this, articleContentResponse);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetArticleContentView.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
