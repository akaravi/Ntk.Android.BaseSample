package activity.imageGallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.ConfigRestHeader;
import config.ConfigStaticValue;
import dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.base.api.imageGallery.interfase.IImageGallery;
import ntk.base.api.imageGallery.model.ImageGalleryCommentResponse;
import ntk.base.api.imageGallery.model.ImageGalleryCommentViewRequest;
import ntk.base.api.imageGallery.model.ImageGalleryContentCategoryListRequest;
import ntk.base.api.imageGallery.model.ImageGalleryContentResponse;
import ntk.base.api.news.interfase.INews;
import ntk.base.api.news.model.NewsCommentResponse;
import ntk.base.api.news.model.NewsCommentViewRequest;
import ntk.base.api.utill.RetrofitManager;
import ntk.base.app.R;

public class ActCommentView extends AppCompatActivity {
    @BindView(R.id.txtPackageName)
    EditText txtPackageName;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtId)
    EditText txtId;
    @BindView(R.id.txtActionClientOrder)
    EditText txtActionClientOrder;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_gallery_comment_view);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("ImageGalleryContentCategoryList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ImageGalleryContentCategoryList");
    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        ImageGalleryCommentViewRequest request = new ImageGalleryCommentViewRequest();
        if (!txtId.getText().toString().matches("")) {
            if (txtId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtId.setError("Invalid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.Id = Long.valueOf(txtId.getText().toString());
            }
        } else {
            txtId.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!txtActionClientOrder.getText().toString().matches("")) {
            if (txtActionClientOrder.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtActionClientOrder.setError("Invalid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                request.ActionClientOrder = Integer.valueOf(txtActionClientOrder.getText().toString());
            }
        } else {
            txtActionClientOrder.setError("Required !!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        RetrofitManager manager = new RetrofitManager(ActCommentView.this);
        IImageGallery iImageGallery = manager.getRetrofit(configStaticValue.ApiBaseUrl).create(IImageGallery.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);
        headers.put("PackageName", txtPackageName.getText().toString());

        Observable<ImageGalleryCommentResponse> call = iImageGallery.GetCommentView(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ImageGalleryCommentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ImageGalleryCommentResponse response) {
                        JsonDialog cdd = new JsonDialog(ActCommentView.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActCommentView.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this, ActImageGallery.class));
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, ActImageGallery.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
