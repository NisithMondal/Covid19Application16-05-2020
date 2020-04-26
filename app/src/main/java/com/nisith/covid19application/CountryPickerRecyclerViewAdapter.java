package com.nisith.covid19application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryPickerRecyclerViewAdapter extends RecyclerView.Adapter<CountryPickerRecyclerViewAdapter.MyViewHoldar> {



    private String allEffectedCountriesName[];
    private Country country;




    public CountryPickerRecyclerViewAdapter(Context context, String  []allEffectedCountryInfoArrayList ){
        this.allEffectedCountriesName = allEffectedCountryInfoArrayList;
        country = new Country(context);
    }

    @NonNull
    @Override
    public CountryPickerRecyclerViewAdapter.MyViewHoldar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_country_picker,parent,false);
        return new MyViewHoldar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldar holder, int position) {
        String countryName = allEffectedCountriesName[position];

        int flagId = country.getCountryFlag(countryName);
        if (flagId != -1){
//            holder.flagImageThumbnail.setImageResource(flagId);
            Picasso.get().load(flagId).centerCrop().fit().into(holder.flagImageThumbnail);
        }else {
            Picasso.get().load(R.drawable.ic_defalt_flag).centerCrop().fit().into(holder.flagImageThumbnail);
        }

        holder.countryName.setText(countryName);




    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedCountriesName != null){
            totalItems = allEffectedCountriesName.length;
        }
        return totalItems;
    }

    class MyViewHoldar extends RecyclerView.ViewHolder{

        ImageView flagImageThumbnail;
        TextView countryName;

        public MyViewHoldar(@NonNull View itemView) {
            super(itemView);
            flagImageThumbnail = itemView.findViewById(R.id.flag_image_thumbnail);
            countryName = itemView.findViewById(R.id.country_name_text_view);

        }
    }


}
