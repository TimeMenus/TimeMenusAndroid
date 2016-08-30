package com.express.apps.expresscafe;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.services.DataService;
import com.squareup.picasso.Picasso;
import java.net.URL;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;
    private final List<Item> items;
//    private final List<Item> items;
//    private final Integer[] imgid;

    public CustomListAdapter(Activity context, List<String> itemname,List<Item> items) {

        super(context, R.layout.categories_list, itemname);
        this.items=items;
        this.context=context;
        this.itemname=itemname;
//        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.categories_list, null,true);

        //


        TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView txtDescription = (TextView) rowView.findViewById(R.id.description);
        TextView txtCategory = (TextView) rowView.findViewById(R.id.category);

//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);


        Item i=items.get(position);

        txtTitle.setText(i.getName());
        txtCategory.setText(DataService.getCategoryById(i.getCategoryId()).getName());
        txtDescription.setText(i.getDescription());

        Picasso.with(context).load(i.getPicture().getUrl()).into(imageView);


        return rowView;

    };

    private class DownloadPicture extends AsyncTask<URL, Integer, Long> {
        @Override
        protected Long doInBackground(URL... urls) {
            return null;
        }

//        protected Long doInBackground(URL url) {
////            int count = urls.length;
//            long totalSize = 0;
////            for (int i = 0; i < count; i++) {
////                totalSize += Downloader.downloadFile(urls[i]);
////                publishProgress((int) ((i / (float) count) * 100));
//                // Escape early if cancel() is called
////                if (isCancelled()) break;
////            }
//            return totalSize;
//        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPe?rcent(progress[0]);
        }

        protected void onPostExecute(Long result) {
//            showDialog("Downloaded " + result + " bytes");
        }



    }
}