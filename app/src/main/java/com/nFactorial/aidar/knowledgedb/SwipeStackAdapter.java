package com.nFactorial.aidar.knowledgedb;

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

public class SwipeStackAdapter extends BaseAdapter {

    private Context context;
    private ButtonSwipeOnClickHandler buttonSwipeOnClickHandler;
    private List<Question> data;
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
    public Question getItem(int position) {
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
        TextView subcatView = (TextView) convertView.findViewById(R.id.subcat);
        TextView textViewCard = (TextView) convertView.findViewById(R.id.solve_info_text);
        subcatView.setText(data.get(position).getSubcat());
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

    public void setData(List<Question> data) {
        this.data = data;
    }

    public void insertItemIntoStack(int position, Question item) {
        data.add(position, item);
    }

    public List<Question> getData() {
        return data;
    }
}
