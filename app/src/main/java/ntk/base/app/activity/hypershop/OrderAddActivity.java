package ntk.base.app.activity.hypershop;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.dtomodel.hypershop.HyperShopContentModel;
import ntk.android.base.dtomodel.hypershop.HyperShopOrderModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.services.hypershop.HyperShopMicroService;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class OrderAddActivity extends AbstractFilterModelingActivity {
    @Override
    public void getData() {
        new HyperShopMicroService(this).orderAdd(getReq())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<HyperShopOrderModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<HyperShopOrderModel> response) {
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
        return "OrderAdd";
    }
}
