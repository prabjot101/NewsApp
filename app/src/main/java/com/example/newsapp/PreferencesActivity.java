package com.example.newsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    CheckBox chkGeneral,chkBusiness,chkEntertainment,chkHealth,chkScience,chkSports,chkTechnology;
    Button btnPrefSave;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        chkGeneral = findViewById(R.id.chkGeneral);
        chkBusiness = findViewById(R.id.chkBusiness);
        chkEntertainment = findViewById(R.id.chkEntertainment);
        chkHealth = findViewById(R.id.chkHealth);
        chkScience = findViewById(R.id.chkScience);
        chkSports = findViewById(R.id.chkSports);
        chkTechnology = findViewById(R.id.chkTechnology);

        btnPrefSave = findViewById(R.id.btnPrefSave);

        DatabaseHandler db = new DatabaseHandler(this);

        sharedPreferences = getSharedPreferences("MyUser", MODE_PRIVATE);

        String[] result =db.findProduct(sharedPreferences.getString("user",""));


//        SharedPreferences sh = getSharedPreferences("MyArticlePrefs", MODE_PRIVATE);

        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show

        chkGeneral.setChecked(Boolean.parseBoolean(result[1]));
        chkBusiness.setChecked(Boolean.parseBoolean(result[2]));
        chkEntertainment.setChecked(Boolean.parseBoolean(result[3]));
        chkHealth.setChecked(Boolean.parseBoolean(result[4]));
        chkScience.setChecked(Boolean.parseBoolean(result[5]));
        chkSports.setChecked(Boolean.parseBoolean(result[6]));
        chkTechnology.setChecked(Boolean.parseBoolean(result[7]));

//        sharedPreferences = getSharedPreferences("MyUser", MODE_PRIVATE);
//
//        if(sharedPreferences.getString("user","")=="Guest" || sharedPreferences.getString("user","")==""){
//
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(PreferencesActivity.this);
//        builder.setTitle("Uh...oh!");
//        builder.setMessage("Looks like you are logged in as Guest. We cannot save preferences on your Guest Account.Do you want to LogIn?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sharedPreferences = getSharedPreferences("MyUser",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.remove("user");
//                    editor.apply();
//                    Intent intent = new Intent(PreferencesActivity.this,LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        }else{

            btnPrefSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String preferences = "";
                    String[] record = new String[9];
                    record[0] = sharedPreferences.getString("user","");
                    
                    if (chkGeneral.isChecked()){
                        preferences+="General,";
                        record[1]="true";
                    }else {
                        record[1]="false";
                    }
                    if (chkBusiness.isChecked()){
                        preferences+="Business,";
                        record[2]="true";
                    }else {
                        record[2]="false";
                    }
                    if (chkEntertainment.isChecked()){
                        preferences+="Entertainment,";
                        record[3]="true";
                    }else {
                        record[3]="false";
                    }
                    if (chkHealth.isChecked()){
                        preferences+="Health,";
                        record[4]="true";
                    }else {
                        record[4]="false";
                    }
                    if (chkScience.isChecked()){
                        preferences+="Science,";
                        record[5]="true";
                    }else {
                        record[5]="false";
                    }
                    if (chkSports.isChecked()){
                        preferences+="Sports,";
                        record[6]="true";
                    }else {
                        record[6]="false";
                    }
                    if (chkTechnology.isChecked()){
                        preferences+="Technology,";
                        record[7]="true";
                    }else {
                        record[7]="false";
                    }

                    record[8] = preferences;

                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    db.addPreference(record);


//                    SharedPreferences sp = getSharedPreferences("MyArticlePrefs",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//
//                    if (preferences==""){
//                        editor.putString("preferences", preferences);
//                    }else {
//                        editor.putString("preferences", preferences.substring(0,preferences.length()-1));
//
//                    }
                    // Storing the key and its value as the data fetched from edittext

//                    editor.putString("general", String.valueOf(chkGeneral.isChecked()));
//                    editor.putString("business", String.valueOf(chkBusiness.isChecked()));
//                    editor.putString("entertainment", String.valueOf(chkEntertainment.isChecked()));
//                    editor.putString("health", String.valueOf(chkHealth.isChecked()));
//                    editor.putString("science", String.valueOf(chkScience.isChecked()));
//                    editor.putString("sports", String.valueOf(chkSports.isChecked()));
//                    editor.putString("technology", String.valueOf(chkTechnology.isChecked()));
//
//
//                    editor.commit();


                    Intent intent = new Intent(PreferencesActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            });


//        }

    }
}