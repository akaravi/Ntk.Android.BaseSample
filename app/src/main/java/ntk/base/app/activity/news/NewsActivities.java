package ntk.base.app.activity.news;

import android.content.Intent;
import android.view.View;

import ntk.base.app.activity.AbstractApiListActivity;

public class NewsActivities extends AbstractApiListActivity {

    @Override
    protected String[] apiList() {
        return new String[]{"News Content List",
                "News Content View",
                "News Tag List",
                "News Category List",
                "News Category Tag List",
                "News Content Other Info List",
                "News Comment List",
                "News Comment Add",
                "News Comment View",
                "News Content Favorite Add Or Remove",
                "News Content Favorite List",
                "NewsContentSimilarList",
                "NewsContentCategoryList"};
    }

    @Override
    protected void chooseActivity(View view) {
        switch (view.getId()) {
            case 0:
                startActivity(new Intent(NewsActivities.this, ContentListActivity.class));
                break;
            case 1:
                startActivity(new Intent(NewsActivities.this, ContentViewActivity.class));
                break;
            case 2:
                startActivity(new Intent(NewsActivities.this, TagListActivity.class));
                break;
            case 3:
                startActivity(new Intent(NewsActivities.this, CategoryListActivity.class));
                break;
            case 4:
                startActivity(new Intent(NewsActivities.this, CategoryTagListActivity.class));
                break;
            case 5:
                startActivity(new Intent(NewsActivities.this, ContentOtherInfoListActivity.class));
                break;
            case 6:
                startActivity(new Intent(NewsActivities.this, CommentListActivity.class));
                break;
            case 7:
                startActivity(new Intent(NewsActivities.this, SetCommentActivity.class));
                break;
            case 8:
                startActivity(new Intent(NewsActivities.this, CommentViewActivity.class));
                break;
            case 9:
                startActivity(new Intent(NewsActivities.this, ContentFavoriteAddOrRemoveActivity.class));
                break;
            case 10:
                startActivity(new Intent(NewsActivities.this, ContentFavoriteListActivity.class));
                break;
            case 11:
                startActivity(new Intent(NewsActivities.this, ContentSimilarListActivity.class));
                break;
            case 12:
                startActivity(new Intent(NewsActivities.this, ContentCategoryListActivity.class));
                break;
        }
    }

    @Override
    protected String getTitleName() {
        return "News";
    }
}
