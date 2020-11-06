package ntk.base.app.activity.member;

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

public class ActMember extends AppCompatActivity {
    public static String LAYOUT_VALUE = "LAYOUT_VALUE";
    @BindView(R.id.api_recycler_view)
    RecyclerView apiRecyclerView;
    private String[] articleList = new String[]{
            "Member Group List",
            "Member History Add",
            "Member History List",
            "Member History View",
            "Member Property Add",
            "Member Property List",
            "Member Property View",
            "Member Property ViewByJoinId",
            "Member PropertyDetail List",
            "Member PropertyDetailGroup List",
            "Member PropertyDetailValue List",
            "Member PropertySite List",
            "Member PropertyType List",
            "Member User Add",
            "Member User List",
            "Member UserGroup List",
            "Member UserSite AddeByJoinId",
            "Member UserSite List"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Member");
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
                            startActivity(new Intent(ActMember.this, ActGetGroupList.class));
                            break;
                        case 1:
                            startActivity(new Intent(ActMember.this, ActSetHistoryAdd.class));
                            break;
                        case 2:
                            startActivity(new Intent(ActMember.this, ActGetHistoryList.class));
                            break;
                        case 3:
                            startActivity(new Intent(ActMember.this, ActGetHistoryView.class));
                            break;

                        case 4:
                            startActivity(new Intent(ActMember.this, ActSetPropertyAdd.class));
                            break;
                        case 5:
                            startActivity(new Intent(ActMember.this, ActGetPropertyList.class));
                            break;
                        case 6:
                            startActivity(new Intent(ActMember.this, ActGetPropertyView.class));
                            break;
                        case 7:
                            startActivity(new Intent(ActMember.this, ActGetPropertyViewByJoinId.class));
                            break;


                        case 8:
                            startActivity(new Intent(ActMember.this, ActGetPropertyDetailList.class));
                            break;
                        case 9:
                            startActivity(new Intent(ActMember.this, ActGetPropertyDetailGroupList.class));
                            break;
                        case 10:
                            startActivity(new Intent(ActMember.this, ActGetPropertyDetailValueList.class));
                            break;


                        case 11:
                            startActivity(new Intent(ActMember.this, ActGetPropertySiteList.class));
                            break;
                        case 12:
                            startActivity(new Intent(ActMember.this, ActGetPropertyTypeList.class));
                            break;


                        case 13:
                            startActivity(new Intent(ActMember.this, ActSetUserAdd.class));
                            break;
                        case 14:
                            startActivity(new Intent(ActMember.this, ActGetUserList.class));
                            break;
                        case 15:
                            startActivity(new Intent(ActMember.this, ActGetUserGroupList.class));
                            break;
                        case 16:
                            startActivity(new Intent(ActMember.this, ActSetUserSiteAddeByJoinId.class));
                            break;
                        case 17:
                            startActivity(new Intent(ActMember.this, ActGetUserSiteList.class));
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
