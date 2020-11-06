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
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.R;
import ntk.base.app.activity.BaseFilterModelingActivity;

//can do with base with extra inflate
public class ContentViewActivity extends BaseFilterModelingActivity {

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
        extraInflate(R.layout.act_news_get_content_view);

    }


    public void getData() {
        NewsContentViewRequest request = new NewsContentViewRequest();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        if (!txtId.getText().toString().matches("")) {
            if (txtId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtId.setError("inValid Info !!");
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
                txtActionClientOrder.setError("inValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.ActionClientOrder = Integer.valueOf(txtActionClientOrder.getText().toString());
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
                request.ScorePercent = Integer.valueOf(txtScorePercent.getText().toString());
            }
        } else {
            txtScorePercent.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        new NewsContentService(this).getViewModel()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsContent>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsContent> newsContentErrorException) {
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
