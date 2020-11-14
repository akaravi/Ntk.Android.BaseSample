package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.entity.NewsCategory;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.news.NewsCategoryService;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class CategoryListActivity extends AbstractFilterModelingActivity {


    @Override
    protected String getTitleName() {
        return "NewsCategoryList";
    }


    public void getData() {

        new NewsCategoryService(this).getAll(getReq())
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
