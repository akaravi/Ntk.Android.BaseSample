package ntk.base.app.activity.news;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.news.NewsContentModel;
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractByIdActivity;

//can do with base with extra inflate
public class ContentViewActivity extends AbstractByIdActivity {

    @BindView(R.id.txtId)
    EditText txtId;
    private List<Long> TagIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected String getTitleName() {
        return "NewsContentView";
    }

    @Override
    public void getData() {
        new NewsContentService(this).getOne(Long.valueOf(getId()))
                .observeOn(AndroidSchedulers.mainThread())
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
}
