package com.example.kenlow.rentasaur2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PropertyProfile extends AppCompatActivity {

    private Toolbar Property_profile_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_profile);

        Property_profile_toolbar = findViewById(R.id.property_profile_toolbar);
        setSupportActionBar(Property_profile_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getIncomingIntent();



    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("property_profile_image") &&
                getIntent().hasExtra("property_profile_title") &&
                getIntent().hasExtra("property_profile_address") &&
                getIntent().hasExtra("property_profile_rental") &&
                getIntent().hasExtra("property_profile_info")){

            String property_imageUrl = getIntent().getStringExtra("property_profile_image");
            String property_name = getIntent().getStringExtra("property_profile_title");
            String property_addr = getIntent().getStringExtra("property_profile_address");
            String property_rental = getIntent().getStringExtra("property_profile_rental");
            String property_info = getIntent().getStringExtra("property_profile_info");

            setImage(property_imageUrl, property_name, property_addr, property_rental, property_info);
        }

    }

    private void setImage(String property_imageUrl, String property_name, String property_addr, String property_rental, String property_info){

        TextView prop_profile_name = findViewById(R.id.property_profile_title);
        prop_profile_name.setText(property_name);

        TextView prop_profile_address = findViewById(R.id.property_profile_address);
        prop_profile_address.setText(property_addr);

        TextView prop_profile_rental = findViewById(R.id.property_profile_rental);
        prop_profile_rental.setText(property_rental);

        TextView prop_profile_info = findViewById(R.id.property_profile_info);
        prop_profile_info.setText(property_info);


        ImageView image = findViewById(R.id.property_profile_image);

        Glide.with(this)
                .asBitmap()
                .load(property_imageUrl)
                .into(image);

    }

    // Create the options in the dropdown menu. Add toolbar to main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu, menu);

        return true;

    }


}
