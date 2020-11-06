package ntk.base.app.activity.news;

import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.base.app.activity.BaseFilterModelingActivity;

//need to define favorite api
//same as base
public class ContentFavoriteListActivity extends BaseFilterModelingActivity {


    public void getData() {
        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = Integer.parseInt(rowPerPageText.getText().toString());
        request.SkipRowData = Integer.parseInt(skipRowDataText.getText().toString());
        request.SortType = sort_Type_posistion;
        request.CurrentPageNumber = Integer.parseInt(currentPageNumberText.getText().toString());
        request.SortColumn = sortColumnText.getText().toString();

        //todo add favorite
//        call.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<NewsContentFavoriteListResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(NewsContentFavoriteListResponse response) {
//                        JsonDialog cdd = new JsonDialog(ActGetContentFavoriteList.this, response);
//                        cdd.setCanceledOnTouchOutside(false);
//                        cdd.show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        progressBar.setVisibility(View.GONE);
//                        Log.i("Error", e.getMessage());
//                        Toast.makeText(ActGetContentFavoriteList.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
    }

    @Override
    protected String getTitleName() {
        return "NewsContentFavoriteList";
    }
}
