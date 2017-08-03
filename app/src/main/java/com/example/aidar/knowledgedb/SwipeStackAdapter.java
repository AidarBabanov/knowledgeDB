package com.example.aidar.knowledgedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import link.fls.swipestack.SwipeStack;

/**
 * Created by aidar on 8/3/17.
 */

public class SwipeStackAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public SwipeStackAdapter(List<String> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.swipe_card, parent, false);
        TextView textViewCard = (TextView) convertView.findViewById(R.id.solve_info_text);
        textViewCard.setText(data.get(position));

        return convertView;
    }
}
