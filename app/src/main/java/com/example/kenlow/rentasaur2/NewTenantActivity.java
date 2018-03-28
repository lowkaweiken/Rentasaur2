package com.example.kenlow.rentasaur2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;
import java.util.Map;

public class NewTenantActivity extends AppCompatActivity {

    private Toolbar newTenantToolbar;
    private String property_id = null;
    private String tenantName;
    private String tenantPhone;

    private EditText newTenantName;
    private EditText newTenantPhone;

    private Button tenant_btn;

    private static final String TAG = "NewTenantActivity";

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id;
    public String tenant_id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tenant);

        newTenantToolbar = findViewById(R.id.new_tenant_toolbar);
        setSupportActionBar(newTenantToolbar);
        getSupportActionBar().setTitle("Add New Tenant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();

        newTenantName = findViewById(R.id.new_tenant_name);
        newTenantPhone = findViewById(R.id.new_tenant_phone);
        tenant_btn = findViewById(R.id.tenant_btn);

        if (getIntent().hasExtra("property_profile_id") &&
                getIntent().hasExtra("tenant_name") &&
                getIntent().hasExtra("tenant_phone")) {
            property_id = getIntent().getStringExtra("property_profile_id");
            tenantName = getIntent().getStringExtra("tenant_name");
            tenantPhone = getIntent().getStringExtra("tenant_phone");
            Toast.makeText(NewTenantActivity.this, property_id, Toast.LENGTH_LONG).show();

            Log.d("PROPERTY ID: ",property_id );
            Log.d("TENANT NAME: ",tenantName );
            Log.d("TENANT PHONE: ",tenantPhone );


            newTenantName.setText(tenantName);
            newTenantPhone.setText(tenantPhone);

        }

        tenant_btn.setEnabled(true);

        tenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tenantName = newTenantName.getText().toString();
                final String tenantPhone = newTenantPhone.getText().toString();

                if (!TextUtils.isEmpty(tenantName) && !TextUtils.isEmpty(tenantPhone)){

                    tenant_btn.setEnabled(false);

                    final Map<String, Object> tenantMap = new HashMap<>();
                    tenantMap.put("tenant_name", tenantName);
                    tenantMap.put("tenant_phone", tenantPhone);
                    tenantMap.put("property_id", property_id);
                    tenantMap.put("user_id", current_user_id);

                    firebaseFirestore.collection("Tenant")
                            .add(tenantMap)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(NewTenantActivity.this, "Tenant was added", Toast.LENGTH_LONG).show();
                                        Intent mainIntent = new Intent(NewTenantActivity.this, MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();


                                    }
                                }
                            }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String tenantID = documentReference.getId();
                            Log.d(TAG, "DocumentSnapshot added with ID: " + tenantID);

                            Map<String, Object> tenantMap = new HashMap<>();
                            tenantMap.put("tenant_id", tenantID);

                            firebaseFirestore.collection("Tenant").document(tenantID)
                                    .set(tenantMap, SetOptions.merge());

                        }
                    });

                }
            }
        });
    }
}
