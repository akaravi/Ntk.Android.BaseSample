package ntk.base.app.activity.news;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import ntk.android.base.api.news.model.NewsContentFavoriteAddRequest;
import ntk.android.base.api.news.model.NewsContentFavoriteRemoveRequest;
import ntk.android.base.entitymodel.base.ErrorExceptionBase;
import ntk.android.base.services.news.NewsContentService;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractActivity;
import ntk.base.app.activity.AbstractFavoriteActivity;

public class ContentFavoriteAddOrRemoveActivity extends AbstractFavoriteActivity {

    @Override
    protected String getTitleName() {
        return "NewsContentFavoriteAdd";
    }


    @Override
    public void getData() {
        //not need to implement
    }

    @Override
    protected  Observable<ErrorExceptionBase> favoriteAdd(long id) {
       return new NewsContentService(this).addFavorite(id);
    }

    @Override
    protected  Observable<ErrorExceptionBase> favoriteRemove(long id) {
        return new NewsContentService(this).removeFavorite(id);
    }
}
