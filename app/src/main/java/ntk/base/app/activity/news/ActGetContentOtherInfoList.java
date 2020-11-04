package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.news.NewsContentOtherInfoModel;
import ntk.android.base.services.news.NewsContentOtherInfoService;
import ntk.base.app.activity.BaseFilterModelingActivity;

public class ActGetContentOtherInfoList extends BaseFilterModelingActivity {


    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        new NewsContentOtherInfoService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsContentOtherInfoModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsContentOtherInfoModel> newsContentOtherInfoModelErrorException) {
                        showResult(newsContentOtherInfoModelErrorException);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }


    @Override
    protected String getTitleName() {
        return "NewsContentOtherInfoList";
    }
}
