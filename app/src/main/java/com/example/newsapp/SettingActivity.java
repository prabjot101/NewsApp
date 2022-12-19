package com.example.newsapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView name,email;
    Button logOut ;
    ImageView pic, btn_back;
    Switch aSwitch;
    ImageView btn_pref, btn_about, btn_contact, btn_feedback;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        logOut = findViewById(R.id.log_out);
        pic = findViewById(R.id.pic_);
        aSwitch = findViewById(R.id.myswitch);
        btn_pref = findViewById(R.id.preference_btn);
        btn_about = findViewById(R.id.about_btn);
        btn_contact = findViewById(R.id.contact_btn);
        btn_feedback = findViewById(R.id.feedback_btn);
        btn_back = findViewById(R.id.btn_back);


        sharedPreferences = getSharedPreferences("MyUser", MODE_PRIVATE);

//        if (sharedPreferences.getString("mode","light")=="dark"){
//            aSwitch.setChecked(true);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("mode","dark");
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }else {
//            aSwitch.setChecked(false);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("mode","dark");
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }


        if(sharedPreferences.getString("user","")=="Guest" || sharedPreferences.getString("user","")==""){
            name.setText("Guest User");
            email.setText("guest@newsapp.com");
        }else{
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this,gso);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if(acct!=null){
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();

                name.setText(personName);
                email.setText(personEmail);
            }
        }


        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // define Intent object with action attribute as ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);

                String[] TO = {"newsappcsis3175@gmail.com"};

                // add three fields to intent using putExtra function
                intent.putExtra(Intent.EXTRA_EMAIL, TO );
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.putExtra(Intent.EXTRA_TEXT, "Please enter feedback here...");

                // set type of intent
                intent.setType("message/rfc822");

                // startActivity with intent with chooser as Email client using createChooser function
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));



            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(SettingActivity.this);
                dialog.setContentView(R.layout.custom_layout2);

                ImageView close = dialog.findViewById(R.id.btn_close);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(SettingActivity.this);
                dialog.setContentView(R.layout.custom_layout1);

                ImageView close = dialog.findViewById(R.id.btn_close);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btn_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getSharedPreferences("MyUser", MODE_PRIVATE);

                if(sharedPreferences.getString("user","")=="Guest" || sharedPreferences.getString("user","")==""){

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SettingActivity.this);
                    builder.setTitle("Uh...oh!");
                    builder.setMessage("Looks like you are logged in as Guest. We cannot save preferences on your Guest Account.Do you want to LogIn?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences = getSharedPreferences("MyUser",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("user");
                            editor.apply();
                            Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    finish();
                    Intent intent = new Intent(SettingActivity.this,PreferencesActivity.class);
                    startActivity(intent);
                }





//
//                finish();
//                Intent intent = new Intent(SettingActivity.this,PreferencesActivity.class);
//                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
//            SharedPreferences sp = getSharedPreferences("MyArticlePrefs",MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
            @Override
            public void onClick(View view) {
                if(aSwitch.isChecked()){
//                    editor.putString("mode", "dark");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
//                    editor.putString("mode", "light");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences = getSharedPreferences("MyUser",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (sharedPreferences.getString("user","")=="Guest" || sharedPreferences.getString("user","")==""){
                            editor.remove("user");
                            editor.apply();
                            Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            editor.remove("user");
                            editor.apply();
                            signOut();
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        });


    }

    void signOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this,gso);
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}