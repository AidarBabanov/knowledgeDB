package com.example.aidar.knowledgedb;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidar.knowledgedb.Activities.CompanyActivity;

import java.util.List;

/**
 * Created by aidar on 7/18/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> listItems;
    private RecyclerViewAdapterOnClickHandler recyclerViewAdapterOnClickHandler;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(String topic);
    }

    public RecyclerViewAdapter(List<String> listItems, RecyclerViewAdapterOnClickHandler recyclerViewAdapterOnClickHandler) {
        this.listItems = listItems;
        this.recyclerViewAdapterOnClickHandler = recyclerViewAdapterOnClickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        if(listItems==null)return 0;
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_item);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String topic = listItems.get(adapterPosition);
            Log log = null;
            log.i("Check", topic);
            recyclerViewAdapterOnClickHandler.onClick(topic);
        }
    }
}
