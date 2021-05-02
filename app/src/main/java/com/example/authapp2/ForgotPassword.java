package com.example.authapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private long backpssedtime;
    private Toast bactast;

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText)findViewById(R.id.email);
        resetPasswordButton = (Button)findViewById(R.id.resetPassword);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }
        });

    }

    @Override
    public void onBackPressed() {
        if(backpssedtime + 2000> System.currentTimeMillis()){
            bactast.cancel();
            startActivity(new Intent(ForgotPassword.this,MainActivity.class));
            return;
        }else{
            bactast = Toast.makeText(getBaseContext(),"Press back again to go to the Login Screen!",Toast.LENGTH_SHORT);
            bactast.show();
        }

        backpssedtime = System.currentTimeMillis();
    }

    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide a valid email address!");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email to reset your password and login again!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this,MainActivity.class));

                }else{
                    Toast.makeText(ForgotPassword.this,"Try again! Something went wrong!",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}