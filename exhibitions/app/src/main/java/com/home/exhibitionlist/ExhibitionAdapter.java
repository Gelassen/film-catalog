package com.home.exhibitionlist;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.model.Exhibit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.ExhibitionViewHolder> {

    private List<Exhibit> model = new ArrayList<>();

    public void updateModel(List<Exhibit> model) {
        this.model.clear();
        this.model.addAll(model);
        notifyDataSetChanged();
    }

    @Override
    public ExhibitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exhibition, parent, false);
        return new ExhibitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExhibitionViewHolder holder, int position) {
        holder.reset();
        Exhibit exhibit = model.get(position);
        List<String> data = exhibit.getImages();
        for (String item : data) {
            holder.addView(exhibit.getTitle(), item);
        }
    }

    @Override
    public int getItemCount() {
        return model.size();
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
            View view = LayoutInflater.from(itemView.getContext())
                    .inflate(R.layout.item_exhibition_preview, container, false);
            container.addView(view, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView textView = view.findViewById(R.id.preview_title);
            textView.setText(title);

            final ImageView imageView = view.findViewById(R.id.preview);
            bindImage(url, imageView);
        }

        private void bindImage(final String url, final ImageView view) {
            Picasso.with(itemView.getContext())
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(view);
        }
    }
}
