package ramage.istic.fr.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ramage.istic.fr.firstapp.model.User;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //on récupère les arguments de l'utilisateur
        User user = getIntent().getExtras().getParcelable("user");

        //on set chaque attribut de l'utilisateur au bon endroit
        ((TextView) findViewById(R.id.city_display)).setText(user.getCity());
        ((TextView) findViewById(R.id.date_display)).setText(user.getDate());
        ((TextView) findViewById(R.id.departement_display)).setText(user.getDepartment());
        ((TextView) findViewById(R.id.lastname_display)).setText(user.getLastname());
        ((TextView) findViewById(R.id.firstname_display)).setText(user.getName());
    }
}
