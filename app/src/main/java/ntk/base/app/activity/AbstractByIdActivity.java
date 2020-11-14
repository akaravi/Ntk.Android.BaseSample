package ntk.base.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ntk.base.app.R;
import ntk.base.app.dialog.JsonDialog;

public abstract class AbstractByIdActivity extends AppCompatActivity {
    @BindView(R.id.api_test_submit_button)
    Button apiTestSubmitButton;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.lblLayout)
    TextView lblLayout;
    @BindView(R.id.txtId)
    EditText txtId;

    protected abstract String getTitleName();

    @OnClick(R.id.api_test_submit_button)
    public void onSubmitClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        getData();
    }

    public abstract void getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_by_id);
        ButterKnife.bind(this);
        initialize();

    }

    private void initialize() {
        lblLayout.setText(getTitleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getTitleName());
    }

    public String getId() {
        return txtId.getText().toString();
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
