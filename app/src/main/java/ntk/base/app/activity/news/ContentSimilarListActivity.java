package ntk.base.app.activity.news;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.Filters;
import ntk.android.base.entitymodel.news.NewsContentSimilarModel;
import ntk.android.base.services.news.NewsContentSimilarService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractFilterModelingActivity;

public class ContentSimilarListActivity extends AbstractFilterModelingActivity {


    @BindView(R.id.txtLinkContentId)
    EditText txtLinkContentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        extraInflate();
    }

    @Override
    protected Integer extraInflateId() {
        return R.layout.activity_news_content_similar_list;
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
        new NewsContentSimilarService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsContentSimilarModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsContentSimilarModel> resposne) {
                        showResult(resposne);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });

    }

    @Override
    protected String getTitleName() {
        return "NewsContentSimilarList";
    }


}
