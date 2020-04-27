package com.nisith.covid19application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CountryPickerRecyclerViewAdapter extends RecyclerView.Adapter<CountryPickerRecyclerViewAdapter.MyViewHolder> {



    private ArrayList<String> allEffectedCountriesName;
    private CountryFlags countryFlags;
    private OnCardItemClickListener onCardItemClickListener;

    public interface OnCardItemClickListener{
        void onCardItemClicked(int position,String countryName, int countryFlagId);
    }

    public CountryPickerRecyclerViewAdapter(AppCompatActivity appCompatActivity, ArrayList<String>  allEffectedCountryInfoArrayList ){
        this.allEffectedCountriesName = allEffectedCountryInfoArrayList;
        countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());
        onCardItemClickListener = (CountrySettingActivity) appCompatActivity;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_country_picker,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String countryName = allEffectedCountriesName.get(position);
        int flagId = countryFlags.getCountryFlag(countryName);
        if (flagId != -1){
            Picasso.get().load(flagId).centerCrop().fit().into(holder.flagImageThumbnail);
        }else {
            holder.flagImageThumbnail.setImageResource(R.drawable.ic_defalt_flag);
        }
        holder.countryName.setText(countryName);
    }



    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedCountriesName != null){
            totalItems = allEffectedCountriesName.size();
        }
        return totalItems;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView flagImageThumbnail;
        TextView countryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImageThumbnail = itemView.findViewById(R.id.flag_image_thumbnail);
            countryName = itemView.findViewById(R.id.country_name_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String countryName = allEffectedCountriesName.get(getAdapterPosition());
                    int flagId = countryFlags.getCountryFlag(countryName);
                  onCardItemClickListener.onCardItemClicked(getAdapterPosition(),countryName,flagId);
                }
            });
        }
    }


}
