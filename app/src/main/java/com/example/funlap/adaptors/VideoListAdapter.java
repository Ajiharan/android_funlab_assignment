package com.example.funlap.adaptors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.example.funlap.R;
import com.example.funlap.model.Story;
import com.example.funlap.model.VideoFile;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter  extends ArrayAdapter<VideoFile> {
    Activity context;
    List<VideoFile> items;

    public VideoListAdapter(@NonNull Activity context, ArrayList<VideoFile> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }
    private class ViewHolder {

        TextView title;
        TextView video_view;
        TextView desc;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.video_list, parent, false);

            holder = new VideoListAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.video_titles);
            holder.title.getPaint().setUnderlineText(true);
            holder.video_view= (TextView) convertView.findViewById(R.id.video_view);
            holder.desc=(TextView)convertView.findViewById(R.id.video_descr);
            convertView.setTag(holder);

        } else {
            holder = (VideoListAdapter.ViewHolder) convertView.getTag();
        }

        VideoFile video = items.get(position);

        holder.title.setText(video.getTitle());
        holder.video_view.setText(video.getUri());
        holder.desc.setText("description : "+video.getDesc());
        return convertView;

    }
}
