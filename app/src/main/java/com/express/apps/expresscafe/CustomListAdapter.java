package com.express.apps.expresscafe;

import android.app.Activity;
import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
//    private final Integer[] imgid;

    public CustomListAdapter(Activity context, String[] itemname) {

        super(context, R.layout.categories_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
//        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.categories_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(R.drawable.common_full_open_on_phone);

//        imageView.setImageResource(imgid[position]);
//        extratxt.setText("Description "+itemname[position]);
        return rowView;

    };
}