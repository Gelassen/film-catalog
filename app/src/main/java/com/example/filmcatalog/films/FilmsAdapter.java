package com.example.filmcatalog.films;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filmcatalog.Favorites;
import com.example.filmcatalog.R;
import com.example.filmcatalog.films.model.Result;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class FilmsAdapter extends Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;
    private static final int FOOTER = 1;

    private Context context;
    private Films.View view;
    private boolean isUpdateInProgress;
    private boolean isShowFooter;
    private List<Result> dataSource = new ArrayList<>();
    private Favorites favorites;

    public void update(List<Result> results, boolean showFooter) {
        isUpdateInProgress = false;
        isShowFooter = showFooter;
        dataSource.addAll(results);
        isShowFooter = dataSource.size() == 0 ? false : true;
        notifyDataSetChanged();
    }

    public void clear() {
        isUpdateInProgress = false;
        dataSource.clear();
        notifyDataSetChanged();
    }

    public FilmsAdapter(Context context, Films.View view) {
        this.context = context;
        this.view = view;
        isShowFooter = true;
        isUpdateInProgress = false;
        favorites = Favorites.restore(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_footer, parent, false);
                return new FooterViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_item, parent, false);
                return new FilmsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FilmsViewHolder) {
            final Result item = dataSource.get(position);
            bindFilmsViewHolder((FilmsViewHolder) holder, item);
        } else if (holder instanceof FooterViewHolder) {
            bindFooterViewHolder((FooterViewHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        isShowFooter = isShowFooter && !(dataSource.size() == 0);
        final int count = isShowFooter ? dataSource.size() + FOOTER : dataSource.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (isShowFooter && position == getFooterPosition()) ? VIEW_TYPE_FOOTER : VIEW_TYPE_ITEM;
    }

    public void onDestroy() {
        favorites.save(context);
    }

    private void bindFooterViewHolder(final FooterViewHolder holder) {
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilmsAdapter.this.view.onNextPage();
                holder.next.setVisibility(View.GONE);
                holder.progress.setVisibility(View.VISIBLE);
            }
        });
        holder.next.setVisibility(isUpdateInProgress ? View.GONE : View.VISIBLE);
        holder.progress.setVisibility(isUpdateInProgress ? View.VISIBLE : View.GONE);

        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        params.setFullSpan(true);
        holder.itemView.setLayoutParams(params);
    }

    private void bindFilmsViewHolder(final FilmsViewHolder holder, final Result item) {
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getOverview());
        Glide.with(context)
                .load(context.getString(R.string.base_url) + item.getPosterPath())
                .into(holder.avatar);
        holder.date.setText(getFormattedData(item.getReleaseDate()));
        holder.fav.getBackground().setLevel(favorites.isFav(item.getId()) ? 1 : 0);
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFav = favorites.isFav(item.getId());
                favorites.setFavorities(item.getId(), !isFav);
                holder.fav.getBackground().setLevel(favorites.isFav(item.getId()) ? 1 : 0);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view == null) return;
                view.onFilmsItemClick(item.getTitle());
            }
        });
    }

    private int getFooterPosition() {
        return getItemCount() - 1;
    }

    private String getFormattedData(String date) {
        if (TextUtils.isEmpty(date)) return "";

        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = dateTimeFormat.parseDateTime(date);

        DateTimeFormatter dateTimeFormatSecond = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dateTimeFormatSecond.print(dateTime);
    }

    public void setProgressState(boolean turnOff) {
        isUpdateInProgress = turnOff;
        notifyItemChanged(getFooterPosition());
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatar;
        private final TextView title;
        private final TextView desc;
        private final TextView date;
        private final ImageView fav;

        public FilmsViewHolder(View view) {
            super(view);

            avatar = view.findViewById(R.id.icon);
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            date = view.findViewById(R.id.date);
            fav = view.findViewById(R.id.fav);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private final ImageView next;
        private final ProgressBar progress;

        public FooterViewHolder(View itemView) {
            super(itemView);
            next = itemView.findViewById(R.id.next);
            progress = itemView.findViewById(R.id.progress);
        }
    }
}
