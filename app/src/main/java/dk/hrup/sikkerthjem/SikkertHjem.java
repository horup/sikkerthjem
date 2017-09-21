package dk.hrup.sikkerthjem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SikkertHjem extends ActionBarActivity implements View.OnClickListener, View.OnLongClickListener {
    private static SharedPreferences preferences;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        smsManager = SmsManager.getDefault();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sikkert_hjem);
        preferences = getPreferences(0);

        ((Button)findViewById(R.id.lock)).setOnLongClickListener(this);
        ((Button)findViewById(R.id.lock)).setLongClickable(true);
        ((Button)findViewById(R.id.unlock)).setOnLongClickListener(this);
        ((Button)findViewById(R.id.unlock)).setLongClickable(true);
        ((Button)findViewById(R.id.delsikring)).setOnLongClickListener(this);
        ((Button)findViewById(R.id.delsikring)).setLongClickable(true);

        setDefaults();
    }

    private void setDefaults()
    {
        setDefaultConfiguration(findViewById(R.id.lock), "[password]1");
        setDefaultConfiguration(findViewById(R.id.unlock), "[password]0");
        setDefaultConfiguration(findViewById(R.id.delsikring), "[password]2");
    }

    private void setDefaultConfiguration(View v, String value)
    {
        String conf = getConfiguration(v);
        if (conf.isEmpty())
            setConfiguration(v, value);
    }

    public static String getPhone()
    {
        return preferences.getString("phone", "");
    }

    public static void setPhone(String phone)
    {
        preferences.edit().putString("phone", phone).apply();
    }

    public static String getPassword()
    {
        return preferences.getString("password", "");
    }

    public static void setPassword(String password)
    {
        preferences.edit().putString("password", password).apply();
    }

    public static String getConfiguration(View button)
    {
        return preferences.getString("" + button.getId(), "");
    }

    public static void setConfiguration(View button, String value)
    {
        preferences.edit().putString("" + button.getId(), value).apply();
    }

    private String getSMS(String configuration)
    {
        return configuration.replace("[password]", getPassword());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String phone = getPhone();
        String password = getPassword();
        if (phone != "" && password != "") {

            if (v.getId() == R.id.lock) {
                String sms = getSMS(getConfiguration(v));
                smsManager.sendTextMessage(phone, null, sms, null, null);
                Toast toast = Toast.makeText(getApplicationContext(), "Alarm Fuldsikring sendt til " + phone, Toast.LENGTH_LONG);
                toast.show();
            } else if (v.getId() == R.id.delsikring) {
                String sms = getSMS(getConfiguration(v));
                smsManager.sendTextMessage(phone, null, sms, null, null);
                Toast toast = Toast.makeText(getApplicationContext(), "Alarm Delsikring sendt til " + phone, Toast.LENGTH_LONG);
                toast.show();
            }
            else if (v.getId() == R.id.unlock) {
                String sms = getSMS(getConfiguration(v));
                smsManager.sendTextMessage(phone, null, sms, null, null);
                Toast toast = Toast.makeText(getApplicationContext(), "Alarm Frakobling sendt til " + phone, Toast.LENGTH_LONG);
                toast.show();
            }

        }

        if (v.getId() == R.id.settings) {
           Intent i = new Intent(this, Settings.class);
           startActivity(i);
        }
    }

    private void configureButton(final Button b)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this).setTitle("Konfigurere " + b.getText());
        final EditText input = new EditText(this);
        input.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        input.setText(getConfiguration(b));
        alert.setView(input);

        alert.setPositiveButton("Gem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Editable value = input.getText();
                setConfiguration(b, value.toString());
                setDefaults();
            }
        });

        alert.setNegativeButton("Annulere", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.lock) {
            this.configureButton((Button)v);
        } else if (v.getId() == R.id.delsikring) {
            this.configureButton((Button)v);
        }
        else if (v.getId() == R.id.unlock) {
            this.configureButton((Button)v);
        }

        return true;
    }
}
