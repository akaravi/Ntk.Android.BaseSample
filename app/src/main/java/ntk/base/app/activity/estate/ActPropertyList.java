package ntk.base.app.activity.estate;

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
import ntk.android.base.api.estate.interfase.IEstate;
import ntk.android.base.api.estate.model.EstatePropertyListRequest;
import ntk.android.base.api.estate.model.EstatePropertyListResponse;
import ntk.android.base.api.baseModel.Filters;
import ntk.android.base.config.RetrofitManager;
import ntk.base.app.R;

public class ActPropertyList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    @BindView(R.id.txtLinkContentId)
    EditText txtLinkContentId;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ConfigRestHeader configRestHeader = new ConfigRestHeader();
    private List<String> sort_type = new ArrayList<String>();
    private ConfigStaticValue configStaticValue = new ConfigStaticValue(this);
    private int sort_Type_posistion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_estate_property_list);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        lblLayout.setText("EstatePropertyList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("EstatePropertyList");
        sort_type.add("Descnding_Sort");
        sort_type.add("Ascnding_Sort");
        sort_type.add("Random_Sort");
        sortTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sort_type));
        sortTypeSpinner.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.api_test_submit_button)
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
        EstatePropertyListRequest request = new EstatePropertyListRequest();
        request.RowPerPage = Integer.valueOf(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.valueOf(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.valueOf(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();
        long LinkContentId = 0;
        if (!txtLinkContentId.getText().toString().matches("")) {
            if (txtLinkContentId.getInputType() != InputType.TYPE_CLASS_NUMBER) {
                txtLinkContentId.setError("inValid Info !!");
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                txtLinkContentId.setError(null);
                LinkContentId = Long.valueOf(txtLinkContentId.getText().toString());
            }
        }
        if (LinkContentId > 0) {
            List<Filters> filters = new ArrayList<>();
            Filters f = new Filters();
            f.PropertyName = "LinkContentId";
            f.IntValue1 = LinkContentId;
            filters.add(f);
            request.filters = filters;
        }
        RetrofitManager manager = new RetrofitManager(ActPropertyList.this);
        IEstate iEstate = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(IEstate.class);
        Map<String, String> headers = new HashMap<>();
        headers = configRestHeader.GetHeaders(this);

        Observable<EstatePropertyListResponse> call = iEstate.GetEstatePropertyActList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<EstatePropertyListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(EstatePropertyListResponse response) {
                        JsonDialog cdd = new JsonDialog(ActPropertyList.this, response);
                        cdd.setCanceledOnTouchOutside(false);
                        cdd.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("Error", e.getMessage());
                        Toast.makeText(ActPropertyList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort_Type_posistion = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
