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
import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.models.Menu;
import com.express.apps.expresscafe.services.DataService;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends ListActivity{

    List<String> categories=new ArrayList<>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        List<Item> items=DataService.getItems();

        for(Item i: items){
            categories.add(i.getName());
        }



        CustomListAdapter adapter=new CustomListAdapter(this, categories,items);
        listView=(ListView)findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= categories.get(position);
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }


}
