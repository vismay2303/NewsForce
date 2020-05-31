package com.android.vismay.newsforce.Home.OthersFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.vismay.newsforce.R;
import com.android.vismay.newsforce.Utilities.FeedbackModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFeedbackFragment extends Fragment implements View.OnClickListener {

    Button submit;
    RatingBar q1,q2,q3;
    EditText q4,q5;
    CheckBox inq6,toiq6,gnq6;
    RadioGroup q7,q8;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;

    public HelpFeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_help_feedback, container, false);

        submit=rootView.findViewById(R.id.submit_feedback);
        submit.setOnClickListener(this);

        q1=rootView.findViewById(R.id.rating_q1);
        q2=rootView.findViewById(R.id.rating_q2);
        q3=rootView.findViewById(R.id.rating_q3);
        q4=rootView.findViewById(R.id.edittext_q4);
        q5=rootView.findViewById(R.id.edittext_q5);
        inq6=rootView.findViewById(R.id.inShorts_q6);
        toiq6=rootView.findViewById(R.id.toi_q6);
        gnq6=rootView.findViewById(R.id.gnews_q6);
        q7=rootView.findViewById(R.id.radio_group_q7);
        q8=rootView.findViewById(R.id.radio_group_q8);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v==submit){
            int resQ8=findResQ8();
            int resQ7=findResQ7();
            String resQ6=findResQ6();
            if(resQ8!=-1 && resQ7!=-1) {
                FeedbackModel feedbackModel = new FeedbackModel((double) q1.getRating(), (double) q2.getRating(), (double) q3.getRating(),
                        q4.getText().toString(), q5.getText().toString(), resQ6, resQ7, resQ8);
                String emailx=fetchEmailDetails();
                mDatabase.child("Feedback").child(emailx).setValue(feedbackModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Thank You for you valuable opinion", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(getContext(), "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String fetchEmailDetails() {
        if(getActivity().getIntent().getStringExtra("Key").toString().compareTo("Google")==0){
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            if (acct != null) {
                String personEmail = acct.getEmail();
                return personEmail.replace('.', ',');
            }
            else{
                return "";
            }
        }
        else{
            firebaseAuth= FirebaseAuth.getInstance();
            return firebaseAuth.getCurrentUser().getEmail().replace('.', ',');
        }
    }

    private String findResQ6() {
        String res="";
        if(inq6.isChecked()){
            res+="Inshorts ";
        }
        if(toiq6.isChecked()){
            res+="Times Of India ";
        }
        if(gnq6.isChecked()){
            res+="Google News ";
        }
        return res;
    }

    private int findResQ7() {
        switch(q7.getCheckedRadioButtonId()){
            case R.id.halfhour_q9:
                return 1;
            case R.id.onehour_q9:
                return 2;
            case R.id.twohour_q9:
                return 3;
            case R.id.morethantwo_q9:
                return 4;
            default:
                return -1;
        }
    }

    private int findResQ8() {
        switch(q8.getCheckedRadioButtonId()){
            case R.id.yes_q8:
                return 1;
            case R.id.no_q8:
                return 0;
            default:
                return -1;
        }
    }
}
