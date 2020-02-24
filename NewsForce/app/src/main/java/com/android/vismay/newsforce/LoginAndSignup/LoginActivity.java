package com.android.vismay.newsforce.LoginAndSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vismay.newsforce.Home.HomeActivity;
import com.android.vismay.newsforce.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    TextView signup_page;
    TextView forgot_password;
    Button login;
    EditText email_txt,password_txt;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_txt = findViewById(R.id.EditText_Email);
        password_txt = findViewById(R.id.EditText_Password);
        login = findViewById(R.id.Button_Login);
        forgot_password = findViewById(R.id.TextView_ForgotPassword);
        signup_page = findViewById(R.id.TextView_SignUp);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
            updateUI("Firebase");

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait a moment...");

        forgot_password.setOnClickListener(this);

        signup_page.setOnClickListener(this);

        login.setOnClickListener(this);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            updateUI("Google");
        }
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI("Google");
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    public void updateUI(String key){
        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("Key", key);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v==login){
            dialog.show();
            String emailx,passwordx;
            emailx=email_txt.getText().toString();
            passwordx=password_txt.getText().toString();

            mAuth.signInWithEmailAndPassword(emailx,passwordx).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this, com.android.vismay.newsforce.Home.HomeActivity.class);
                        intent.putExtra("Key", "Firebase");
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Some Error Occured.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }
            });
        }
        else if(v==signInButton){
            signIn();
        }
        else if(v==signup_page){
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
        else if(v==forgot_password){
            BottomSheetDialogFragmentForgotPass1 bottomSheet=new BottomSheetDialogFragmentForgotPass1();
            bottomSheet.show(getSupportFragmentManager(), "Forgot Password Bottom Sheet");
        }
    }
}
