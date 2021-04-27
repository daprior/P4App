package com.example.p4projectdaniel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    // FIELDS
    private FirebaseAuth mAuth;
    EditText editTextUsername;
    EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextUsername = (EditText) findViewById(R.id.Username);
        editTextPassword = (EditText) findViewById(R.id.Password);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.LoginBtn).setOnClickListener(this);
        findViewById(R.id.RegisterBtn).setOnClickListener(this);
    }

    private void userLogin(){
        String Email = editTextUsername.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if(Email.isEmpty()){
            editTextUsername.setError("Email is required");
            editTextUsername.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextUsername.setError("Please enter a valid email");
            editTextUsername.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    // SIGN IN SUCCESS.

                    mAuth.getCurrentUser();
                    finish();
                    Intent intent = new Intent(MainActivity.this, BottomNavActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    // SIGN IN SUCCESS WITH EMAIL VERIFICATION.

                    //if (mAuth.getCurrentUser().isEmailVerified()) {
                    //    finish();
                    //    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    //    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //    startActivity(intent);
                }
                else {

                    //

                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(this,BottomNavActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.LoginBtn:
                userLogin();
                break;

            case R.id.RegisterBtn:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }
    }
}