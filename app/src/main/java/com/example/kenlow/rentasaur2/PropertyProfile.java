package com.example.kenlow.rentasaur2;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentReference;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wafflecopter.multicontactpicker.MultiContactPicker;
import com.wafflecopter.multicontactpicker.ContactResult;

import java.util.List;

public class PropertyProfile extends AppCompatActivity {

    private Toolbar Property_profile_toolbar;
    private static final String TAG = "PropertyProfile";
    private FirebaseFirestore firebaseFirestore;
    public List<PropertyPost> property_list;
    public String property_id;
    public FloatingActionButton edit_property_btn;
    private static final int CONTACT_PICKER_REQUEST = 991;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_profile);

        Property_profile_toolbar = findViewById(R.id.property_profile_toolbar);
        setSupportActionBar(Property_profile_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();

        edit_property_btn = findViewById(R.id.edit_property_btn);

        getIncomingIntent();

        edit_property_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(PropertyProfile.this, NewPostActivity.class);
                editIntent.putExtra("property_profile_id", property_id);
                startActivity(editIntent);
            }
        });



    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("property_profile_image") &&
                getIntent().hasExtra("property_profile_title") &&
                getIntent().hasExtra("property_profile_address") &&
                getIntent().hasExtra("property_profile_rental") &&
                getIntent().hasExtra("property_profile_info") &&
                getIntent().hasExtra("property_profile_id")){

            String property_imageUrl = getIntent().getStringExtra("property_profile_image");
            String property_name = getIntent().getStringExtra("property_profile_title");
            String property_addr = getIntent().getStringExtra("property_profile_address");
            String property_rental = getIntent().getStringExtra("property_profile_rental");
            String property_info = getIntent().getStringExtra("property_profile_info");
            property_id = getIntent().getStringExtra("property_profile_id");

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

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch(item.getItemId()){

                // Option to logout
                case R.id.action_delete_btn:

                    deleteItem(item.getOrder());
                    Toast.makeText(PropertyProfile.this, "Property deleted", Toast.LENGTH_LONG).show();
                    return super.onOptionsItemSelected(item);

                case R.id.action_add_tenant_btn:
                    //--------------------------------Testing-------------------------------------//

//                    Intent TenantIntent = new Intent(PropertyProfile.this, NewTenantActivity.class);
//                    TenantIntent.putExtra("property_profile_id", property_id);
//                    startActivity(TenantIntent);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){


                    if (ContextCompat.checkSelfPermission(PropertyProfile.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(PropertyProfile.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(PropertyProfile.this, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);

                    } else {
                        addTenant();
                    }

                    } else {
                        // If the build version is Lollipop and below, permission is already granted, so just go into image picker
                        addTenant();
                    }

//                    Toast.makeText(PropertyProfile.this, "Going to Add Tenant Screen", Toast.LENGTH_LONG).show();


                    return super.onOptionsItemSelected(item);


                default:

                    return false;

            }
        }

    private void addTenant() {

            new MultiContactPicker.Builder(PropertyProfile.this) //Activity/fragment context
                    .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                    .hideScrollbar(false) //Optional - default: false
                    .showTrack(true) //Optional - default: true
                    .searchIconColor(Color.WHITE) //Option - default: White
                    .handleColor(ContextCompat.getColor(PropertyProfile.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                    .bubbleColor(ContextCompat.getColor(PropertyProfile.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                    .bubbleTextColor(Color.WHITE) //Optional - default: White
                    .showPickerForResult(CONTACT_PICKER_REQUEST);

    }

    private void deleteItem(int index) {
        firebaseFirestore.collection("Posts")
                .document(property_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PropertyProfile.this, "You successfully deleted this property", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(PropertyProfile.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                Log.d("MyTag", results.get(0).getDisplayName());
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }

}
