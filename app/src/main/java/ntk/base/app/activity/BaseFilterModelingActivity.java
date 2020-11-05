package ntk.base.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ntk.base.app.R;
import ntk.base.app.dialog.JsonDialog;

public abstract class BaseFilterModelingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.row_per_page_text)
    protected EditText rowPerPageText;
    @BindView(R.id.sort_type_spinner)
    Spinner sortTypeSpinner;
    @BindView(R.id.skip_row_data_text)
    protected EditText skipRowDataText;
    @BindView(R.id.current_page_number_text)
    protected EditText currentPageNumberText;
    @BindView(R.id.sort_column_text)
    protected EditText sortColumnText;
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    private List<String> sort_type = new ArrayList<String>();

    protected int sort_Type_posistion;

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    public abstract void getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base_filtermodeling);
        ButterKnife.bind(this);
        initialize();
    }

    protected void extraInflate(@LayoutRes int layout) {
       ViewStub stub= findViewById(R.id.view_stub);
       stub.setLayoutResource(layout);
       stub.inflate();
    }
    private void initialize() {
        lblLayout.setText(getTitleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getTitleName());
        sort_type.add("Descnding_Sort");
        sort_type.add("Ascnding_Sort");
        sort_type.add("Random_Sort");
        sortTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sort_type));
        sortTypeSpinner.setOnItemSelectedListener(this);
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected abstract String getTitleName();

    public void showResult(Object response) {
        progressBar.setVisibility(View.GONE);
        JsonDialog cdd = new JsonDialog(this, response);
        cdd.setCanceledOnTouchOutside(false);
        cdd.show();
    }

    public void showError(Throwable e) {
        progressBar.setVisibility(View.GONE);
        Log.i("Error", e.getMessage());
        Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
