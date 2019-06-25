package activity.blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import activity.Main;
import activity.core.ActGetResponseMain;
import activity.file.ActFile;
import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.base.app.R;

public class ActBlog extends AppCompatActivity {

    @BindView(R.id.api_recycler_view)
    RecyclerView apiRecyclerView;
    private String[] articleList = new String[]{"BlogTagList",
            "BlogContentView",
            "BlogContentSimilarList",
            "BlogContentOtherInfoList",
            "BlogContentList",
            "BlogContentFavoriteList",
            "BlogContentCategoryList",
            "BlogCommentView",
            "BlogCommentList",
            "BlogCommentAdd",
            "BlogContentFavoriteAddOrRemove",
            "BlogCategoryList",
            "BlogCategoryTagList"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blog);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Blog");
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
                            startActivity(new Intent(ActBlog.this, ActTagList.class));
                            finish();
                            break;
                        case 1:
                            startActivity(new Intent(ActBlog.this, ActContentView.class));
                            finish();
                            break;
                        case 2:
                            startActivity(new Intent(ActBlog.this, ActContentSimilarList.class));
                            finish();
                            break;
                        case 3:
                            startActivity(new Intent(ActBlog.this, ActContentOtherInfoList.class));
                            finish();
                            break;
                        case 4:
                            startActivity(new Intent(ActBlog.this, ActContentList.class));
                            finish();
                            break;
                        case 5:
                            startActivity(new Intent(ActBlog.this, ActContentFavoriteList.class));
                            finish();
                            break;
                        case 6:
                            startActivity(new Intent(ActBlog.this, ActContentCategoryList.class));
                            finish();
                            break;
                        case 7:
                            startActivity(new Intent(ActBlog.this, ActCommentView.class));
                            finish();
                            break;
                        case 8:
                            startActivity(new Intent(ActBlog.this, ActCommentList.class));
                            finish();
                            break;
                        case 9:
                            startActivity(new Intent(ActBlog.this, ActCommentAdd.class));
                            finish();
                            break;
                        case 10:
                            startActivity(new Intent(ActBlog.this, ActBlogContentFavoriteAddOrRemove.class));
                            finish();
                            break;
                        case 11:
                            startActivity(new Intent(ActBlog.this, ActCategoryList.class));
                            finish();
                            break;
                        case 12:
                            startActivity(new Intent(ActBlog.this, ActCategoryTagList.class));
                            finish();
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
