package com.example.my_contact;

import androidx.annotation.AnimatorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.System.exit;

public class data_save extends AppCompatActivity {
    private Button btnSave;
    private EditText edtName,edtNumber;
    CircleImageView image;


    Database_Source database_source;

    private static final int PICK_IMAGE_REQUEST =1;
    Uri imgUri;
    Bitmap bitmapImage;

    Model_Class model_class;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSave = findViewById(R.id.btnSave);

        edtName = findViewById(R.id.edtName);
            edtNumber = findViewById(R.id.edtNumber);
            image =findViewById(R.id.img);

       database_source = new Database_Source(this);

       model_class = (Model_Class) getIntent().getSerializableExtra("UPDATE");
        if (model_class != null){
            btnSave.setText("UPDATE");
            edtName.setText(model_class.getName());
            edtNumber.setText(model_class.getNumber());


        }


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(model_class !=null){

                        try {
                            int id = model_class.getId();
                            String Uname =edtName.getText().toString();
                            String Unumber = edtNumber.getText().toString();

                            Model_Class Umodel_Class = new Model_Class(id,Uname,Unumber);
                            database_source.UpdateContact(Umodel_Class);
                            Toast.makeText(data_save.this, "success", Toast.LENGTH_SHORT).show();

                            Intent Bintent = new Intent(data_save.this,View_Full_Details.class);
                            Bintent.putExtra("Bupdate",Umodel_Class);
                            startActivity(Bintent);
                            exit(0);


                        }catch (Exception e){
                            Toast.makeText(data_save.this, "failed: "+e, Toast.LENGTH_LONG).show();
                        }



                    }else {
                        try {

                            if (bitmapImage !=null){

                                Model_Class model_class = new Model_Class(edtName.getText().toString(), edtNumber.getText().toString(), bitmapImage);

                                database_source.AddContact(model_class);
                                Toast.makeText(data_save.this, " save successfully", Toast.LENGTH_SHORT).show();
                            }else {

                                Model_Class model_class = new Model_Class(edtName.getText().toString(), edtNumber.getText().toString());

                                database_source.AddContactWithOutPhoto(model_class);
                                Toast.makeText(data_save.this, " save successfully", Toast.LENGTH_SHORT).show();

                            }

                            Intent intent = new Intent(data_save.this,MainActivity.class);
                            startActivity(intent);

                        }catch (Exception e){

                            edtNumber.setHint("Please enter your number !!");
                        }

                    }

                    }


            });

            // pick image form phone
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent objectinteger = new Intent();
                        objectinteger.setType("image/*");
                        objectinteger.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(objectinteger,PICK_IMAGE_REQUEST);

                    }catch (Exception e){
                        e.getMessage();
                    }

                }
            });



    }




    // image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);

                imgUri = data.getData();
                bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);

                    image.setImageBitmap(bitmapImage);

        }catch (Exception e){
            Toast.makeText(this, "image pick error: "+e, Toast.LENGTH_SHORT).show();
        }

    }



}
