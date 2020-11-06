package ntk.base.app.activity.news;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.entity.NewsContent;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.news.NewsCommentModel;
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractActivity;

//net to be content view
public class CommentViewActivity extends AbstractActivity {


    @BindView(R.id.txtId)
    EditText txtId;
    @BindView(R.id.txtActionClientOrder)
    EditText txtActionClientOrder;


    @Override
    protected int layout() {
        return R.layout.activity_news_comment_view;
    }

    @Override
    protected String getTitleName() {
        return "NewsCommentView";
    }


    public void getData() {
        NewsCommentModel request = new NewsCommentModel();
        if (!txtId.getText().toString().matches("")) {
            if (txtId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtId.setError("Invalid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.valueOf(txtId.getText().toString());
            }
        } else {
            txtId.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!txtActionClientOrder.getText().toString().matches("")) {
            if (txtActionClientOrder.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtActionClientOrder.setError("Invalid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                //todo karavi say
//                request.actionClientOrder = Integer.valueOf(txtActionClientOrder.getText().toString());
            }
        } else {
            txtActionClientOrder.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        new NewsContentService(this).getViewModel().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsContent>>() {

                    @Override
                    public void onNext(@NonNull ErrorException<NewsContent> response) {
                        showResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }

                });
    }

}
