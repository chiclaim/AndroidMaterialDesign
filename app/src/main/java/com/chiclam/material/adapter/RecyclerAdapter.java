package com.chiclam.material.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chiclam.material.R;

import java.util.List;

/**
 * Created by chiclaim on 2016/07/12
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<String> list;

    public RecyclerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvTitle.setText(list.get(position));
        itemViewHolder.tvDesc.setText(list.get(position) + " this is description");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_vibrant);
            tvDesc = (TextView) itemView.findViewById(R.id.title_desc);
        }
    }

}
