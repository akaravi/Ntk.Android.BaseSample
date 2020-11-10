package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class TagListActivity extends AbstractFilterModelingActivity {


    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();

//        new NewsTagModelService(this).getAll(request)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new NtkObserver<ErrorException<NewsTagModel>>() {
//                    @Override
//                    public void onNext(@NonNull ErrorException<NewsTagModel> newsTagModelErrorException) {
//                        showResult(newsTagModelErrorException);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        showError(e);
//                    }
//                });
    }

    @Override
    protected String getTitleName() {
        return "NewsTagList";
    }
}
