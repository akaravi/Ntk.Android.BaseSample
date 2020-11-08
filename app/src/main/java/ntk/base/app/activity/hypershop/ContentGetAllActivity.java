package ntk.base.app.activity.hypershop;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.dtomodel.hypershop.HyperShopContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.services.hypershop.HyperShopMicroService;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class ContentGetAllActivity extends AbstractFilterModelingActivity {
    @Override
    public void getData() {
        new HyperShopMicroService(this).getAllContent(getReq())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<HyperShopContentModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<HyperShopContentModel> response) {
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
        return "ContentGetAll";
    }
}
