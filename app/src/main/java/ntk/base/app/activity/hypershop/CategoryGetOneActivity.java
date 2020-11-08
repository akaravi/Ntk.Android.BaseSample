package ntk.base.app.activity.hypershop;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.dtomodel.hypershop.HyperShopCategoryModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.services.hypershop.HyperShopMicroService;
import ntk.base.app.activity.AbstractByIdActivity;

public class CategoryGetOneActivity extends AbstractByIdActivity {
    @Override
    public void getData() {
        new HyperShopMicroService(this).getOneCategory(getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<HyperShopCategoryModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<HyperShopCategoryModel> response) {
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
        return "CategoryGetOne";
    }
}
