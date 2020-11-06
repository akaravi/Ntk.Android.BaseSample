package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.entity.NewsCategory;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.news.NewsCategoryService;
import ntk.base.app.activity.BaseFilterModelingActivity;

public class CategoryListActivity extends BaseFilterModelingActivity {


    @Override
    protected String getTitleName() {
        return "NewsCategoryList";
    }


    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        new NewsCategoryService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsCategory>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsCategory> response) {
                        showResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }

}
