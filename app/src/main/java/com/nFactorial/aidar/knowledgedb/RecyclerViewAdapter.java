package com.nFactorial.aidar.knowledgedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aidar on 7/18/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private RecyclerViewAdapterOnClickHandler recyclerViewAdapterOnClickHandler;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(int position);
    }

    public void setData(List<ListItem> listItems){
        this.listItems = listItems;
    }

    public RecyclerViewAdapter(List<ListItem> listItems, RecyclerViewAdapterOnClickHandler recyclerViewAdapterOnClickHandler) {
        this.listItems = listItems;
        this.recyclerViewAdapterOnClickHandler = recyclerViewAdapterOnClickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listItems.get(position).toString());
    }

    @Override
    public int getItemCount() {
        if(listItems==null)return 0;
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_item);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            recyclerViewAdapterOnClickHandler.onClick(adapterPosition);
        }

    }
}
