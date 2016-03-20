package ramage.istic.fr.firstapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListClientActivity extends Activity {

    private SimpleAdapter simpleAdapter;

    private ListView maListViewPerso;
    private List<Map<String, String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        maListViewPerso = (ListView) findViewById(R.id.listView);
        listItem = new ArrayList<>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("name","Romain");
        map.put("lastname","Ramage");
        map.put("city", "Rennes");
        map.put("date", "29/07/1993");
        listItem.add(map);
        map = new HashMap<String,String>();
        map.put("name","Francois");
        map.put("lastname","Sacla");
        map.put("city", "dazdzaaz");
        map.put("date","29/sazazdza/1993");
        listItem.add(map);
        simpleAdapter=new SimpleAdapter (this.getBaseContext(), listItem, R.layout.client_item,
                new String[] {"name", "lastname", "city","date"}, new int[] {R.id.firstname, R.id.lastname, R.id.city,R.id.date});
        maListViewPerso.setAdapter(simpleAdapter);
    }

    public void addClient(View view){

    }

}
