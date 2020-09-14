package com.example.tutorial_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RadioGroup radiogroup;
    RadioButton radioButton;
    EditText finame,laname,remail,rpassword;
    Switch switch1;
    Spinner spinner1;
    CheckBox checkBox;

    String gender;
    String Fname;
    String Lname;
    String REmail;
    String RPassword;
    String Branch;
    String Status;
    String City;

    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       finame=findViewById(R.id.fname);
       laname=findViewById(R.id.lname);
       remail=findViewById(R.id.remail);
       rpassword=findViewById(R.id.rpassword);
       switch1=findViewById(R.id.switch1);
       radiogroup=findViewById(R.id.radiogroup);
       spinner1=findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);


        helper = new DatabaseHelper(this);
    }

    public void Register(View view) {
       int radioId = radiogroup.getCheckedRadioButtonId();
       Fname= finame.getText().toString().trim();
       Lname = laname.getText().toString().trim();
       REmail= remail.getText().toString().trim();
       RPassword = rpassword.getText().toString().trim();

       if (!Validation(Fname,Lname,REmail,RPassword,spinner1))
           return;

       checkBox=findViewById(R.id.checkBox);
       if (checkBox.isChecked())
           Status="Ative";
       else
           Status="Inactive";

       gender=radioButton.getText().toString();
       if (switch1.isChecked())
           Branch="CE";
       else
           Branch="IT";

       if (helper.insertData(Fname,Lname,REmail,RPassword,gender,Branch,spinner1,Status)){
           Toast.makeText(this, "Record Added", Toast.LENGTH_SHORT).show();
           clearFields();
       }
       else
           Toast.makeText(this, "Record is not added", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }



    private boolean Validation(String fname, String lname, String rEmail, String rPassword, Spinner spinner) {
        boolean re=true;

        if (spinner.equals("Select City")){
            ((TextView)spinner.getSelectedView()).setError("Please Select City");
            re=false;
        }

        if (RPassword.isEmpty()){
            rpassword.requestFocus();
            rpassword.setError("Please enter password");
            re=false;
        }
        else if (RPassword.length()<6){
            rpassword.requestFocus();
            rpassword.setError("password must be 6 digits");
            re=false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(REmail).matches()){
            remail.requestFocus();
            remail.setError("Please enter valid email");
            re=false;
        }
        if (REmail.isEmpty()){
            remail.requestFocus();
            remail.setError("Please enter email address");
            re=false;
        }
        if (Lname.isEmpty()){
            laname.requestFocus();
            laname.setError("Please enter lastname");
            re=false;
        }
        if (Fname.isEmpty()){
            finame.requestFocus();
            finame.setError("Please enter firstname");
            re=false;
        }

        return re;
    }

    private void clearFields() {
        finame.setText("");
        laname.setText("");
        remail.setText("");
        rpassword.setText("");

        if (checkBox.isChecked())
            checkBox.toggle();

        if (switch1.isChecked())
            switch1.toggle();

        spinner1.setSelection(0);

        radioButton=findViewById(R.id.rbtnmale);
        radioButton.setChecked(true);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        City=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}