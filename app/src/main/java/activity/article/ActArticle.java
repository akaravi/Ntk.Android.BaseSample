package activity.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import activity.Main;
import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.base.app.R;


public class ActArticle extends AppCompatActivity {

    public static String LAYOUT_VALUE = "LAYOUT_VALUE";
    private String[] articleList = new String[]{"ActArticle Content List",
            "ActArticle Content View",
            "ActArticle Tag List",
            "ActArticle Category List",
            "ActArticle Category Tag List",
            "ActArticle Content Other Info List",
            "ActArticle Comment List",
            "ActArticle Comment Add",
            "ActArticle Content Favorite Add And Remove",
            "ActArticle Comment View",
            "ActArticle Content Favorite List",
            "ArticleContentSimilarList",
            "ArticleContentCategoryList"};
    @BindView(R.id.api_recycler_view)
    RecyclerView apiRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Article");
        apiRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        apiRecyclerView.setAdapter(new ApiRecyclerViewAdapter(this, articleList));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, Main.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this, Main.class));
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
        public ApiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ApiViewHolder(LayoutInflater.from(context).inflate(R.layout.button_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ApiViewHolder apiViewHolder, int i) {
            apiViewHolder.button.setText(list[i]);
            apiViewHolder.button.setId(i);
            apiViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case 0:
                            startActivity(new Intent(ActArticle.this, GetArticleContentList.class).putExtra(LAYOUT_VALUE, "ArticleContentList"));
                            finish();
                            break;
                        case 1:
                            startActivity(new Intent(ActArticle.this, GetArticleContentView.class).putExtra(LAYOUT_VALUE, "ArticleContentView"));
                            finish();
                            break;
                        case 2:
                            startActivity(new Intent(ActArticle.this, GetArticleTagList.class).putExtra(LAYOUT_VALUE, "ArticleTagList"));
                            finish();
                            break;
                        case 3:
                            startActivity(new Intent(ActArticle.this, GetArticleCategoryList.class).putExtra(LAYOUT_VALUE, "ArticleCategoryList"));
                            finish();
                            break;
                        case 4:
                            startActivity(new Intent(ActArticle.this, GetArticleCategoryTagList.class).putExtra(LAYOUT_VALUE, "ArticleCategoryTagList"));
                            finish();
                            break;
                        case 5:
                            startActivity(new Intent(ActArticle.this, GetArticleContentOtherInfoList.class).putExtra(LAYOUT_VALUE, "ArticleContentOtherInfoList"));
                            finish();
                            break;
                        case 6:
                            startActivity(new Intent(ActArticle.this, GetArticleCommentList.class).putExtra(LAYOUT_VALUE, "ArticleCommentList"));
                            finish();
                            break;
                        case 7:
                            startActivity(new Intent(ActArticle.this, GetArticleCommentAdd.class).putExtra(LAYOUT_VALUE, "ArticleCommentAddRequest"));
                            finish();
                            break;
                        case 8:
                            startActivity(new Intent(ActArticle.this, GetArticleContentFavoriteAddOrRemove.class).putExtra(LAYOUT_VALUE, "ArticleContentFavoriteAddRequest"));
                            finish();
                            break;
                        case 9:
                            startActivity(new Intent(ActArticle.this, ArticleCommentViewActivity.class).putExtra(LAYOUT_VALUE, "ArticleCommentView"));
                            finish();
                            break;
                        case 10:
                            startActivity(new Intent(ActArticle.this, ArticleContentFavoriteListActivity.class).putExtra(LAYOUT_VALUE, "ArticleContentFavoriteList"));
                            finish();
                            break;
                        case 11:
                            startActivity(new Intent(ActArticle.this, ActContentSimilarList.class));
                            finish();
                            break;
                        case 12:
                            startActivity(new Intent(ActArticle.this, ActContentCategoryList.class));
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
