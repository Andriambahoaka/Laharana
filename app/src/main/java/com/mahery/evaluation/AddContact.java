package com.mahery.evaluation;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahery.evaluation.databinding.ActivityAddContactBinding;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAddContactBinding binding;
    private RecyclerView contactRV;

    // Arraylist for storing data
    private ArrayList<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//////////////////////////////





        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAddContact.toolbar);
        binding.appBarAddContact.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_contact);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // contactRV = findViewById(R.id.idListe);
        contactList = new ArrayList<>();
        contactList.add(new Contact("Tsiry Andria","0331240052",R.drawable.profil));
        contactList.add(new Contact("Naly Rakoto","0331240042",R.drawable.profil));


        ContactAdapter carteAdapter= new ContactAdapter(this,contactList);
        // GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactRV = (RecyclerView) findViewById(R.id.idListe);
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        contactRV.setLayoutManager(mLayoutManager);
        contactRV.setAdapter(carteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_contact, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_contact);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}