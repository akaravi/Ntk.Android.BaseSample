package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import activity.blog.ActBlog;
import activity.estate.ActEstate;
import activity.file.ActFile;
import activity.imageGallery.ActImageGallery;
import activity.movieGallery.ActMovieGallery;
import activity.musicGallery.ActMusicGallery;
import activity.product.ActProduct;
import activity.service.ActService;
import activity.ticketing.ActTicket;
import activity.application.ActApplication;
import activity.article.ActArticle;
import activity.biography.ActBiography;
import activity.core.ActCore;
import activity.news.ActNews;
import activity.pooling.ActPooling;
import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.base.app.R;

public class Main extends AppCompatActivity {


    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
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
            "Image Gallery ",
            "Move Gallery",
            "Music Gallery",
            "Product",
            "Service",
            "Quote",
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
        mainRecyclerView.setLayoutManager(new GridLayoutManager(Main.this, 2));
        mainRecyclerView.setAdapter(new MainRecyclerViewAdapter(this, apiNames));
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
                            finish();
                            break;
                        case 1:
                            startActivity(new Intent(context, ActArticle.class));
                            finish();
                            break;
                        case 2:
                            startActivity(new Intent(context, ActNews.class));
                            finish();
                            break;
                        case 3:
                            startActivity(new Intent(context, ActPooling.class));
                            finish();
                            break;
                        case 4:
                            startActivity(new Intent(context, ActTicket.class));
                            finish();
                            break;
                        case 5:
                            startActivity(new Intent(context, ActBiography.class));
                            finish();
                            break;
                        case 6:
                            startActivity(new Intent(context, ActApplication.class));
                            finish();
                            break;
                        case 7:
                            startActivity(new Intent(context, ActEstate.class));
                            finish();
                            break;
                        case 8:
                            startActivity(new Intent(context, ActFile.class));
                            finish();
                            break;
                        case 9:
                            startActivity(new Intent(context, ActBlog.class));
                            finish();
                            break;
                        case 10:
                            startActivity(new Intent(context, ActImageGallery.class));
                            finish();
                            break;
                        case 11:
                            startActivity(new Intent(context, ActMovieGallery.class));
                            finish();
                            break;
                        case 12:
                            startActivity(new Intent(context, ActMusicGallery.class));
                            finish();
                            break;
                        case 13:
                            startActivity(new Intent(context, ActProduct.class));
                            finish();
                            break;
                        case 14:
                            startActivity(new Intent(context, ActService.class));
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

        class MainViewHolder extends RecyclerView.ViewHolder {
            private Button button;

            MainViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button_item);
            }
        }
    }
}
