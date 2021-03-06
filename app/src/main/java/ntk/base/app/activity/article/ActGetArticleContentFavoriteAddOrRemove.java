package ntk.base.app.activity.article;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
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
import ntk.android.base.api.article.model.ArticleContentFavoriteAddRequest;
import ntk.android.base.api.article.model.ArticleContentFavoriteAddResponse;
import ntk.android.base.api.article.model.ArticleContentFavoriteRemoveRequest;
import ntk.android.base.api.article.model.ArticleContentFavoriteRemoveResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActGetArticleContentFavoriteAddOrRemove extends AppCompatActivity {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    @BindView(R.id.LalIdActContentFavoriteAdd)
    EditText LalIdActContentFavoriteAdd;
    @BindViews({R.id.add_progress_bar, R.id.remove_progress_bar})
    List<ProgressBar> progressBars;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article_get_content_favorite_add_or_remove);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        lblLayout.setText("ArticleContentFavoriteAdd/ArticleContentFavoriteRemove");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ActArticle.LAYOUT_VALUE));
    }

    @OnClick(R.id.remove_button)
    public void remove() {
        progressBars.get(1).setVisibility(View.VISIBLE);
        removeFavorite();
    }

    @OnClick(R.id.add_button)
    public void add() {
        progressBars.get(0).setVisibility(View.VISIBLE);
        addFavorite();
    }

    private void addFavorite() {
        ArticleContentFavoriteAddRequest request = new ArticleContentFavoriteAddRequest();
        if (!LalIdActContentFavoriteAdd.getText().toString().matches("")) {
            try {
                request.Id = Long.valueOf(LalIdActContentFavoriteAdd.getText().toString());
            } catch (Exception e) {
                LalIdActContentFavoriteAdd.setError("InValid Info !!");
                progressBars.get(0).setVisibility(View.GONE);
                return;
            }
        } else {
            LalIdActContentFavoriteAdd.setError("Require !!");
            progressBars.get(0).setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActGetArticleContentFavoriteAddOrRemove.this);
        IArticle iArticle = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IArticle.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<ArticleContentFavoriteAddResponse> call = iArticle.SetContentFavoriteAdd(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArticleContentFavoriteAddResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArticleContentFavoriteAddResponse response) {
                        JsonDialog cdd = new JsonDialog(ActGetArticleContentFavoriteAddOrRemove.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBars.get(0).setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetArticleContentFavoriteAddOrRemove.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBars.get(0).setVisibility(View.GONE);
                    }
                });

    }

    private void removeFavorite() {
        ArticleContentFavoriteRemoveRequest request = new ArticleContentFavoriteRemoveRequest();
        if (!LalIdActContentFavoriteAdd.getText().toString().matches("")) {
            try {
                request.Id = Long.valueOf(LalIdActContentFavoriteAdd.getText().toString());
            } catch (Exception e) {
                LalIdActContentFavoriteAdd.setError("InValid Info !!");
                progressBars.get(1).setVisibility(View.GONE);
                return;
            }
        } else {
            LalIdActContentFavoriteAdd.setError("Require !!");
            progressBars.get(1).setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActGetArticleContentFavoriteAddOrRemove.this);
        IArticle iArticle = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IArticle.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<ArticleContentFavoriteRemoveResponse> call = iArticle.SetContentFavoriteRemove(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArticleContentFavoriteRemoveResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArticleContentFavoriteRemoveResponse response) {
                        JsonDialog cdd = new JsonDialog(ActGetArticleContentFavoriteAddOrRemove.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBars.get(1).setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetArticleContentFavoriteAddOrRemove.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBars.get(1).setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
