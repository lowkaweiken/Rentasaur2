package com.example.kenlow.rentasaur2;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.databinding.DataBindingUtil;

import com.example.kenlow.rentasaur2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore;
    private String current_user_id;
    private FloatingActionButton addPostBtn;
    private BottomNavigationView mainbottomNav;

    private PropertyFragment propertyFragment;
    private TenantFragment tenantFragment;
    private ExpensesFragment expensesFragment;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        // Set the toolbar
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(binding.mainToolbar);

        getSupportActionBar().setTitle("Rentasaur");

        if(mAuth.getCurrentUser() != null){

        mainbottomNav = findViewById(R.id.mainBottomNav);

        // FRAGMENTS
        propertyFragment = new PropertyFragment();
        tenantFragment = new TenantFragment();
        expensesFragment = new ExpensesFragment();

        // Set property layout by default during start
        replaceFragment(propertyFragment);

        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_action_property:
                        addPostBtn.show();
                        replaceFragment(propertyFragment);
                        return true;

                    case R.id.bottom_action_tenants:
                        addPostBtn.hide();
                        replaceFragment(tenantFragment);
                        return true;

                    case R.id.bottom_action_expenses:
                        replaceFragment(expensesFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

        addPostBtn = findViewById(R.id.add_post_btn);

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivity(newPostIntent);

            }
        });

    }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get currently signed in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){
            sendToLogin();

        } else {
            current_user_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){

                        // Check if the result actually exists
                        if(!task.getResult().exists()){
                            Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                            startActivity(setupIntent);
                            finish();
                        }
                    } else {
                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }



    // Create the options in the dropdown menu. Add toolbar to main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        binding.searchView.setMenuItem(menu.findItem(R.id.action_search_btn));

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            // Option to logout
            case R.id.action_logout_btn:

                logOut();

                return true;

            case R.id.action_settings_btn:

                Intent settingsIntent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(settingsIntent);

                return true;

                default:

                    return false;

        }


    }

    private void logOut() {
        mAuth.signOut(); // Sign out
        sendToLogin(); // Send to login page

    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }


    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }

}
