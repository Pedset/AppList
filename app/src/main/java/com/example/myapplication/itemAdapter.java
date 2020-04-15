package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class itemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] countries;
    String[] capitals;
    String[] languages;

    public itemAdapter (Context c, String[] con, String[] lang, String[] cap){
        countries = con;
        capitals = cap;
        languages = lang;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countries.length;
    }

    @Override
    public Object getItem(int position) {
        return countries[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.my_listview_detail, null);

        TextView nameTextView = v.findViewById(R.id.nameTextView);
        TextView langTextView = v.findViewById(R.id.langTextView);
        TextView capTextView = v.findViewById(R.id.capTextView);

        nameTextView.setText(countries[position]);
        langTextView.setText("Language: " + languages[position]);
        capTextView.setText("Capital City: " + capitals[position]);

        return v;
    }
}
