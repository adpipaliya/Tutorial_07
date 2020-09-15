package com.example.tutorial_07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    TextView txtWelcome;
    DatabaseHelper helper;
    String UserEmail;
    TextView tfname;
    TextView tlname;
    TextView temail;
    TextView tpassword;
    TextView tgender;
    TextView tbranch;
    TextView tcity;
    TextView tstatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

       tfname = findViewById(R.id.fname);
       tlname = findViewById(R.id.lname);
       temail = findViewById(R.id.email);
       tpassword = findViewById(R.id.password);
       tgender = findViewById(R.id.gender);
       tbranch = findViewById(R.id.switch1);
       tcity = findViewById(R.id.city);
       tstatus = findViewById(R.id.status);

       SharedPreferences preferences = getSharedPreferences("college",MODE_PRIVATE);
       UserEmail = preferences.getString("Username"," ");

       txtWelcome = findViewById(R.id.txtwelcome);
       txtWelcome.setText(UserEmail);

       SetData();

    }

    private void SetData() {
        DatabaseHelper helper = new DatabaseHelper(this);

        Cursor res = helper.getData(UserEmail);
        res.moveToNext();

        tfname.setText("FirstName : "+res.getString(1));
        tlname.setText("LastNAme: "+res.getString(2));
        temail.setText("Email: "+res.getString(3));
        tpassword.setText("Password: "+res.getString(4));
        tgender.setText("Gender: "+res.getString(5));
        tbranch.setText("Branch: "+res.getString(6));
        tcity.setText("City: "+res.getString(7));
        tstatus.setText("Status: "+res.getString(8));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuLogout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("college",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("Username");
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}