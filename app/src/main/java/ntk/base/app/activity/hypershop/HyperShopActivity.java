package ntk.base.app.activity.hypershop;

import android.content.Intent;
import android.view.View;

import ntk.base.app.activity.AbstractApiListActivity;

public class HyperShopActivity extends AbstractApiListActivity {
    @Override
    protected String[] apiList() {
        return new String[]{
                "ContentGetAll",
                "ContentGetOne",
                "CategoryGetAll",
                "CategoryGetOne",
                "OrderAdd",
        };
    }

    @Override
    protected void chooseActivity(View view) {
        switch (view.getTag().toString()) {
            case "ContentGetAll":
                startActivity(new Intent(HyperShopActivity.this, ContentGetAllActivity.class));
                break;
            case "ContentGetOne":
                startActivity(new Intent(HyperShopActivity.this, ContentGetOneActivity.class));
                break;
            case "CategoryGetAll":
                startActivity(new Intent(HyperShopActivity.this, CategoryGetAllActivity.class));
                break;
            case "CategoryGetOne":
                startActivity(new Intent(HyperShopActivity.this, CategoryGetOneActivity.class));
                break;
            case "OrderAdd":
                startActivity(new Intent(HyperShopActivity.this,OrderAddActivity.class));

        }
    }

    @Override
    protected String getTitleName() {
        return "HyperShop";
    }
}
