package ntk.base.app.activity.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.news.NewsCommentModel;
import ntk.android.base.services.news.NewsCommentService;
import ntk.base.app.activity.AbstractByIdActivity;

//net to be content view
public class CommentViewActivity extends AbstractByIdActivity {


    @Override
    protected String getTitleName() {
        return "NewsCommentView";
    }

    @Override
    public void getData() {
        new NewsCommentService(this).getOne(Long.valueOf(getId()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<NewsCommentModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<NewsCommentModel> newsCommentModelErrorException) {
                        showResult(newsCommentModelErrorException);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }
}
