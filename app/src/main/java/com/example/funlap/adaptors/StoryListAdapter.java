package com.example.funlap.adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.funlap.R;
import com.example.funlap.model.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryListAdapter extends ArrayAdapter<Story> {

    Activity context;
    List<Story> items;

    public StoryListAdapter(@NonNull Activity context, ArrayList<Story> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }
    private class ViewHolder {

        TextView title;
        TextView author;
        TextView story_des;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoryListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.story_list, parent, false);

            holder = new StoryListAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.story_titles);
            holder.title.getPaint().setUnderlineText(true);
            holder.author= (TextView) convertView.findViewById(R.id.story_author);
            holder.story_des=(TextView)convertView.findViewById(R.id.story_desc);
            convertView.setTag(holder);

        } else {
            holder = (StoryListAdapter.ViewHolder) convertView.getTag();
        }

        Story story = items.get(position);

        holder.title.setText(story.getTitle());
        holder.story_des.setText(story.getDesc());
        holder.author.setText("Author : "+story.getAuthor());
        return convertView;

    }

}
