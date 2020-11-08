package ntk.base.app.activity.news;

import android.widget.AdapterView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.news.NewsCategoryTagModel;
import ntk.android.base.services.news.NewsCategoryTagService;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class CategoryTagListActivity extends AbstractFilterModelingActivity implements AdapterView.OnItemSelectedListener {


    @Override
    protected String getTitleName() {
        return "CategoryTagList";
    }

    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();

        new NewsCategoryTagService(this).getAll(request)
        .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsCategoryTagModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsCategoryTagModel> newsCategoryTagModelErrorException) {
                        showResult(newsCategoryTagModelErrorException);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });

    }


}
