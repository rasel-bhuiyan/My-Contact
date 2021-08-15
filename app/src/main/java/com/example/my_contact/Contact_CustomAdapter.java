package com.example.my_contact;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Contact_CustomAdapter extends ArrayAdapter<Model_Class> {

    Context context;
    ArrayList<Model_Class> arrayList;

    public Contact_CustomAdapter(@NonNull Context context, ArrayList<Model_Class> arrayList) {
        super(context, R.layout.contact_single_row,arrayList);
       this.context = context;
       this.arrayList = arrayList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater =  LayoutInflater.from(context);
                View v = layoutInflater.inflate(R.layout.contact_single_row,parent,false);

        ImageView imageView = v.findViewById(R.id.Simage);
        TextView name = v.findViewById(R.id.Sname);
        TextView number =  v.findViewById(R.id.Snumber);

       name.setText(String.valueOf(arrayList.get(position).getName()));
       number.setText(String.valueOf(arrayList.get(position).getNumber()));


        //   imageView.setImageBitmap(arrayList.get(position).getImage());



        return v;
    }
}
