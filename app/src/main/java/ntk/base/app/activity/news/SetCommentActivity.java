package ntk.base.app.activity.news;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorExceptionBase;
import ntk.android.base.entitymodel.news.NewsCommentModel;
import ntk.android.base.services.news.NewsCommentService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractActivity;

public class SetCommentActivity extends AbstractActivity {

    @BindView(R.id.txtWriter)
    EditText txtWriter;
    @BindView(R.id.txtComment)
    EditText txtComment;
    @BindView(R.id.txtLinkParentId)
    EditText txtLinkParentId;
    @BindView(R.id.txtLinkContentId)
    EditText txtLinkContentId;


    @Override
    protected int layout() {
        return R.layout.activity_news_set_comment;
    }

    @Override
    protected String getTitleName() {
        return null;
    }


    public void getData() {
        NewsCommentModel request = new NewsCommentModel();
        if (!txtWriter.getText().toString().matches("")) {
            request.writer = txtWriter.getText().toString();
        } else {
            txtWriter.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!txtComment.getText().toString().matches("")) {
            request.comment = txtWriter.getText().toString();
        } else {
            txtWriter.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (!txtLinkParentId.getText().toString().matches("")) {
            if (txtLinkParentId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtLinkParentId.setError("InValid Info  !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.linkParentId = Long.valueOf(txtLinkParentId.getText().toString());
            }
        }

        if (!txtLinkContentId.getText().toString().matches("")) {
            if (txtLinkContentId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtLinkContentId.setError("InValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.linkContentid = Long.parseLong(txtLinkContentId.getText().toString());
            }
        } else {
            txtLinkContentId.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        new NewsCommentService(this).add(request).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorExceptionBase>() {
                    @Override
                    public void onNext(@NonNull ErrorExceptionBase response) {
                        showResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }


}
