package com.example.p4projectdaniel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.p4projectdaniel.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    private FirebaseAuth mAuth;
    EditText editTextRegisterUsername;
    EditText editTextRegisterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegisterUsername = (EditText) findViewById(R.id.RegisterUsername);
        editTextRegisterPassword= (EditText) findViewById((R.id.RegisterPassword));


        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.SignUpBtn).setOnClickListener(this);
        findViewById(R.id.BackToLoginBtn).setOnClickListener(this);
    }

    private void registerUser(){
        String Email = editTextRegisterUsername.getText().toString().trim();
        String Password = editTextRegisterPassword.getText().toString().trim();
        if(Email.isEmpty()){
            editTextRegisterUsername.setError("Email is required");
            editTextRegisterUsername.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextRegisterUsername.setError("Please enter a valid email");
            editTextRegisterUsername.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            editTextRegisterPassword.setError("Password is required");
            editTextRegisterPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //Sign Up Success
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, BottomNavActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        editTextRegisterPassword.setText("");
                        Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        editTextRegisterPassword.setText("");
                        Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.SignUpBtn:
                registerUser();
                break;

            case R.id.BackToLoginBtn:
                Intent intentLogin = new Intent(this, MainActivity.class);
                intentLogin.addFlags(intentLogin.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogin);
                finish();

                break;
        }

    }
}