package ramage.istic.fr.firstapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ramage.istic.fr.firstapp.model.User;

public class ListClientActivity extends Activity {

    private SimpleAdapter simpleAdapter;

    private ListView maListViewPerso;
    private List<Map<String, String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        maListViewPerso = (ListView) findViewById(R.id.listView);

        Uri users = LibraryContentProvider.CONTENT_URI;
        Cursor c = managedQuery(users, null, null, null, "name");
        listItem = new ArrayList<>();
        if (c.moveToFirst()) {
            do{
                Map<String,String> map = new HashMap<String,String>();
                map.put("firstname",c.getString(c.getColumnIndex(LibraryContentProvider.USER_NAME)));
                map.put("lastname",LibraryContentProvider.USER_LASTNAME);
                map.put("city", LibraryContentProvider.USER_CITY);
                map.put("date", LibraryContentProvider.USER_DATE);
                listItem.add(map);
            } while (c.moveToNext());
        }

        simpleAdapter=new SimpleAdapter (this.getBaseContext(), listItem, R.layout.client_item,
                new String[] {"name", "lastname", "city","date"}, new int[] {R.id.firstname_item, R.id.lastname_item, R.id.city_item,R.id.date_item});
        maListViewPerso.setAdapter(simpleAdapter);
    }

    public void addClient(View view){
        // creation d'un intent pour appeler une autre activité
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        User user = extras.getParcelable("user");
        Map<String,String> map = new HashMap<String,String>();
        map.put("name",user.getName());
        map.put("lastname",user.getLastname());
        map.put("city", user.getCity());
        map.put("date",user.getDate());
        listItem.add(map);
        simpleAdapter=new SimpleAdapter (this.getBaseContext(), listItem, R.layout.client_item,
                new String[] {"name", "lastname", "city","date"}, new int[] {R.id.firstname_item, R.id.lastname_item, R.id.city_item,R.id.date_item});
        maListViewPerso.setAdapter(simpleAdapter);
    }

}
