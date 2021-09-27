package com.example.inclass05grp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppListAdapter extends ArrayAdapter<DataServices.App> {
    public AppListAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_description, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.appName = convertView.findViewById(R.id.appName);
            viewHolder.artistName = convertView.findViewById(R.id.artistName);
            viewHolder.releaseDate = convertView.findViewById(R.id.releaseDate);
            convertView.setTag(viewHolder);
        }
        DataServices.App app = getItem(position);

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.appName.setText(app.name);
        viewHolder.artistName.setText(app.artistName);
        viewHolder.releaseDate.setText(app.releaseDate);
        return convertView;
    }

    class ViewHolder {
        TextView appName, artistName, releaseDate;
    }
}
