package com.home.exhibitionlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.home.fileexhibitsloader.FileExhibitsLoader;
import com.home.model.Exhibit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private Presenter presenter;
    private ExhibitionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ExhibitionAdapter();

        RecyclerView view = findViewById(R.id.list);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);


        presenter = new Presenter(new FileExhibitsLoader(this), this);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.onStart();
    }

    @Override
    public void onModel(List<Exhibit> data) {
        adapter.updateModel(data);
    }

    private class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.TestViewHolder> {

        private List<Exhibit> model = new ArrayList<>();

        public void updateModel(List<Exhibit> model) {
            this.model.clear();
            this.model.addAll(model);
            notifyDataSetChanged();
        }

        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_exhibition_preview, parent, false);
            return new TestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {
//            holder.reset();
            Exhibit exhibit = model.get(position);
            List<String> data = exhibit.getImages();
            for (String item : data) {
//                holder.addView(exhibit.getTitle(), item);
            }
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public class TestViewHolder extends RecyclerView.ViewHolder {

            private final ImageView view;

            public TestViewHolder(View itemView) {
                super(itemView);
                view = findViewById(R.id.preview);
            }

            public void setData(String url) {
//                Picasso picasso = Picasso.with(MainActivity.this);
//                picasso.setLoggingEnabled(true);
//                picasso.load(url)
//                        .fit()
//                        .centerCrop()
//                        .error(R.drawable.ic_launcher_background)
//                        .into(view);

                Glide
                        .with(MainActivity.this)
                        .load("https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png")
                        .into(view);
            }
        }

        public class ExhibitionViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout container;

            public ExhibitionViewHolder(View itemView) {
                super(itemView);
                container = itemView.findViewById(R.id.container);
            }

            public void reset() {
                container.removeAllViews();
            }

            public void addView(String title, String url) {
                final View view = LayoutInflater.from(itemView.getContext())
                        .inflate(R.layout.item_exhibition_preview, container, false);
                ((TextView) view.findViewById(R.id.preview_title)).setText(title);
                bindImage(url, (ImageView) view.findViewById(R.id.preview));
                container.addView(view);
            }

            private void bindImage(final String url, final ImageView view) {
                Picasso.with(MainActivity.this)
                        .load(url)
                        .fit()
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background)
                        .into(view);
            }
        }
    }
}


