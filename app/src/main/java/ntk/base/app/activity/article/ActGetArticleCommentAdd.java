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
import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;
import ntk.base.app.dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.article.interfase.IArticle;
import ntk.android.base.api.article.model.ArticleCommentAddRequest;
import ntk.android.base.api.article.model.ArticleCommentResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActGetArticleCommentAdd extends AppCompatActivity {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    @BindView(R.id.writer)
    EditText writer;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.LinkParentId)
    EditText LinkParentId;
    @BindView(R.id.LinkContentId)
    EditText LinkContentId;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
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
        setContentView(R.layout.act_article_get_comment_add);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        lblLayout.setText("ArticleCommentAdd");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ActArticle.LAYOUT_VALUE));
        apiTestSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getData();
            }
        });
    }

    private void getData() {
        ArticleCommentAddRequest request = new ArticleCommentAddRequest();
        if (!writer.getText().toString().matches("")) {
            request.Writer = writer.getText().toString();
        } else {
            writer.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!comment.getText().toString().matches("")) {
            request.Comment = comment.getText().toString();
        } else {
            comment.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }

        try {
            if (!LinkParentId.getText().toString().matches("")) {
                request.LinkParentId = Long.valueOf(LinkParentId.getText().toString());
            }
        } catch (Exception e) {
            LinkParentId.setError("InValid Info  !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        try {
            if (!LinkContentId.getText().toString().matches("")) {
                request.LinkContentId = Long.valueOf(LinkContentId.getText().toString());
            } else {
                LinkContentId.setError("Required !!");
                progressBar.setVisibility(View.GONE);
                return;
            }
        } catch (Exception e) {
            LinkContentId.setError("InValid Info  !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActGetArticleCommentAdd.this);
        IArticle iArticle = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IArticle.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<ArticleCommentResponse> call = iArticle.SetComment(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArticleCommentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArticleCommentResponse response) {
                        JsonDialog cdd = new JsonDialog(ActGetArticleCommentAdd.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetArticleCommentAdd.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
}
