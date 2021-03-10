package com.example.p4projectdaniel.ui.feedback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p4projectdaniel.R;
import com.google.firebase.auth.FirebaseAuth;

public class FeedbackFragment extends Fragment implements View.OnClickListener{

    private EditText editTextMessage;
    private Button buttonSend;
    private String Name;
    private String Email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        buttonSend = (Button) view.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","daniel_wow1@live.dk", null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

}