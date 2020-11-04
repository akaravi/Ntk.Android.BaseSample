package ntk.base.app.activity.news;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.interfase.INews;
import ntk.android.base.api.news.model.NewsCategoryTagRequest;
import ntk.android.base.api.news.model.NewsCategoryTagResponse;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.config.RetrofitManager;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.news.NewsCategoryTagModel;
import ntk.android.base.services.news.NewsCategoryTagService;
import ntk.base.app.activity.BaseFilterModelingActivity;
import ntk.base.app.dialog.JsonDialog;

public class ActGetCategoryTagList extends BaseFilterModelingActivity implements AdapterView.OnItemSelectedListener {


    @Override
    protected String getTitleName() {
        return "CategoryTagList";
    }

    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
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
