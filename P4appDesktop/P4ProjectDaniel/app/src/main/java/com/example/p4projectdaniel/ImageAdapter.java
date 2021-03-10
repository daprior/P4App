package com.example.p4projectdaniel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
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

                    BuyFragment buyFragment = new BuyFragment();
                    Bundle bundle = new Bundle();
                    int position = getAdapterPosition();
                    Upload current = mUploads.get(position);
                    String name = current.getName();
                    bundle.putInt("position", position);
                    bundle.putString("name", name);
                    bundle.putString("price", current.getPrice());

                    if (imageView != null)
                        bundle.putString("imageUrl", current.getImageUrl());
                    else
                        bundle.putString("imageUrl", null);
                    bundle.putString("userName", current.getUserName());
                    bundle.putString("date", current.getDate());
                    bundle.putString("desc", current.getDesc());
                    bundle.putString("email", current.getEmail());
                    bundle.putString("key", current.getKey());
                    /*if (((BitmapDrawable) imageView.getDrawable()).getBitmap() != null)
                        bundle.putParcelable("bitmapImage", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    else
                        bundle.putParcelable("bitmapImage", null);*/
                    buyFragment.setArguments(bundle);


                    ((FragmentActivity) mContext)
                            .getSupportFragmentManager()
                            .beginTransaction().replace(R.id.nav_host_fragment, buyFragment) // HER SKAL LAVES NOGET OM TROR JEG
                            .addToBackStack(null).commit();


                }
            });
        }


    }
}
