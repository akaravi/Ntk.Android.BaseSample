package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.news.NewsContentModel;
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.activity.AbstractFilterModelingActivity;

//need to define favorite api
//same as base
public class ContentFavoriteListActivity extends AbstractFilterModelingActivity {


    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();

        new NewsContentService(this).getFavoriteList(request).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsContentModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsContentModel> newsContentModelErrorException) {
                        showResult(newsContentModelErrorException);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }

                });
    }
        @Override
        protected String getTitleName () {
            return "NewsContentFavoriteList";
        }
    }
