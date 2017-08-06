package com.example.aidar.knowledgedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aidar on 8/3/17.
 */

public class SwipeStackAdapter<T> extends BaseAdapter {

    private Context context;
    private ButtonSwipeOnClickHandler buttonSwipeOnClickHandler;
    private List<T> data;
    private ImageButton yesButton;
    private ImageButton noButton;

    public SwipeStackAdapter(Context context, ButtonSwipeOnClickHandler buttonSwipeOnClickHandler) {
        this.context = context;
        this.buttonSwipeOnClickHandler = buttonSwipeOnClickHandler;
    }

    public interface ButtonSwipeOnClickHandler {
        void yesButtonSwiper();
        void noButtonSwiper();
    }

    @Override
    public int getCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.swipe_card, parent, false);
        TextView textViewCard = (TextView) convertView.findViewById(R.id.solve_info_text);
        textViewCard.setText(data.get(position).toString());
        yesButton = (ImageButton) convertView.findViewById(R.id.yes_question);
        noButton = (ImageButton) convertView.findViewById(R.id.no_question);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSwipeOnClickHandler.yesButtonSwiper();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSwipeOnClickHandler.noButtonSwiper();
            }
        });
        return convertView;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void insertItemIntoStack(int position, T item) {
        data.add(position, item);
    }

    public List<T> getData() {
        return data;
    }
}
