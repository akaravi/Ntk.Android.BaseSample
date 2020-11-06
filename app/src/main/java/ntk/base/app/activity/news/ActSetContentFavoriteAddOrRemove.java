package ntk.base.app.activity.news;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import ntk.android.base.api.news.model.NewsContentFavoriteAddRequest;
import ntk.android.base.api.news.model.NewsContentFavoriteRemoveRequest;
import ntk.base.app.R;
import ntk.base.app.activity.AbstractActivity;

public class ActSetContentFavoriteAddOrRemove extends AbstractActivity {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.progressBarAdd)
    ProgressBar progressBarAdd;
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.progressBarRemove)
    ProgressBar progressBarRemove;
    @BindView(R.id.txtIdAddOrRemove)
    EditText txtIdAddOrRemove;

    @Override
    protected int layout() {
        return R.layout.act_news_set_content_favorite_add_or_remove;
    }


    @Override
    protected String getTitleName() {
        return "NewsContentFavoriteAdd";
    }

    @OnClick(R.id.btnRemove)
    public void remove() {
        progressBarRemove.setVisibility(View.VISIBLE);
        removeFavorite();
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        progressBarAdd.setVisibility(View.VISIBLE);
        addFavorite();
    }

    private void addFavorite() {
        NewsContentFavoriteAddRequest request = new NewsContentFavoriteAddRequest();
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarAdd.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.valueOf(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarAdd.setVisibility(View.GONE);
            return;
        }
        //todo karavi ask
    }

    private void removeFavorite() {
        NewsContentFavoriteRemoveRequest request = new NewsContentFavoriteRemoveRequest();
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarRemove.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.valueOf(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarRemove.setVisibility(View.GONE);
            return;
        }

    }


    @Override
    public void getData() {
        //not need to implement
    }
}
