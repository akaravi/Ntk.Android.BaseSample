package ntk.base.app.activity.biography;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ntk.base.app.config.ConfigRestHeader;
import ntk.base.app.config.ConfigStaticValue;
import ntk.base.app.dialog.JsonDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.api.biography.interfase.IBiography;
import ntk.android.base.api.biography.model.BiographyContentResponse;
import ntk.android.base.api.biography.model.BiographyContentWithSimilarDatePeriodStartListRequest;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActContentWithSimilarDatePeriodStartList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.lblLayout)
    TextView lblLayout;
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
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tag_id_text)
    EditText tagIdText;
    @BindView(R.id.add_button)
    Button addButton;
    @BindView(R.id.SearchDayMin)
    EditText SearchDayMin;
    @BindView(R.id.SearchDayMax)
    EditText SearchDayMax;

    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    private List<String> sort_type = new ArrayList<String>();
    private int sort_Type_posistion;
    private List<Long> TagIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biography_content_with_similar_date_period_start_list);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("BiographyContentWithSimilarDatePeriodStartList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("BiographyContentWithSimilarDatePeriodStartList");
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
    }

    @OnClick(R.id.add_button)
    public void onAddClick(View v) {
        try {
            TagIds.add(Long.valueOf(tagIdText.getText().toString()));
            tagIdText.setText("");
        } catch (Exception e) {
            tagIdText.setError("inValid Info !!");
        }
    }

    private void getData() {
        BiographyContentWithSimilarDatePeriodStartListRequest request = new BiographyContentWithSimilarDatePeriodStartListRequest();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        if (!TagIds.isEmpty()) {
            request.TagIds = TagIds;
        }
        if (!SearchDayMax.getText().toString().matches("")) {
            if (SearchDayMax.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                progressBar.setVisibility(View.GONE);
                SearchDayMax.setError("Invalid Info !!");
                return;
            } else {
                request.SearchDayMax = Integer.valueOf(SearchDayMax.getText().toString());
            }
        }
        if (!SearchDayMin.getText().toString().matches("")) {
            if (SearchDayMin.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                progressBar.setVisibility(View.GONE);
                SearchDayMin.setError("Invalid Info !!");
                return;
            } else {
                request.SearchDayMin = Integer.valueOf(SearchDayMin.getText().toString());
            }
        }

        RetrofitManager manager = new RetrofitManager(ActContentWithSimilarDatePeriodStartList.this);
        IBiography iBiography = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<BiographyContentResponse> call = iBiography.GetContentWithSimilarDatePeriodStartList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BiographyContentResponse response) {
                        JsonDialog cdd = new JsonDialog(ActContentWithSimilarDatePeriodStartList.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActContentWithSimilarDatePeriodStartList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

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
