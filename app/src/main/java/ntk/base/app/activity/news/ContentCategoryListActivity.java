package ntk.base.app.activity.news;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.entity.NewsCategory;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.Filters;
import ntk.android.base.services.news.NewsCategoryService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractFilterModelingActivity;


//same as base extra Link Content Id
public class ContentCategoryListActivity extends AbstractFilterModelingActivity {


    @BindView(R.id.txtLinkContentId)
    EditText txtLinkContentId;


    @Override
    protected Integer extraInflateId() {
        return R.layout.activity_news_content_category_list;
    }

    public void getData() {

        FilterDataModel request = getReq();
        long LinkContentId = 0;
        if (!txtLinkContentId.getText().toString().matches("")) {
            if (txtLinkContentId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtLinkContentId.setError("inValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                txtLinkContentId.setError(null);
                LinkContentId = Long.parseLong(txtLinkContentId.getText().toString());
            }
        } else {
            txtLinkContentId.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (LinkContentId > 0) {
            Filters f = new Filters();
            f.PropertyName = "LinkContentId";
            f.IntValue1 = LinkContentId;
            request.addFilter(f);
        }
        new NewsCategoryService(this).getAll(request).observeOn(AndroidSchedulers.mainThread())
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

    @Override
    protected String getTitleName() {
        return "NewsContentCategoryList";
    }

}
