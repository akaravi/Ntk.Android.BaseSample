package ntk.base.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.codekidlabs.storagechooser.StorageChooser;

import java.util.HashMap;
import java.util.Map;

import config.ConfigStaticValue;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.base.api.news.interfase.INews;
import ntk.base.api.news.model.NewsContentListRequest;
import ntk.base.api.news.model.NewsContentResponse;
import ntk.base.config.RetrofitManager;
import okhttp3.ResponseBody;

public class ActMain extends AppCompatActivity {
    private ConfigStaticValue configStaticValue =new ConfigStaticValue(this);
    private static final int ACTIVITY_CHOOSE_FILE = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        init();
    }

    private void init() {
        TextView textViewLog = findViewById(R.id.textViewLog);
        textViewLog.setText(".......");

        Button buttonApiNewsContentList = findViewById(R.id.buttonApiNewsContentList);
        buttonApiNewsContentList.setOnClickListener(view -> {
            textViewLog.setText(".......");

            NewsContentListRequest request = new NewsContentListRequest();
            request.RowPerPage = 20;

            RetrofitManager manager = new RetrofitManager(ActMain.this);
            INews iNews = manager.getRetrofit(configStaticValue.GetApiBaseUrl()).create(INews.class);
            Map<String, String> headers = new HashMap<>();
            headers.put("layout", "newscontentlist");

            Observable<NewsContentResponse> call = iNews.GetContentList(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<NewsContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(NewsContentResponse newsContentResponse) {
                            Log.i("Ok", "ApiOK");
                            textViewLog.setText("OK");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("Error", e.getMessage());
                            textViewLog.setText("Error :" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        Button buttonApiUploadFile = findViewById(R.id.buttonApiUploadFile);
        buttonApiUploadFile.setOnClickListener(view -> {
            textViewLog.setText(".......");

            StorageChooser chooser = new StorageChooser.Builder()
                    .withActivity(this)
                    .allowCustomPath(true)
                    .setType(StorageChooser.FILE_PICKER)
                    .disableMultiSelect()
                    .withMemoryBar(true)
                    .withFragmentManager(getFragmentManager())
                    .build();

// Show dialog whenever you want by
            chooser.show();

// get path that the user has chosen
            chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                @Override
                public void onSelect(String path) {
                    Log.e("SELECTED_PATH", path);
                    UploadFile(path);
                }
            });
        });
    }


    private void UploadFile(String fileUri) {
        TextView textViewLog = findViewById(R.id.textViewLog);


        Map<String, String> headers = new HashMap<>();
        headers.put("layout", "newscontentlist");
        headers.put("packagename", "ntk.cms.vitrin.app");

        RetrofitManager manager = new RetrofitManager(ActMain.this);
        Observable<ResponseBody> observable = manager.FileUpload("", fileUri, headers);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody s) {
                        Log.i("Ok", s.toString());
                        textViewLog.setText("Ok : " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error : ", e.getMessage());
                        textViewLog.setText("Error :" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}