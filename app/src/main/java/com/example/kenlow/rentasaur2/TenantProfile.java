package com.example.kenlow.rentasaur2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TenantProfile extends AppCompatActivity {

    private Toolbar Tenant_profile_toolbar;
    private static final String TAG = "TenantProfile";
    private FirebaseFirestore firebaseFirestore;
    public List<TenantPost> tenant_list;
    public String property_id;
    public String tenant_id;
    public String property_name;
    public String tenant_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);

        Tenant_profile_toolbar = findViewById(R.id.tenant_profile_toolbar);
        setSupportActionBar(Tenant_profile_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();

        getIncomingIntent();

    }

    private void getIncomingIntent() {

        if(getIntent().hasExtra("tenant_id")
                && getIntent().hasExtra("tenant_name")
                && getIntent().hasExtra("tenant_property")
                && getIntent().hasExtra("tenant_phone")){

            String tenantID = getIntent().getStringExtra("tenant_id");

            String tenantName = getIntent().getStringExtra("tenant_name");
            String tenantProperty = getIntent().getStringExtra("tenant_property");
            String tenantPhone = getIntent().getStringExtra("tenant_phone");

            setTenantProfile(tenantName, tenantProperty, tenantPhone);

        }


    }

    private void setTenantProfile(String tenantName, String tenantProperty, String tenantPhone) {
        TextView tenant_profile_name = findViewById(R.id.tenant_profile_name);
        tenant_profile_name.setText(tenantName);

        TextView tenant_profile_property = findViewById(R.id.tenant_profile_property);
        tenant_profile_property.setText(tenantProperty);

        TextView tenant_profile_phone = findViewById(R.id.tenant_profile_phone);
        tenant_profile_phone.setText(tenantPhone);
    }
}
