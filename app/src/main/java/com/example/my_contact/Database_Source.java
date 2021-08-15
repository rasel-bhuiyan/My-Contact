package com.example.my_contact;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Database_Source  {

    DataBaseCreateHelper dataBaseCreateHelper;
    SQLiteDatabase sqLiteDatabase;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageByete,getImageByete;

    Context context;
    public Database_Source(Context context) {

         dataBaseCreateHelper = new DataBaseCreateHelper(context);
       this.context = context;
    }

    public  void Open(){
        sqLiteDatabase = dataBaseCreateHelper.getWritableDatabase();
            }
    public  void Close(){
        dataBaseCreateHelper.close();
       }

       //insert and save

    public boolean AddContact(Model_Class model_class){
        this.Open();

        ContentValues contentValues = new ContentValues();

        //for image
        Bitmap image   = model_class.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
       image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        imageByete = byteArrayOutputStream.toByteArray();

        contentValues.put(dataBaseCreateHelper.COL_NAME,model_class.getName());
        contentValues.put(dataBaseCreateHelper.COL_NUMBER,model_class.getNumber());
        contentValues.put(dataBaseCreateHelper.COL_IMAGE,imageByete);

        long check = sqLiteDatabase.insert(dataBaseCreateHelper.TABLE_CONTACT_INFO,null,contentValues);

        if (check != 0)
        return  true;
        else return false;

    }

    // AddContactWithOutPhoto
    public boolean AddContactWithOutPhoto(Model_Class model_class){
        this.Open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dataBaseCreateHelper.COL_NAME,model_class.getName());
        contentValues.put(dataBaseCreateHelper.COL_NUMBER,model_class.getNumber());

        long check = sqLiteDatabase.insert(dataBaseCreateHelper.TABLE_CONTACT_INFO,null,contentValues);


        if (check != 0)
            return  true;
        else return false;
    }

    //data show

    public ArrayList<Model_Class> getAllContact(){
        this.Open();

        ArrayList<Model_Class> Contact_arrayList  = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(dataBaseCreateHelper.TABLE_CONTACT_INFO,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex(DataBaseCreateHelper.COL_NAME));
                long number = cursor.getInt(cursor.getColumnIndex(DataBaseCreateHelper.COL_NUMBER));
                byte[] byteimage = cursor.getBlob(cursor.getColumnIndex(DataBaseCreateHelper.COL_IMAGE));

                Bitmap imageBitmap = BitmapFactory.decodeByteArray(byteimage,0,byteimage.length);

            }while (cursor.moveToFirst());
        }


    return Contact_arrayList;
    }



    ArrayList<Model_Class> getContactList(){
        this.Open();

        ArrayList<Model_Class> arrayList = new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DataBaseCreateHelper.TABLE_CONTACT_INFO,null);


            if(cursor.getCount()!=0){

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);

                    Model_Class model_class = new Model_Class(id,name,number);
                    arrayList.add(model_class);


                }


            }

        }catch (Exception e){
            Toast.makeText(context, "error G: "+e, Toast.LENGTH_SHORT).show();
        }

        return  arrayList;
    }


    //update

   public boolean UpdateContact(Model_Class model_class){
       this.Open();

       ContentValues contentValues = new ContentValues();

       contentValues.put(dataBaseCreateHelper.COL_NAME,model_class.getName());
       contentValues.put(dataBaseCreateHelper.COL_NUMBER,model_class.getNumber());

       int UpdatedRow =      sqLiteDatabase.update(DataBaseCreateHelper.TABLE_CONTACT_INFO,contentValues,DataBaseCreateHelper.COL_ID+" =?",new String[]{String.valueOf(model_class.getId())});
       if (UpdatedRow > 0)
           return  true;
       else return false;

    }

    //delete

    boolean DeleteContact(Model_Class model_class){
    this.Open();

    int check = sqLiteDatabase.delete(dataBaseCreateHelper.TABLE_CONTACT_INFO,dataBaseCreateHelper.COL_ID+"= ?",new String[]{String.valueOf(model_class.getId())});
       this.Close();

       if(check>=0)
           return true;
       else
           return false;


    }



}
