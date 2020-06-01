package com.android.vismay.newsforce.Home;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BlendMode;
import android.net.Uri;
import android.os.Bundle;

import com.android.vismay.newsforce.LoginAndSignup.LoginActivity;
import com.android.vismay.newsforce.R;
import com.android.vismay.newsforce.Utilities.SignUpModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    TextView name,email;
    ImageView img;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavController navController;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,R.id.nav_about_app,R.id.nav_help_and_feedback,
                R.id.nav_settings,R.id.nav_business,R.id.nav_entertainment,R.id.nav_sports,R.id.nav_technology,R.id.nav_country,R.id.nav_worldNews,
                R.id.sources_BBC,R.id.sources_CNN,R.id.sources_FourFourTwo,R.id.sources_FoxNews,R.id.sources_Independent,R.id.sources_TheHindu,R.id.sources_TOI)
                .setDrawerLayout(drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);




        Log.e("Key", getIntent().getStringExtra("Key").toString());

        View header=navigationView.getHeaderView(0);
        img=header.findViewById(R.id.NavHeaderImg);
        name=header.findViewById(R.id.NavHeaderName);
        email=header.findViewById(R.id.NavHeaderEmail);


        addData();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logoutUser() {
        if(getIntent().getStringExtra("Key").toString().compareTo("Google")==0){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(HomeActivity.this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(HomeActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            firebaseAuth=FirebaseAuth.getInstance();
            if(firebaseAuth.getCurrentUser()!=null){
                firebaseAuth.signOut();
            }
        }
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        finish();
    }

    private void addData() {
        if(getIntent().getStringExtra("Key").toString().compareTo("Google")==0){
            addDataGoogle();
        }
        else{
            addDataFirebase();
        }
    }

    private void addDataGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            //String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            //id.setText(personId);

            Glide.with(this).load(String.valueOf(personPhoto)).into(img);
        }
    }

    private void addDataFirebase() {
        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        String emailx="";
        if(firebaseAuth.getCurrentUser()!=null){
            emailx=firebaseAuth.getCurrentUser().getEmail();
        }
        mDatabase.child("users").child(emailx.replace('.', ',')).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SignUpModel model=dataSnapshot.getValue(SignUpModel.class);
                email.setText(model.getEmail());
                name.setText(model.getName());
                Log.e("Name", model.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        int id=item.getItemId();
        switch(id){
            case R.id.nav_sports:
                navController.navigate(R.id.nav_sports);
                break;
            case R.id.nav_business:
                navController.navigate(R.id.nav_business);
                break;
            case R.id.nav_country:
                navController.navigate(R.id.nav_country);
                break;
            case R.id.nav_entertainment:
                navController.navigate(R.id.nav_entertainment);
                break;
            case R.id.nav_technology:
                navController.navigate(R.id.nav_technology);
                break;
            case R.id.nav_worldNews:
                navController.navigate(R.id.nav_worldNews);
                break;
            case R.id.sources_BBC:
                navController.navigate(R.id.sources_BBC);
                break;
            case R.id.sources_CNN:
                navController.navigate(R.id.sources_CNN);
                break;
            case R.id.sources_FourFourTwo:
                navController.navigate(R.id.sources_FourFourTwo);
                break;
            case R.id.sources_FoxNews:
                navController.navigate(R.id.sources_FoxNews);
                break;
            case R.id.sources_Independent:
                navController.navigate(R.id.sources_Independent);
                break;
            case R.id.sources_TheHindu:
                navController.navigate(R.id.sources_TheHindu);
                break;
            case R.id.sources_TOI:
                navController.navigate(R.id.sources_TOI);
                break;
            case R.id.nav_logout:
                logoutUser();
                break;
            case R.id.nav_about_app:
                navController.navigate(R.id.nav_about_app);
                break;
            case R.id.nav_help_and_feedback:
                navController.navigate(R.id.nav_help_and_feedback);
                break;
            case R.id.nav_settings:
                navController.navigate(R.id.nav_settings);
                break;
        }
        return true;
    }

}
