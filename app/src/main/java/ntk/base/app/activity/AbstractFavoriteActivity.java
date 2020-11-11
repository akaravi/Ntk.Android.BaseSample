package ntk.base.app.activity;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorExceptionBase;
import ntk.base.app.R;

public abstract class AbstractFavoriteActivity extends AbstractActivity {

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
        return R.layout.activity_base_favorite_add_remove;
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
        long Id;
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarAdd.setVisibility(View.GONE);
                return;
            } else {
                Id = Long.parseLong(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarAdd.setVisibility(View.GONE);
            return;
        }
        favoriteAdd(Id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NtkObserver<ErrorExceptionBase>() {
                    @Override
                    public void onNext(@NonNull ErrorExceptionBase errorExceptionBase) {
                        showResult(errorExceptionBase);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }

    protected abstract Observable<ErrorExceptionBase> favoriteAdd(long id);

    private void removeFavorite() {
        long Id;
        if (!txtIdAddOrRemove.getText().toString().matches("")) {
            if (txtIdAddOrRemove.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtIdAddOrRemove.setError("InValid Info !!");
                progressBarRemove.setVisibility(View.GONE);
                return;
            } else {
                Id = Long.valueOf(txtIdAddOrRemove.getText().toString());
            }
        } else {
            txtIdAddOrRemove.setError("Require !!");
            progressBarRemove.setVisibility(View.GONE);
            return;
        }
        favoriteRemove(Id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NtkObserver<ErrorExceptionBase>() {
                    @Override
                    public void onNext(@NonNull ErrorExceptionBase errorExceptionBase) {
                        showResult(errorExceptionBase);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showError(e);
                    }
                });
    }

    protected abstract Observable<ErrorExceptionBase> favoriteRemove(long id);

}
