package com.example.kenlow.rentasaur2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.List;

public class NewTenantActivity extends AppCompatActivity {

    private Toolbar newTenantToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tenant);

        newTenantToolbar = findViewById(R.id.new_tenant_toolbar);
        setSupportActionBar(newTenantToolbar);
        getSupportActionBar().setTitle("Add New Tenant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

}
