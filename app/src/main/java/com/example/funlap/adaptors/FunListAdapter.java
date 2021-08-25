package com.example.funlap.adaptors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.funlap.R;
import com.example.funlap.model.Fun;


import java.util.ArrayList;
import java.util.List;

public class FunListAdapter extends ArrayAdapter<Fun> {
    Activity context;
    List<Fun> items;

    public FunListAdapter(@NonNull Activity context, ArrayList<Fun> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }
    private class ViewHolder {

        TextView title;
        TextView cat;
        TextView fun_desc;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FunListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.fun_list, parent, false);

            holder = new FunListAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.fun_titles);
            holder.title.getPaint().setUnderlineText(true);
            holder.cat= (TextView) convertView.findViewById(R.id.fun_cat);
            holder.fun_desc=(TextView)convertView.findViewById(R.id.fun_desc);
            convertView.setTag(holder);

        } else {
            holder = (FunListAdapter.ViewHolder) convertView.getTag();
        }

        Fun fun = items.get(position);

        holder.title.setText(fun.getTitle());
        holder.cat.setText("category :"+fun.getCategory());
        holder.fun_desc.setText(fun.getDesc());
        return convertView;

    }
}
