package activity.news;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import ntk.android.base.api.news.interfase.INews;
import ntk.android.base.api.news.model.NewsContentListRequest;
import ntk.android.base.api.news.model.NewsContentResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActGetContentList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.txtTag)
    EditText txtTag;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.row_per_page_text)
    EditText rowPerPageText;
    @BindView(R.id.sort_type_spinner)
    Spinner sortTypeSpinner;
    @BindView(R.id.skip_row_data_text)
    EditText skipRowDataText;
    @BindView(R.id.current_page_number_text)
    EditText currentPageNumberText;
    @BindView(R.id.sort_column_text)
    EditText sortColumnText;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private List<Long> TagIds = new ArrayList<>();
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    private List<String> sort_type = new ArrayList<String>();
    private int sort_Type_posistion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_get_content_list);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("NewsContentList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("NewsContentList");
        sort_type.add("Descnding_Sort");
        sort_type.add("Ascnding_Sort");
        sort_type.add("Random_Sort");
        sortTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sort_type));
        sortTypeSpinner.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
//        getDataV2();
    }

    @OnClick(R.id.btnAdd)
    public void onAddClick(View v) {
        try {
            TagIds.add(Long.valueOf(txtTag.getText().toString()));
            txtTag.setText("");
        } catch (Exception e) {
            txtTag.setError("inValid Info !!");
        }
    }

    private void getData() {
        NewsContentListRequest request = new NewsContentListRequest();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        if (!TagIds.isEmpty()) {
            request.TagIds = TagIds;
        }
        RetrofitManager manager = new RetrofitManager(ActGetContentList.this);
        INews iNews = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(INews.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<NewsContentResponse> call = iNews.GetContentList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NewsContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(NewsContentResponse response) {
                        JsonDialog cdd = new JsonDialog(ActGetContentList.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetContentList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

//    private void getDataV2() {
//        ntk.base.entityModel.base.FilterModel request = new ntk.base.entityModel.base.FilterModel();
//        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
//        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
//        request.SortType = sort_Type_posistion;
//        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
//        request.SortColumn = sortColumnText.getText().toString();
//
//
//        Map<String, String> headers = new HashMap<>();
//        headers = configRestHeader.GetHeaders(this);
//        NewsContentService newsContentService = new NewsContentService(this, headers);
//
//
//
//        newsContentService.getAll(request).subscribeOn(Schedulers.io()).subscribe(new Observer<NewsContentResponceModel>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull NewsContentResponceModel response) {
//                JsonDialog cdd = new JsonDialog(ActGetContentList.this, response);
//                cdd.setCanceledOnTouchOutside(false);
//                cdd.show();
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                progressBar.setVisibility(View.GONE);
//                Log.i("Error", e.getMessage());
//                Toast.makeText(ActGetContentList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onComplete() {
//                progressBar.setVisibility(View.GONE);
//
//            }
//        });
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort_Type_posistion = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
