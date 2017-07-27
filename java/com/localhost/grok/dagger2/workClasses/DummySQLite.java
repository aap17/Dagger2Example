package com.localhost.grok.dagger2.workClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by grok on 3/9/17.
 */

public class DummySQLite extends SQLiteOpenHelper implements DAO {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="DummyDB";
    private static final String TABLE_USERS="users";
    private static final String USER_ID="idUser";
    private static final String USER_NAME="name";
    private static final String USER_DESCR="description";
    private static final String TABLE_IMAGES="images";
    private static final String IMAGE_ID="idImage";
    private static final String IMAGE= "image";



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);


    }



    public DummySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public DummySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" + USER_ID
                + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT," + USER_DESCR + " TEXT, " + IMAGE_ID + " INTEGER)";
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES + " ("+  IMAGE_ID
                + " INTEGER PRIMARY KEY, " + IMAGE + " BLOB "+ ", FOREIGN KEY (" + IMAGE_ID +") REFERENCES " + TABLE_USERS + "("+ IMAGE_ID + "))";

        sqLiteDatabase.execSQL(CREATE_IMAGES_TABLE);
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }




    public ContactJson selectPersonRaw(Integer number)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        //  String[] selectionArgs = { String.valueOf(number) };
        // cursor=db.rawQuery("Select *" +  " FROM "+ TABLE_USERS + " WHERE " + USER_ID + " = "+number,  null);
        cursor=db.rawQuery("Select *" +  " FROM "+ TABLE_USERS + " WHERE " + USER_ID + " = ?",  new String[]{String.valueOf(number)});

        ContactJson result = new ContactJson();
        while (cursor.moveToNext())
        {
            result.setId(cursor.getInt(0));
            result.setName(cursor.getString(1));
            result.setDescription(cursor.getString(2));
        }


        return result;
    }


    public void failmethod()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select row", null);
    }

    @Override
    public void insertPersons(List<ContactJson> json) {

        new InsertItemAsync(this).execute(json);
    }

    @Override
    public ContactJson selectPerson(int id)
    {
        //return unionSelection(id);
        return  joinSelect(id);
    }




    @Override
    public void deletePerson(int number)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USERS,USER_ID +" = ?",new String[]{String.valueOf(number)});

    }


    private ContactJson joinSelect(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        //builder.setTables(TABLE_USERS +"," +TABLE_IMAGES);
        //builder.setTables(TABLE_USERS+"."+IMAGE_ID+ " = "+TABLE_IMAGES+"."+IMAGE_ID);
        builder.setTables(TABLE_USERS+ " LEFT OUTER JOIN " + TABLE_IMAGES + " ON ( " + TABLE_IMAGES + "."+ IMAGE_ID + " = " + TABLE_IMAGES + "."+ IMAGE_ID + ")");
        String selection=builder.buildQuery(new String[]{USER_NAME, USER_DESCR, IMAGE}, USER_ID + " = "+id,null,null,null,null);
        Log.e("SQL:", " "+selection);
        Cursor cursor=db.rawQuery(selection,null);
        ContactJson result = new ContactJson();
        while (cursor.moveToNext())
        {
            result.setName(cursor.getString(0));
            result.setDescription(cursor.getString(1));
            result.setByteImage(cursor.getBlob(2));
        }

        return result;
    }

    private static class InsertItemAsync extends AsyncTask<List<ContactJson>, Void, Void>
    {
        private SQLiteOpenHelper helper;

        @Override
        protected Void doInBackground(List<ContactJson>... contactJsons) {
            insertItemBackground(helper, contactJsons[0]);
            return null;
        }

        public InsertItemAsync(SQLiteOpenHelper helper)
        {
            this.helper=helper;
        }


    }

    private static void insertItemBackground(SQLiteOpenHelper helper, List<ContactJson> items) {

      for (ContactJson contactJson: items) {

          SQLiteDatabase db = helper.getWritableDatabase();
          ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();

          Bitmap bmp = null;
          try {
              URL url = new URL(contactJson.getImage());
              bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
              Log.e("size", "bmp byte count" + bmp.getByteCount());
              bmp.compress(Bitmap.CompressFormat.PNG, 100, bmpStream);
              contactJson.setByteImage(bmpStream.toByteArray());
          } catch (Exception ex) {

          }


          Log.i("Json", " id " + contactJson.getId() + " name " + contactJson.getName() + " imagesize " + bmpStream.size());

          db.insert(TABLE_USERS, null, getUsersData(contactJson));
          db.insert(TABLE_IMAGES, null, getImageData(bmpStream));

      }
    }

    private static ContentValues getUsersData(ContactJson json)
    {
        ContentValues values = new ContentValues();
        values.put(USER_ID, json.getId());
        values.put(USER_NAME, json.getName());
        values.put(USER_DESCR, json.getDescription());

        //values.put(IMAGE, bmp.toByteArray());
        return values;
    }


    private static ContentValues getImageData(ByteArrayOutputStream bmp)
    {
        ContentValues values = new ContentValues();
        if (bmp==null)
        {
            values.put(IMAGE, new byte[]{});
        }
        else {
            values.put(IMAGE, bmp.toByteArray());
        }
        return values;
    }

    @Override
    public void updatePerson(ContactJson json, int id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, getUsersData(json), USER_ID+ " = ?",new String[] { String.valueOf(id)} );
        db.update(TABLE_IMAGES, getImageData(null), IMAGE_ID +" = ?", new String[]{String.valueOf(id)});
    }






}