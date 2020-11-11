package ntk.base.app.activity.news;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.entity.NewsContent;
import ntk.android.base.api.news.model.NewsContentViewRequest;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.news.NewsContentModel;
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractFilterModelingActivity;

//can do with base with extra inflate
public class ContentViewActivity extends AbstractFilterModelingActivity {

    @BindView(R.id.txtId)
    EditText txtId;
    @BindView(R.id.txtActionClientOrder)
    EditText txtActionClientOrder;
    @BindView(R.id.txtScorePercent)
    EditText txtScorePercent;
    private List<Long> TagIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        extraInflate(R.layout.activity_news_get_content_view);

    }


    public void getData() {
        NewsContentViewRequest request = new NewsContentViewRequest();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        if (!txtId.getText().toString().matches("")) {
            if (txtId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtId.setError("inValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.parseLong(txtId.getText().toString());
            }
        } else {
            txtId.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!txtActionClientOrder.getText().toString().matches("")) {
            if (txtActionClientOrder.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtActionClientOrder.setError("inValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.ActionClientOrder = Integer.parseInt(txtActionClientOrder.getText().toString());
            }
        } else {
            txtActionClientOrder.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!txtScorePercent.getText().toString().matches("")) {
            if (txtScorePercent.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtScorePercent.setError("inValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.ScorePercent = Integer.parseInt(txtScorePercent.getText().toString());
            }
        } else {
            txtScorePercent.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        new NewsContentService(this).getViewModel()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsContentModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsContentModel> newsContentErrorException) {
                        showResult(newsContentErrorException);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "NewsContentView";
    }
}
