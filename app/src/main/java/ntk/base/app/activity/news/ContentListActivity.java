package ntk.base.app.activity.news;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.entity.NewsContent;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.Filters;
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class ContentListActivity extends AbstractFilterModelingActivity {

    @BindView(R.id.txtTag)
    EditText txtTag;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    private List<Long> TagIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extraInflate(R.layout.activity_news_content_list);
    }


    @OnClick(R.id.btnAdd)
    public void onAddClick(View v) {
        try {
            TagIds.add(Long.valueOf(txtTag.getText().toString()));
            txtTag.setText("");
        } catch (Exception e) {
            txtTag.setError("inValid Info !!");
        }
    }

    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        request.filters = new ArrayList<>();
        if (!TagIds.isEmpty()) {
            Filters filter = new Filters();
            filter.IntContainValues = TagIds;
            filter.PropertyName = "Tag";
            request.filters.add(filter);
        }
        new NewsContentService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
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


    @Override
    protected String getTitleName() {
        return "NewsContentList";
    }
}
