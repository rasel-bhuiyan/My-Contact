package com.example.my_contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnAdd;
    Button btnShow;
    private ListView listView;
    private SearchView searchView;

    Database_Source database_source;
    ArrayList<Model_Class> arrayList;

    private static final int Request_Call = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        listView =findViewById(R.id.listView);
        searchView = findViewById(R.id.search_bar);


        database_source = new Database_Source(this);
        arrayList =  database_source.getContactList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,data_save.class);
                startActivity(intent);

            }
        });

        // view full details / call / text/ edit

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  Model_Class model_class =  (Model_Class) arrayList.get(position);

                Intent intent = new Intent(MainActivity.this,View_Full_Details.class);
                intent.putExtra("CONTACT", model_class);
                startActivity(intent);
            }
        });


        //delete
         registerForContextMenu(listView);
         //searchView.


    }
// show all contact list


    @Override
    protected void onStart() {
        super.onStart();

            try {
                final Contact_CustomAdapter contact_customAdapter = new Contact_CustomAdapter(this,arrayList);
                listView.setAdapter(contact_customAdapter);



            }catch (Exception e){
                Toast.makeText(this, "show: S "+e, Toast.LENGTH_SHORT).show();
            }

    }


    //delete


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        menu.setHeaderTitle("MENU !!");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == R.id.delete){

           boolean stats = database_source.DeleteContact(arrayList.get(adapterContextMenuInfo.position));

            if(stats){
                Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
            }
        else
            Toast.makeText(this, "delete fail !!", Toast.LENGTH_SHORT).show();

        }
        return super.onContextItemSelected(item);

    }
}
