package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArticleViewActivity extends AppCompatActivity {

    TextView txtTitle,txtDesc,txtSource,txtDate,txtAuthor,txtCategory;
    ImageView newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String imgUrl = getIntent().getStringExtra("image");
        String description = getIntent().getStringExtra("description");
        String publishDate = getIntent().getStringExtra("published_at");
        String source = getIntent().getStringExtra("source");
        String category = getIntent().getStringExtra("category");

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtCategory = findViewById(R.id.txtCategory);
        txtSource = findViewById(R.id.txtSource);
        txtDesc = findViewById(R.id.txtDesc);
        txtDate = findViewById(R.id.txtDate);

        newsImage = findViewById(R.id.imageView);

        txtTitle.setText(title);
        txtAuthor.setText("Author: " + author);
        txtCategory.setText("Category: " + category.toUpperCase());
        txtSource.setText(source.toUpperCase());
        txtDesc.setText(description);
        txtDate.setText("Date: " + publishDate.substring(0,10));

         Glide.with(this).load(imgUrl).into(newsImage);
    }
}