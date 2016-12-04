package pt.utl.ist.sirs.t05.sirsapp.Activities;

import pt.utl.ist.sirs.t05.sirsapp.Activities.Settings.SettingsActivity;
import pt.utl.ist.sirs.t05.sirsapp.Constants.Constant;
import pt.utl.ist.sirs.t05.sirsapp.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity  {
    private String keyString;
    private String errorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent pairIntent = getIntent();
        this.keyString = pairIntent.getStringExtra("SessionKey");
        this.errorString = pairIntent.getStringExtra("Error");

        if(errorString != null && errorString.equals("Error")){
            Toast.makeText(HomeActivity.this, "Token appears to be incorrect!", Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        Button pair_button = (Button) findViewById(R.id.pair_button);
        Button unlock_button = (Button) findViewById(R.id.unlock_button);
        Button lock_button = (Button) findViewById(R.id.lock_button);

        pair_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeActivity = new Intent(HomeActivity.this, PairingActivity.class);
                HomeActivity.this.startActivity(changeActivity);
            }
        });

        unlock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyString != null && Constant.unlockSocketOpen == false) {
                    Intent changeActivity = new Intent(HomeActivity.this, UnlockActivity.class);
                    changeActivity.putExtra("SessionKey", keyString);
                    HomeActivity.this.startActivity(changeActivity);
                }else if(Constant.unlockSocketOpen == true){
                    Toast.makeText(HomeActivity.this, "There is an ongoing connection !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(HomeActivity.this, "Phone has to be paired !", Toast.LENGTH_LONG).show();
                }
            }
        });

        lock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyString != null && Constant.unlockSocketOpen == true) {
                    Constant.lockNonce = "77999";
                    Toast.makeText(HomeActivity.this, "Files locked in 5 seconds(max) !", Toast.LENGTH_LONG).show();
                } else if(Constant.unlockSocketOpen == false){
                    Toast.makeText(HomeActivity.this, "There has to be an ongoing connection !", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(HomeActivity.this, "Phone has to be paired !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent aboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
                HomeActivity.this.startActivity(aboutIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
                HomeActivity.this.startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

