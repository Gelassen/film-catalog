package com.home.snappii.view;


import android.media.Image;
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

import com.bumptech.glide.Glide;
import com.home.snappii.AppApplication;
import com.home.snappii.R;
import com.home.snappii.model.Result;
import com.home.snappii.utils.ItemDecoration;

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
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);
        list.addItemDecoration(new ItemDecoration(4));

        presenter.requestData();
    }

    @Override
    public void show(List<Result> users) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.updateData(users);
    }

    @Override
    public void onError() {
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
        public void onBindViewHolder(ViewHolder holder, int position) {
            Result result = data.get(position);
            holder.name.setText(result.getName().getFirst());
            holder.surname.setText(result.getName().getLast());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO complete me
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
            private final TextView surname;

            public ViewHolder(View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.avatar);
                name = itemView.findViewById(R.id.name);
                surname = itemView.findViewById(R.id.surname);
            }
        }
    }
}
