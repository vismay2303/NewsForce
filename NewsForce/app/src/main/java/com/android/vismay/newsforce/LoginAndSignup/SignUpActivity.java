package com.android.vismay.newsforce.LoginAndSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vismay.newsforce.Home.HomeActivity;
import com.android.vismay.newsforce.R;
import com.android.vismay.newsforce.Utilities.SignUpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    private FirebaseAuth mAuth;
    EditText name,email,password,cpassword,phone,username;
    Button signup;
    ProgressDialog dialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    TextView username_exist,email_exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar=findViewById(R.id.toolbar_signup);
        name=findViewById(R.id.EditText_Name_SignUp);
        email=findViewById(R.id.EditText_Email_Signup);
        phone=findViewById(R.id.EditText_Phone_SignUp);
        password=findViewById(R.id.EditText_Password_SignUp);
        cpassword=findViewById(R.id.EditText_Confirm_Password_SignUp);
        signup=findViewById(R.id.Button_SignUp);
        username=findViewById(R.id.EditText_UserName_SignUp);
        username_exist=findViewById(R.id.TextView_Username_Exists_Signup);
        email_exist=findViewById(R.id.TextView_Email_Exists_Signup);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();

        mAuth.signOut();

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait a moment...");

        final List<String> list_usernames=new ArrayList<>();
        final List<String> list_emails=new ArrayList<>();
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_usernames.clear();
                list_emails.clear();
                for(DataSnapshot mData:dataSnapshot.getChildren()){
                    SignUpModel signUpModel=mData.getValue(SignUpModel.class);
                    list_usernames.add(signUpModel.getUsername());
                    list_emails.add(signUpModel.getEmail());
                }
                Log.e("Vismay",list_usernames.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(list_usernames.contains(s.toString())){
                    username_exist.setTextColor(getResources().getColor(R.color.red));
                    username_exist.setVisibility(View.VISIBLE);

                }
                else{
                    username_exist.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(list_emails.contains(s.toString())){
                    email_exist.setVisibility(View.VISIBLE);
                }
                else{
                    email_exist.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==signup){
                    dialog.show();
                    final String emailx=email.getText().toString();
                    String passwordx=password.getText().toString();
                    String cpasswordx=cpassword.getText().toString();
                    final String usernamex=username.getText().toString();
                    final String namex=name.getText().toString();
                    final String phonex=phone.getText().toString();
//
                    int flag=0;

                    if(namex.length()==0 || emailx.length()==0 || passwordx.length()==0 ||cpasswordx.length()==0 || phonex.length()==0 || usernamex.length()==0){
                        Toast.makeText(SignUpActivity.this,"Fields can't remain empty",Toast.LENGTH_SHORT).show();
                        flag=-1;
                    }
                    if(!emailx.endsWith(".com")){
                        Toast.makeText(SignUpActivity.this,"Please Enter a Valid Email Address.",Toast.LENGTH_SHORT).show();
                        flag=-1;
                    }
                    if(passwordx.compareTo(cpasswordx)!=0){
                        Toast.makeText(SignUpActivity.this,"Passwords entered do not match.",Toast.LENGTH_SHORT).show();
                        flag=-1;
                    }
                    if(passwordx.length()<=6){
                        Toast.makeText(SignUpActivity.this,"Password must be more than 6 characters.",Toast.LENGTH_SHORT).show();
                        flag=-1;
                    }
                    if(username_exist.getVisibility()==View.VISIBLE){
                        Toast.makeText(SignUpActivity.this,"Please use another username.",Toast.LENGTH_SHORT).show();
                        flag=-1;
                    }
                    for(int i=0;i<passwordx.length();i++){
                        if((int)passwordx.charAt(i)<65 && (int)passwordx.charAt(i)>90)
                        {
                            Toast.makeText(SignUpActivity.this,"Password must have an uppercase letter",Toast.LENGTH_SHORT).show();
                            flag=-1;
                        }
                        if((int)passwordx.charAt(i)<97 && (int)passwordx.charAt(i)>122)
                        {
                            Toast.makeText(SignUpActivity.this,"Password must have a lowercase letter",Toast.LENGTH_SHORT).show();
                            flag=-1;
                        }
                        if((int)passwordx.charAt(i)<48 && (int)passwordx.charAt(i)>57)
                        {
                            Toast.makeText(SignUpActivity.this,"Password must have a digit",Toast.LENGTH_SHORT).show();
                            flag=-1;
                        }
                    }

                    //adding values
                    if(flag==0){
                        mAuth.createUserWithEmailAndPassword(emailx,passwordx).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    SignUpModel obj=new SignUpModel(emailx,namex,phonex,usernamex,"on");String emailxx=emailx;
                                    emailxx=emailxx.replace('.',',');
                                    mDatabase.child("users").child(emailxx).setValue(obj);



                                    Toast.makeText(SignUpActivity.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpActivity.this, HomeActivity.class);
                                    intent.putExtra("Key", "Firebase");
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, "Some Error Occured.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onStart() {
        mAuth.signOut();
        super.onStart();
    }
}
