package com.example.newsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsPrefsManager";
    private static final String TABLE_SAVED_PREFS = "preferences";
    private static final String KEY_ID = "email";
    private static final String KEY_GENERAL = "general";
    private static final String KEY_BUSINESS = "business";
    private static final String KEY_ENTERTAIN = "entertainment";
    private static final String KEY_HEALTH = "health";
    private static final String KEY_SCIENCE = "science";
    private static final String KEY_SPORTS = "sports";
    private static final String KEY_TECH = "technology";
    private static final String KEY_PREFSTR = "preferences";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SAVED_PREFS + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_GENERAL + " TEXT,"
                + KEY_BUSINESS + " TEXT," + KEY_ENTERTAIN + " TEXT,"
                + KEY_HEALTH + " TEXT," + KEY_SPORTS + " TEXT,"
                + KEY_SCIENCE + " TEXT," + KEY_TECH + " TEXT," + KEY_PREFSTR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_PREFS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addPreference(String[] record) {

        String sqlInsert = "INSERT OR REPLACE INTO " + TABLE_SAVED_PREFS + " VALUES(?,?,?,?,?,?,?,?,?)";

        SQLiteDatabase db = this.getWritableDatabase();


//        ContentValues values = new ContentValues();
//        values.put(KEY_GENERAL, newsModel.getTitle());
//        values.put(KEY_BUSINESS, newsModel.getAuthor());
//        values.put(KEY_ENTERTAIN, newsModel.getImage());
//        values.put(KEY_HEALTH, newsModel.getDescription());
//        values.put(KEY_SPORTS, newsModel.getPublishDate());
//        values.put(KEY_SCIENCE, newsModel.getSource());
//        values.put(KEY_TECH, newsModel.getCategory());

        db.execSQL(sqlInsert,new String[]{record[0],record[1],record[2],record[3],record[4],record[5],record[6],record[7],record[8]});

        // Inserting Row
//        db.insert(TABLE_SAVED_PREFS, null, values);

        db.close(); // Closing database connection
    }

    public String[] findProduct(String email) {
        String query = "Select * FROM " + TABLE_SAVED_PREFS + " WHERE " + KEY_ID + " =  \"" + email + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        String[] prefs = new String[9];

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int i=0;i<cursor.getColumnCount();i++){
                prefs[i]=cursor.getString(i);
            }
            cursor.close();
        } else {
            prefs = null;
        }
        db.close();
        return prefs;
    }


//    // code to get all contacts in a list view
//    public List<NewsModel> getAllContacts() {
//        List<NewsModel> newsModelList = new ArrayList<NewsModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_PREFS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                NewsModel newsModel = new NewsModel();
//                newsModel.setTitle(cursor.getString(0));
//                newsModel.setAuthor(cursor.getString(1));
//                newsModel.setImage(cursor.getString(2));
//                newsModel.setDescription(cursor.getString(3));
//                newsModel.setPublishDate(cursor.getString(4));
//                newsModel.setSource(cursor.getString(5));
//                newsModel.setCategory(cursor.getString(6));
//
//                // Adding contact to list
//                newsModelList.add(newsModel);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return newsModelList;
//    }

//    // Deleting single contact
//    public void deleteNews(NewsModel newsModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SAVED_PREFS, KEY_GENERAL + " = ?",
//                new String[] { newsModel.getTitle() });
//        db.close();
//    }

}
