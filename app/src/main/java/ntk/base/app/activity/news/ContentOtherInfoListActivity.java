package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.news.NewsContentOtherInfoModel;
import ntk.android.base.services.news.NewsContentOtherInfoService;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class ContentOtherInfoListActivity extends AbstractFilterModelingActivity {


    public void getData() {
        new NewsContentOtherInfoService(this).getAll(getReq())
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
