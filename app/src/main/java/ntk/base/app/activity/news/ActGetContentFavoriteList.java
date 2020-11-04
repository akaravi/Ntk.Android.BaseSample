package ntk.base.app.activity.news;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.news.interfase.INews;
import ntk.android.base.api.news.model.NewsContentFavoriteListRequest;
import ntk.android.base.api.news.model.NewsContentFavoriteListResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.activity.BaseFilterModelingActivity;
import ntk.base.app.dialog.JsonDialog;

public class ActGetContentFavoriteList extends BaseFilterModelingActivity {


    public void getData() {

    }


    @Override
    protected String getTitleName() {
        return "NewsContentFavoriteList";
    }
}
