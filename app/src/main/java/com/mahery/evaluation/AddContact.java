package com.mahery.evaluation;

import android.content.Context;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbb20.CountryCodePicker;
import com.mahery.evaluation.databinding.ActivityAddContactBinding;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddContact extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAddContactBinding binding;
    private RecyclerView contactRV;

    private EditText nomEdt, numeroEdt;
    private CircleImageView imageEdt;
    private byte[] imageByte;
    private Button addContactBtn;
    private DBHandler dbHandler;
    CountryCodePicker ccp;



    // Arraylist for storing data
    private ArrayList<Contact> contactList;
    ContactAdapter contactAdapter;

    static int IMAGE_PICKING = 12;
    static int IMAGE_CAPTURE = 11;
    public SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //////////////// Row Too big ///////////////////////////////
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }


        //////////////////////////////// Initialisaton ////////////////////////
        nomEdt = (EditText) findViewById(R.id.nom);
        numeroEdt = (EditText) findViewById(R.id.numero);
        imageEdt = findViewById(R.id.image);
        addContactBtn = findViewById(R.id.enregistrer);
        ccp = (CountryCodePicker) findViewById(R.id.countryP);





        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(AddContact.this);

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


       /* slidingRootNav=     new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(binding.appBarAddContact.toolbar)
                 .withMenuOpened(false)
                .withMenuLayout(R.layout.app_bar_add_contact)
                .inject();*/










        // contactRV = findViewById(R.id.idListe);
      /*  contactList = new ArrayList<>();
        contactList.add(new Contact("Tsiry Andria","0331240052",R.drawable.profil));
        contactList.add(new Contact("Naly Rakoto","0331240042",R.drawable.profil)); */
        contactList = dbHandler.findAllContact();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                numeroEdt.setText("+"+ccp.getSelectedCountryCode());
            }
        });
        contactAdapter= new ContactAdapter(this,contactList);
        // GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactRV = (RecyclerView) findViewById(R.id.idListe);
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        contactRV.setLayoutManager(mLayoutManager);
        contactRV.setAdapter(contactAdapter);



        // below line is to add on click listener for our add course button.
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String nom = nomEdt.getText().toString();
                String numero = numeroEdt.getText().toString();
            ///    int image = imageEdt.getDrawable();


                // validating if the text fields are empty or not.
                if (nom.isEmpty() && numero.isEmpty()) {
                    Toast.makeText(AddContact.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }


                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.

                dbHandler.addNewContact(nom,numero, imageByte);
                contactList = dbHandler.findAllContact();
                contactAdapter= new ContactAdapter(AddContact.this,contactList);
                contactAdapter.notifyDataSetChanged();
                contactRV.setAdapter(contactAdapter);
                // after adding the data we are displaying a toast message.
                //Toast.makeText(AddContact.this, "Course has been added.", Toast.LENGTH_SHORT).show();
                nomEdt.setText("");
                numeroEdt.setText("");
                imageEdt.setImageDrawable(getResources().getDrawable(R.drawable.portrait));

            }
        });




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

    public void loadPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selectionner l'image"), IMAGE_PICKING);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == IMAGE_PICKING) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageEdt.setImageURI(selectedImageUri);
                    Bitmap bitmap=getBitmap(this,selectedImageUri);
                    this.imageByte=convertBitmapToByteArray(bitmap);


                }
            }
        }
    }

    private Bitmap getBitmap(Context context, Uri imageUri){
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }
}