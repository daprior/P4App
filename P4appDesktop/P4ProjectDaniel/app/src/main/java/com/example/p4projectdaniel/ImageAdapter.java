package com.example.p4projectdaniel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p4projectdaniel.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Upload> mUploads;


    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewPrice.setText("Dkk " + uploadCurrent.getPrice());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerInside()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageView = itemView.findViewById(R.id.image_view_upload);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BuyActivity buyActivity = new BuyActivity(); //Create buyFragment

                    Intent bundle = new Intent(mContext.getApplicationContext(), BuyActivity.class);

                    int position = getAdapterPosition();
                    Upload current = mUploads.get(position);
                    String name = current.getName();
                    bundle.putExtra("position", position);
                    bundle.putExtra("name", name);
                    bundle.putExtra("price", current.getPrice());

                    if (imageView != null)
                        bundle.putExtra("imageUrl", current.getImageUrl());
                    else
                        bundle.putExtra("imageUrl", (byte[]) null);
                    bundle.putExtra("userName", current.getUserName());
                    bundle.putExtra("date", current.getDate());
                    bundle.putExtra("desc", current.getDesc());

                    bundle.putExtra("mobile", current.getMobile());
                    bundle.putExtra("location", current.getLocation());

                    bundle.putExtra("email", current.getEmail());
                    bundle.putExtra("key", current.getKey());

                    mContext.startActivity(bundle);


                    // buyFragment.setArguments(bundle);


                    //((FragmentActivity) mContext)


                    //       .getSupportFragmentManager()
                    //        .beginTransaction().replace(R.id.nav_host_fragment, buyFragment)
                    //        .addToBackStack(null).commit();

                }
            });
        }
    }
}
