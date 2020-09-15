package com.example.tutorial_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button btnlogin;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        SharedPreferences preferences = getSharedPreferences("college",MODE_PRIVATE);
        String Email = preferences.getString("Email","");

        if (!Email.equals("")){
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void LoginHere(View view) {
        if (!LoginValidation())
            return;

        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        helper = new DatabaseHelper(this);

        Cursor res = helper.validateUser(Email, Password);
        if (res.getCount() == 1) {
            SharedPreferences preferences = getSharedPreferences("college", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Email", Email);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean LoginValidation() {
        boolean result = true;

        if (password.getText().toString().isEmpty()){
            result = false;
            password.requestFocus();
            password.setError("Please enter password");
        }
        if (email.getText().toString().isEmpty()){
            result = false;
            email.requestFocus();
            email.setError("Please enter username");
        }

        return result;
    }

    public void Register(View view) {
        Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

}