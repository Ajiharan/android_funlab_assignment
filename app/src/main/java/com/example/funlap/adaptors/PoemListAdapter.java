package com.example.funlap.adaptors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.funlap.R;
import com.example.funlap.model.Poem;
import com.example.funlap.model.Story;

import java.util.ArrayList;
import java.util.List;

public class PoemListAdapter extends ArrayAdapter<Poem> {
    Activity context;
    List<Poem> items;

    public PoemListAdapter(@NonNull Activity context, ArrayList<Poem> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }
    private class ViewHolder {

        TextView title;
        TextView desc;
        TextView poem_detail;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoemListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.poem_list, parent, false);

            holder = new PoemListAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.poems_titles);
            holder.title.getPaint().setUnderlineText(true);
            holder.desc= (TextView) convertView.findViewById(R.id.poem_description);
            holder.poem_detail=(TextView)convertView.findViewById(R.id.poem_details);
            convertView.setTag(holder);

        } else {
            holder = (PoemListAdapter.ViewHolder) convertView.getTag();
        }

        Poem poem = items.get(position);

        holder.title.setText(poem.getTitle());
        holder.desc.setText("description :"+poem.getDesc());
        holder.poem_detail.setText(poem.getPoemText());
        return convertView;

    }
}
