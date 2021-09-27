package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_card, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.userName = convertView.findViewById(R.id.userName);
            viewHolder.userAge = convertView.findViewById(R.id.userAge);
            convertView.setTag(viewHolder);
        }
        User user = getItem(position);

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.userName.setText(user.getName());
        viewHolder.userAge.setText(user.getAge() + " years old");
        return convertView;
    }

    class ViewHolder {
        TextView userName, userAge;
    }
}
