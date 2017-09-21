package dk.hrup.sikkerthjem;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Settings extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((EditText)findViewById(R.id.phone)).setText(SikkertHjem.getPhone());
        ((EditText)findViewById(R.id.password)).setText(SikkertHjem.getPassword());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            SikkertHjem.setPhone(((EditText)findViewById(R.id.phone)).getText().toString());
            SikkertHjem.setPassword(((EditText)findViewById(R.id.password)).getText().toString());
            onBackPressed();
        }
    }
}
