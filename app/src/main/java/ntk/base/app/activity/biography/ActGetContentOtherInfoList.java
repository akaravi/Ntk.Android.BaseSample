package ntk.base.app.activity.biography;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import ntk.android.base.api.biography.model.BiographyContentOtherInfoRequest;
import ntk.android.base.api.biography.model.BiographyContentOtherInfoResponse;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActGetContentOtherInfoList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    private List<String> sort_type = new ArrayList<String>();
    private int sort_Type_posistion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biography_get_content_other_info_list);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("BiographyContentOtherInfoList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("BiographyContentOtherInfoList");
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

    private void getData() {
        BiographyContentOtherInfoRequest request = new BiographyContentOtherInfoRequest();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();

        RetrofitManager manager = new RetrofitManager(ActGetContentOtherInfoList.this);
        IBiography iBiography = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<BiographyContentOtherInfoResponse> call = iBiography.GetContentOtherInfoList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyContentOtherInfoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BiographyContentOtherInfoResponse response) {
                        JsonDialog cdd = new JsonDialog(ActGetContentOtherInfoList.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActGetContentOtherInfoList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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