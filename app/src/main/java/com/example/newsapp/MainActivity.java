package com.example.newsapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NewsInterface{

    public String username;

    ArrayList<NewsModel> newsModels = new ArrayList<>();

    private static final String API_KEY = "http://api.mediastack.com/v1/news?access_key=30bc1df50880ea05828f3cddb4f6cac1";

    RecyclerView recyclerView;

    SharedPreferences sharedPreferences;

    String request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_news);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setNewsModels();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.itemRefresh:
                //code for refresh

                intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);

                return true;
            case R.id.itemPrefs:
                //code for preferences

                sharedPreferences = getSharedPreferences("MyUser", MODE_PRIVATE);

                if(sharedPreferences.getString("user","")=="Guest" || sharedPreferences.getString("user","")==""){

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Uh...oh!");
                    builder.setMessage("Looks like you are logged in as Guest. We cannot save preferences on your Guest Account.Do you want to LogIn?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences = getSharedPreferences("MyUser",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("user");
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    intent = new Intent(MainActivity.this,PreferencesActivity.class);
                    startActivity(intent);
                }
                return true;

            case R.id.itemSettings:
                //code for logout
                intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                return true;

            case R.id.itemLogout:
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
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

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void setNewsModels() {

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Fetching fresh articles just for you...");
//        progressDialog.show();

        SharedPreferences sh = getSharedPreferences("MyUser", MODE_PRIVATE);
        username = sh.getString("user", "");
        request = API_KEY + "&countries=ca";
        if (username.length() <= 5){
            request = API_KEY + "&countries=ca";
        }
        else {

            DatabaseHandler db = new DatabaseHandler(this);
            username = sh.getString("user","");

            String[] result = db.findProduct(username);

            String categories = result[result.length - 1].toLowerCase();

            if (categories.length()<=5){
                request = API_KEY + "&countries=ca";
            }else{
                request = API_KEY + "&languages=en&categories="+categories.substring(0,categories.length()-1);
            }

        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray resultsArr = obj.getJSONArray("data");

                            for (int i = 0; i < resultsArr.length(); i++) {

                                JSONObject resultsArrObject = resultsArr.getJSONObject(i);

                                String title = resultsArrObject.getString("title");
                                String author = resultsArrObject.getString("author");
                                String imgUrl = resultsArrObject.getString("image");
                                String description = resultsArrObject.getString("description");
                                String publishDate = resultsArrObject.getString("published_at");
                                String source = resultsArrObject.getString("source");
                                String category = resultsArrObject.getString("category");

                                if (author==null || author=="" || author=="null"){
                                    author = "Anonymous";
                                }

                                newsModels.add(new NewsModel(title,author,imgUrl,description,publishDate,source,category));
                            }

                            News_RecyclerAdapter adapter = new News_RecyclerAdapter(MainActivity.this,newsModels,MainActivity.this);
                            recyclerView.setAdapter(adapter);
//                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    void signOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this,gso);
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onArticleClick(int position) {

        Intent intent = new Intent(MainActivity.this,ArticleViewActivity.class);

        intent.putExtra("title",newsModels.get(position).getTitle());
        intent.putExtra("author",newsModels.get(position).getAuthor());
        intent.putExtra("image",newsModels.get(position).getImage());
        intent.putExtra("description",newsModels.get(position).getDescription());
        intent.putExtra("published_at",newsModels.get(position).getPublishDate());
        intent.putExtra("source",newsModels.get(position).getSource());
        intent.putExtra("category",newsModels.get(position).getCategory());

        startActivity(intent);
    }

//    @Override
//    public void onChkSaveClick(int position) {
//
//        DatabaseHandler db = new DatabaseHandler(this);
//
//        db.addNews(newsModels.get(position));
//
//        Toast.makeText(this, "Article Saved", Toast.LENGTH_LONG).show();
//
////        newsModels.get(position).setSelected(true);
//
//    }
//
//    @Override
//    public void onChkNoSaveClick(int position) {
//
//        DatabaseHandler db = new DatabaseHandler(this);
//
//        db.addNews(newsModels.get(position));
//
////        newsModels.get(position).setSelected(false);
//
//        Toast.makeText(this, "Article Removed", Toast.LENGTH_LONG).show();
//
//    }

}