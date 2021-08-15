package com.example.my_contact;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class View_Full_Details extends AppCompatActivity  {
    private TextView name,number;
    CircleImageView imageView;
    private ImageView call,text,VideoCall;
    Button edit;

  Model_Class model_class;

    private static final int Request_Call = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__full__details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        try {
            name = findViewById(R.id.Fname);
            number = findViewById(R.id.Fnumber);
            imageView = findViewById(R.id.Fimage);

            call =findViewById(R.id.Fcall);
            text =findViewById(R.id.Ftext);
            edit = findViewById(R.id.Fbtn);

             model_class = (Model_Class) getIntent().getSerializableExtra("CONTACT");

            name.setText(String.valueOf(model_class.getName()));
            number.setText(String.valueOf(model_class.getNumber()));

        }catch (Exception e){
         //   Toast.makeText(this, "error V : "+e, Toast.LENGTH_SHORT).show();
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call_Dial();
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(View_Full_Details.this, "under working ", Toast.LENGTH_SHORT).show();

            }
        });


        Model_Class Bupdate = (Model_Class) getIntent().getSerializableExtra("Bupdate");
        if(Bupdate!=null){
            name.setText(String.valueOf(Bupdate.getName()));
            number.setText(String.valueOf(Bupdate.getNumber()));
        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(View_Full_Details.this,data_save.class);
                intent.putExtra("UPDATE",model_class);
                startActivity(intent);



            }
        });



    }






    private void Call_Dial() {

        String num = number.getText().toString();

        if(num.trim().length()>0){

            if (ContextCompat.checkSelfPermission(View_Full_Details.this,Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED){


                ActivityCompat.requestPermissions(View_Full_Details.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_Call);

            }
            else {
                String dial = "tel:" +num;
                startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(dial)));
            }

        }else
        {
            Toast.makeText(this, "Please Enter Number !!", Toast.LENGTH_SHORT).show();

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == Request_Call){
            if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                Call_Dial();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
