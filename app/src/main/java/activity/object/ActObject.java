package activity.object;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.base.app.R;

public class ActObject extends AppCompatActivity {
    public static String LAYOUT_VALUE = "LAYOUT_VALUE";
    @BindView(R.id.api_recycler_view)
    RecyclerView apiRecyclerView;
    private String[] articleList = new String[]{
            "Object Group List",
            "Object History Add",
            "Object History List",
            "Object History View",
            "Object Property Add",
            "Object Property List",
            "Object Property View",
            "Object Property ViewByJoinId",
            "Object PropertyDetail List",
            "Object PropertyDetailGroup List",
            "Object PropertyDetailValue List",
            "Object PropertySite List",
            "Object PropertyType List",
            "Object User List",
            "Object UserGroup List",
            "Object UserSite AddeByJoinId",
            "Object UserSite List"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_news);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Object");
        apiRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        apiRecyclerView.setAdapter(new ApiRecyclerViewAdapter(this, articleList));
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
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public class ApiRecyclerViewAdapter extends RecyclerView.Adapter<ApiRecyclerViewAdapter.ApiViewHolder> {
        private Context context;
        private String[] list;

        ApiRecyclerViewAdapter(Context context, String[] list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ApiRecyclerViewAdapter.ApiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ApiRecyclerViewAdapter.ApiViewHolder(LayoutInflater.from(context).inflate(R.layout.button_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ApiRecyclerViewAdapter.ApiViewHolder apiViewHolder, int i) {
            apiViewHolder.button.setText(list[i]);
            apiViewHolder.button.setId(i);
            apiViewHolder.button.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case 0:
                            startActivity(new Intent(ActObject.this, ActGetGroupList.class));
                            break;
                        case 1:
                            startActivity(new Intent(ActObject.this, ActSetHistoryAdd.class));
                            break;
                        case 2:
                            startActivity(new Intent(ActObject.this, ActGetHistoryList.class));
                            break;
                        case 3:
                            startActivity(new Intent(ActObject.this, ActGetHistoryView.class));
                            break;

                        case 4:
                            startActivity(new Intent(ActObject.this, ActSetPropertyAdd.class));
                            break;
                        case 5:
                            startActivity(new Intent(ActObject.this, ActGetPropertyList.class));
                            break;
                        case 6:
                            startActivity(new Intent(ActObject.this, ActGetPropertyView.class));
                            break;
                        case 7:
                            startActivity(new Intent(ActObject.this, ActGetPropertyViewByJoinId.class));
                            break;


                        case 8:
                            startActivity(new Intent(ActObject.this, ActGetPropertyDetailList.class));
                            break;
                        case 9:
                            startActivity(new Intent(ActObject.this, ActGetPropertyDetailGroupList.class));
                            break;
                        case 10:
                            startActivity(new Intent(ActObject.this, ActGetPropertyDetailValueList.class));
                            break;


                        case 11:
                            startActivity(new Intent(ActObject.this, ActGetPropertySiteList.class));
                            break;
                        case 12:
                            startActivity(new Intent(ActObject.this, ActGetPropertyTypeList.class));
                            break;



                        case 13:
                            startActivity(new Intent(ActObject.this, ActGetUserList.class));
                            break;
                        case 14:
                            startActivity(new Intent(ActObject.this, ActGetUserGroupList.class));
                            break;
                        case 15:
                            startActivity(new Intent(ActObject.this, ActSetUserSiteAddeByJoinId.class));
                            break;
                        case 16:
                            startActivity(new Intent(ActObject.this, ActGetUserSiteList.class));
                            break;

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.length;
        }

        class ApiViewHolder extends RecyclerView.ViewHolder {
            private Button button;

            ApiViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button_item);
            }
        }
    }
}
