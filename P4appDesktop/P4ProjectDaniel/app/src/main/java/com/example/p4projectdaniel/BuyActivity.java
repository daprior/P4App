package com.example.p4projectdaniel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity {

    ImageView pImage;
    private TextView name;
    private TextView price;
    private TextView seller;
    private TextView sellDate;
    private TextView Desc_tag;
    private TextView Desc_text;
    private Button button_make_offer;
    private Button button_message;
    private Button button_delete;
    boolean mItemClicked = false;
    private String sName;
    private String sEmail;
    private String pName;
    private String bName;
    private String bEmail;
    private int position;
    private String key;
    int imagePosition;
    String stringImageUri;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseRef;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    DatabaseReference userDatabase;
    private List<User> mUser;
    private List<Upload> mUploads;

    @Override
    public void onStart() {
        super.onStart();
        String testEmail = mAuth.getInstance().getCurrentUser().getEmail();
        if (testEmail.equals(sEmail)) {
            button_make_offer.setVisibility(View.GONE);
            button_message.setVisibility(View.GONE);
            button_delete.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "You are seller of this product", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        name = (TextView) findViewById(R.id.product_name);
        price = (TextView) findViewById(R.id.product_price);
        seller = (TextView) findViewById(R.id.product_seller);
        sellDate = (TextView) findViewById(R.id.product_date);
        button_make_offer = (Button) findViewById(R.id.offer_button);
        button_message = (Button) findViewById(R.id.msg_button);
        button_delete = (Button) findViewById(R.id.delete_button);

        pImage = (ImageView) findViewById(R.id.product_image);
        Desc_tag = (TextView) findViewById(R.id.Description_tag);
        Desc_text = (TextView) findViewById(R.id.Description);
        bName = mAuth.getInstance().getCurrentUser().getDisplayName();
        bEmail = mAuth.getInstance().getCurrentUser().getEmail();


        mUploads = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        Intent bundle = getIntent();
        if (bundle != null) {
            position = bundle.getIntExtra("position", 0);
            pName = bundle.getStringExtra("name");
            String pImageUrl = bundle.getStringExtra("imageUrl");
            String pPrice = bundle.getStringExtra("price");

            sName = bundle.getStringExtra("userName");
            key = bundle.getStringExtra("key");
            String date = bundle.getStringExtra("date");
            String desc = bundle.getStringExtra("desc");
            sEmail = bundle.getStringExtra("email");
            name.setText(pName);
            price.setText("₹ " + pPrice);
            seller.setText(sName);
            sellDate.setText(date);
            if (desc != null) {
                Desc_tag.setVisibility(View.VISIBLE);
                Desc_text.setVisibility(View.VISIBLE);
                Desc_text.setText(desc);
            }


            if (pImageUrl != null) {
                String photoUrl = pImageUrl;
                Picasso.get()
                        .load(photoUrl)
                        .into(pImage);
            }
        }


        button_make_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void deleteProduct(){
    }
}