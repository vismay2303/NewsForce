package com.android.vismay.newsforce.LoginAndSignup;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.vismay.newsforce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BottomSheetDialogFragmentForgotPass1 extends BottomSheetDialogFragment implements View.OnClickListener{

    Button submit_email,submit_otp,resend,submit_pass;
    EditText email,otp,pass,cpass;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_forgot_pass, container, false);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        submit_email=view.findViewById(R.id.Button_Email_Submit_Forgot_Pass);
//        submit_otp=view.findViewById(R.id.Button_OTP_Submit_Forgot_Pass);
        email=view.findViewById(R.id.EditText_Email_Forgot_Password_Dialog);
//        otp=view.findViewById(R.id.EditText_OTP_Password_Dialog);
//        resend=view.findViewById(R.id.Button_Resend_Forgot_Pass);
//        submit_pass=view.findViewById(R.id.Button_Confirm_Pass_Forgot_Pass);
//        pass=view.findViewById(R.id.EditText_Password_Dialog);
//        cpass=view.findViewById(R.id.EditText_Confirm_Password);
        mAuth=FirebaseAuth.getInstance();

//        submit_pass.setOnClickListener(this);
        submit_email.setOnClickListener(this);
//        submit_otp.setOnClickListener(this);
//        resend.setOnClickListener(this);
        return view;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Button_Email_Submit_Forgot_Pass:
                String inputEmail=email.getText().toString();
                sendOTPEmail(inputEmail);
                break;
//            case R.id.Button_OTP_Submit_Forgot_Pass:
//                submit_otp.setVisibility(View.GONE);
//                otp.setVisibility(View.GONE);
//                resend.setVisibility(View.GONE);
//                pass.setVisibility(View.VISIBLE);
//                cpass.setVisibility(View.VISIBLE);
//                submit_pass.setVisibility(View.VISIBLE);
//                break;
//            case R.id.Button_Resend_Forgot_Pass:
//                submit_email.setVisibility(View.VISIBLE);
//                email.setVisibility(View.VISIBLE);
//                otp.setVisibility(View.GONE);
//                submit_otp.setVisibility(View.GONE);
//                resend.setVisibility(View.GONE);
//                checkOTP();
//                break;
//            case R.id.Button_Confirm_Pass_Forgot_Pass:
//                String x_pass=pass.getText().toString();
//                String x_cpass=cpass.getText().toString();
//                if(x_pass.compareTo(x_cpass)!=0)
//                    Toast.makeText(getContext(), "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
//                else
//                    changePasswords(x_pass);
//
//                dismiss();
//                break;
        }
    }
    private void sendOTPEmail(String emailx) {

        mAuth.sendPasswordResetEmail(emailx).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    Toast.makeText(getContext(), "Password Change link has been successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                else{
                    Toast.makeText(getContext(), "Some Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
