package com.home.snappii.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.home.snappii.AppApplication;
import com.home.snappii.R;
import com.home.snappii.model.Result;
import com.home.snappii.utils.ItemDecoration;
import com.home.snappii.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewContract.IView, SwipeRefreshLayout.OnRefreshListener {

    private Presenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private DataAdapter adapter = new DataAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter((AppApplication) getApplication());
        presenter.onAttachView(this);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView list = findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        list.addItemDecoration(new ItemDecoration(4));
        list.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.requestNextPage();
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return swipeRefreshLayout.isRefreshing();
            }
        });
        presenter.requestData();
    }

    @Override
    public void show(List<Result> users) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.updateData(users);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Something goes wrong", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.onPullToRefresh();
        swipeRefreshLayout.setRefreshing(true);
    }

    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

        private List<Result> data = new ArrayList<>();

        public void updateData(List<Result> data) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Result result = data.get(position);
            holder.name.setText(result.getName().getFirst() + " " + result.getName().getLast());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailedActivity.start(holder.itemView.getContext(), result);
                }
            });

            Glide.with(holder.itemView.getContext())
                    .load(result.getPicture().getMedium())
                    .into(holder.avatar);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView avatar;
            private final TextView name;

            public ViewHolder(View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.avatar);
                name = itemView.findViewById(R.id.title);
            }
        }
    }
}
