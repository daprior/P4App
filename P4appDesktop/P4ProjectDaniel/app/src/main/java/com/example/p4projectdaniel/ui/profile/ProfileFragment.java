package com.example.p4projectdaniel.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p4projectdaniel.MainActivity;
import com.example.p4projectdaniel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class ProfileFragment extends Fragment{

    TextView textView, textViewEmail;
    EditText editText;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        mAuth = FirebaseAuth.getInstance();
        editText = (EditText) v.findViewById(R.id.editTextDisplayName);
        textViewEmail = v.findViewById(R.id.text_view_email);
        textViewEmail.setText(mAuth.getCurrentUser().getEmail());

        loadUserInformation();

        v.findViewById(R.id.LogOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               saveUserInformation();
           }
        });
        return v;

    }


    private void loadUserInformation() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            if (user.getDisplayName() != null) {
                String displayName = user.getDisplayName();
                editText.setText(displayName);
            }

        }

    }


    private void saveUserInformation() {

        String displayName = editText.getText().toString();
        if (displayName.isEmpty()) {
            editText.setError("Name Required");
            editText.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

        } else {
            Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_LONG).show();
            return;
        }

    }
}