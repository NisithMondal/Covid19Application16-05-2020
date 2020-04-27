package com.nisith.covid19application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisith.covid19application.model.CountriesInfoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AllCountriesRecyclerViewAdapter extends RecyclerView.Adapter<AllCountriesRecyclerViewAdapter.MyViewHoldar> {

    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private OnCountryCardItemClickInterface onCountryCardItemClickInterface;
    private CountryFlags countryFlags;


    public interface OnCountryCardItemClickInterface {
        void onCountryCardItemClick(int position, int countryFlagId);
    }

    public AllCountriesRecyclerViewAdapter(List<CountriesInfoModel> allEffectedCountriesInfoList, AppCompatActivity appCompatActivity){
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        this.onCountryCardItemClickInterface = (AllEffectedCountriesActivity) appCompatActivity;
        countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());
    }

    @NonNull
    @Override
    public MyViewHoldar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_country,parent,false);
        return new MyViewHoldar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldar holder, int position) {
        CountriesInfoModel effectedCountryInfo = allEffectedCountriesInfoList.get(position);

        String countryName = effectedCountryInfo.getCountryName();
        int flagId = countryFlags.getCountryFlag(countryName);
        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(holder.flagImageThumbnail);
        }else {
//            Picasso.get().load(R.drawable.ic_defalt_flag).into(holder.flagImageThumbnail);// Here Picasso not work
            holder.flagImageThumbnail.setImageResource(R.drawable.ic_defalt_flag);
        }

        holder.countryName.setText(countryName);
        holder.totalCases.setText("Total Cases: "+effectedCountryInfo.getTotalCases());
        holder.totalDeaths.setText("Total Deaths: "+effectedCountryInfo.getTotalDeaths());


    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedCountriesInfoList != null){
            totalItems = allEffectedCountriesInfoList.size();
        }
        return totalItems;
    }

    class MyViewHoldar extends RecyclerView.ViewHolder{

        ImageView flagImageThumbnail;
        TextView countryName, totalCases, totalDeaths;

        public MyViewHoldar(@NonNull View itemView) {
            super(itemView);
            flagImageThumbnail = itemView.findViewById(R.id.flag_image_thumbnail);
            countryName = itemView.findViewById(R.id.country_name_text_view);
            totalCases = itemView.findViewById(R.id.total_cases_text_view);
            totalDeaths = itemView.findViewById(R.id.total_deaths_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flagId = countryFlags.getCountryFlag(allEffectedCountriesInfoList.get(getAdapterPosition()).getCountryName());
                    onCountryCardItemClickInterface.onCountryCardItemClick(getAdapterPosition(),flagId);
                }
            });
        }
    }

}
