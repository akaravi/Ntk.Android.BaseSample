package activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

import activity.application.ActApplication;
import activity.article.ActArticle;
import activity.biography.ActBiography;
import activity.blog.ActBlog;
import activity.core.ActCore;
import activity.estate.ActEstate;
import activity.file.ActFile;
import activity.member.ActMember;
import activity.news.ActNews;
import activity.object.ActObject;
import activity.pooling.ActPooling;
import activity.product.ActProduct;
import activity.ticketing.ActTicket;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.ConfigRestHeader;
import config.ConfigStaticValue;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.base.api.core.interfase.ICore;
import ntk.base.api.core.entity.CoreMain;
import ntk.base.api.core.model.MainCoreResponse;
import ntk.base.config.RetrofitManager;
import ntk.base.app.BuildConfig;
import ntk.base.app.R;
import utill.AppUtill;
import utill.EasyPreference;
import utill.FontManager;

public class Main extends AppCompatActivity {


    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    @BindView(R.id.txtUrl)
    EditText url;

    @BindView(R.id.txtPackageName)
    EditText packageName;
private String urlServerApi;
    private String[] apiNames = new String[]{
            "Core",
            "Article",
            "News",
            "Pooling",
            "Ticketing",
            "Biography",
            "Application",
            "Estate",
            "File",
            "Blog",
            "Product",
            "Chart",
            "Link Management",
            "Reservation",
            "Bank payment",
            "Member",
            "Job",
            "Advertisement",
            "Vehicle",
            "Object",
            "Shop"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        urlServerApi="https://apicms.ir";
        String ApiBaseUrl_ = EasyPreference.with(this).getString("ApiBaseUrl", urlServerApi);
        String packageName_ = EasyPreference.with(this).getString("packageName", "ntk.cms.android.basesample.app");
        url.setText(ApiBaseUrl_);
        packageName.setText(packageName_);

        EasyPreference.with(this).addString("ApiBaseUrl", ApiBaseUrl_);
        EasyPreference.with(this).addString("packageName", packageName_);

        mainRecyclerView.setLayoutManager(new GridLayoutManager(Main.this, 2));
        mainRecyclerView.setAdapter(new MainRecyclerViewAdapter(this, apiNames));
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EasyPreference.with(Main.this).remove("ApiBaseUrl");
                EasyPreference.with(Main.this).addString("ApiBaseUrl", s.toString());
            }
        });

        packageName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EasyPreference.with(Main.this).remove("packageName");
                EasyPreference.with(Main.this).addString("packageName", s.toString());
            }
        });
    }

    public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder> {
        private Context context;
        private String[] list;

        MainRecyclerViewAdapter(Context context, String[] list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.button_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
            mainViewHolder.button.setText(list[i]);
            mainViewHolder.button.setId(i);
            mainViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case 0:
                            startActivity(new Intent(context, ActCore.class));
                            break;
                        case 1:
                            startActivity(new Intent(context, ActArticle.class));
                            break;
                        case 2:
                            startActivity(new Intent(context, ActNews.class));
                            break;
                        case 3:
                            startActivity(new Intent(context, ActPooling.class));
                            break;
                        case 4:
                            startActivity(new Intent(context, ActTicket.class));
                            break;
                        case 5:
                            startActivity(new Intent(context, ActBiography.class));
                            break;
                        case 6:
                            startActivity(new Intent(context, ActApplication.class));
                            break;
                        case 7:
                            startActivity(new Intent(context, ActEstate.class));
                            break;
                        case 8:
                            startActivity(new Intent(context, ActFile.class));
                            break;
                        case 9:
                            startActivity(new Intent(context, ActBlog.class));
                            break;
                        case 10:
                            startActivity(new Intent(context, ActProduct.class));
                            break;
                        case 11:
                            //Chart
                            break;
                        case 12:
                            //LinkManager
                            break;
                        case 13:
                            //reservition
                            break;
                        case 14:
                            //Bank Payment
                            break;
                        case 15:
                            startActivity(new Intent(context, ActMember.class));
                            break;
                        case 16:
                            //job
                            break;
                        case 17:
                            //Advertisement
                            break;
                        case 18:
                            //Vehicle
                            break;
                        case 19:
                            startActivity(new Intent(context, ActObject.class));
                            break;
                        case 20:
                            //Shop
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.length;
        }

        class MainViewHolder extends RecyclerView.ViewHolder {
            private Button button;

            MainViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button_item);
            }
        }
    }

    @OnClick(R.id.defaultValueBtn)
    public void onDefaultValueBtnClick(){

        EasyPreference.with(Main.this).remove("ApiBaseUrl");
        EasyPreference.with(Main.this).addString("ApiBaseUrl",urlServerApi);

        EasyPreference.with(Main.this).remove("packageName");
        EasyPreference.with(Main.this).addString("packageName","ntk.cms.android.basesample.app");
        url.setText(urlServerApi);
        packageName.setText("ntk.cms.android.basesample.app");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (EasyPreference.with(this).getString("configapp", "").isEmpty()) {
            HandelData();
        }

    }

    private void HandelData() {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager manager = new RetrofitManager(this);
            ICore iCore = manager.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(ICore.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            Observable<MainCoreResponse> observable = iCore.GetResponseMain(headers);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<MainCoreResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(MainCoreResponse mainCoreResponse) {
                            EasyPreference.with(Main.this).addString("configapp", new Gson().toJson(mainCoreResponse.Item));
                            CheckUpdate();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            CheckUpdate();
        }
    }

    private void CheckUpdate() {
        String st = EasyPreference.with(this).getString("configapp", "");
        CoreMain mcr = new Gson().fromJson(st, CoreMain.class);
        if (mcr.AppVersion > BuildConfig.VERSION_CODE && BuildConfig.APPLICATION_ID.indexOf(".APPNTK") <0) {
            if (mcr.AppForceUpdate) {
                UpdateFore();
            } else {
                Update();
            }
        }
    }

    private void Update() {
        String st = EasyPreference.with(this).getString("configapp", "");
        CoreMain mcr = new Gson().fromJson(st, CoreMain.class);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_permission);
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialog)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialog)).setText("توجه");
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialog)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialog)).setText("نسخه جدید اپلیکیشن اومده دوست داری آبدیت بشه؟؟");
        Button Ok = (Button) dialog.findViewById(R.id.btnOkPermissionDialog);
        Ok.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Ok.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mcr.AppUrl));
            startActivity(i);
            dialog.dismiss();
        });
        Button Cancel = (Button) dialog.findViewById(R.id.btnCancelPermissionDialog);
        Cancel.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Cancel.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
    }

    private void UpdateFore() {
        String st = EasyPreference.with(this).getString("configapp", "");
        CoreMain mcr = new Gson().fromJson(st, CoreMain.class);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_update);
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialogUpdate)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialogUpdate)).setText("توجه");
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialogUpdate)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialogUpdate)).setText("نسخه جدید اپلیکیشن اومده حتما باید آبدیت بشه");
        Button Ok = (Button) dialog.findViewById(R.id.btnOkPermissionDialogUpdate);
        Ok.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Ok.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mcr.AppUrl));
            startActivity(i);
            dialog.dismiss();
        });
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    finish();
            }
            return true;
        });
        dialog.show();
    }

}
