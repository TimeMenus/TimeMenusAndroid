package com.express.apps.expresscafe;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.express.apps.expresscafe.models.Category;
import com.express.apps.expresscafe.models.Menu;
import com.express.apps.expresscafe.services.DataService;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends ListActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        List<Category> categoryList=DataService.getCategories("");

        List<String> list=new ArrayList<>();

        for(Category c: categoryList){
            list.add(c.getName());
        }

        Menu todayMenu=DataService.getTodayMenu();

        DataService.setItemsListener();

//        System.out.print("Item: "+todayMenu.getKey()+" "+todayMenu.getDate());




        setListAdapter(new ArrayAdapter<String>(this, R.layout.categories_list,list));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
