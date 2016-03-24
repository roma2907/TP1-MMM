package ramage.istic.fr.firstapp;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ramage.istic.fr.firstapp.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.raz){
            remiseAzero();
            return true;
        }else if(id == R.id.addtel){
            addTell();
            return true;
        }else if(id == R.id.browser){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://fr.wikipedia.org"));
            startActivity(intent);
            return true;
        }
        return false;
    }


    public void addTell(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.tel_content);
        EditText tellNumber = new EditText(this);
        tellNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(tellNumber);
    }

    public void displayData(View view) {
        String name = ((EditText)findViewById(R.id.name)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastName)).getText().toString();
        String city = ((EditText)findViewById(R.id.city)).getText().toString();
        String date = ((EditText)findViewById(R.id.dateBirthday)).getText().toString();
        String department = ((Spinner)findViewById(R.id.departement)).getSelectedItem().toString();
        Toast.makeText(getApplicationContext(),
                name + " " + lastName + " " + date + " " + city +" "+department, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        // ajout de données supplémentaires dans l'intent
        User user = new User(name,lastName,city,date,department);
        ContentValues values = new ContentValues();

        values.put(LibraryContentProvider.USER_NAME,name);

        values.put(LibraryContentProvider.USER_LASTNAME,lastName);

        values.put(LibraryContentProvider.USER_CITY,city);

        values.put(LibraryContentProvider.USER_DATE,date);

        values.put(LibraryContentProvider.USER_DEPARTEMENT,department);

        Uri uri = getContentResolver().insert(
                LibraryContentProvider.CONTENT_URI, values);

        intent.putExtra("user",user);
        setResult(RESULT_OK,intent);

        finish();
    }

    public void remiseAzero() {
        ((EditText)findViewById(R.id.name)).setText("");
        ((EditText)findViewById(R.id.lastName)).setText("");
        ((EditText)findViewById(R.id.city)).setText("");
        ((EditText)findViewById(R.id.dateBirthday)).setText("");
    }
}
