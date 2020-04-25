package com.nisith.covid19application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisith.covid19application.model.EffectedCountryInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllCountriesRecyclerViewAdapter extends RecyclerView.Adapter<AllCountriesRecyclerViewAdapter.MyViewHoldar> {

    private ArrayList<EffectedCountryInfo> allEffectedCountryInfoArrayList;
    private OnCountryCardItemClickInterface onCountryCardItemClickInterface;


    public interface OnCountryCardItemClickInterface {
        void onCountryCardItemClick(int position);
    }

    public AllCountriesRecyclerViewAdapter(ArrayList<EffectedCountryInfo> allEffectedCountryInfoArrayList, OnCountryCardItemClickInterface onCountryCardItemClickInterface){
        this.allEffectedCountryInfoArrayList = allEffectedCountryInfoArrayList;
        this.onCountryCardItemClickInterface = onCountryCardItemClickInterface;
    }

    @NonNull
    @Override
    public MyViewHoldar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_country,parent,false);
        return new MyViewHoldar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldar holder, int position) {
        EffectedCountryInfo effectedCountryInfo = allEffectedCountryInfoArrayList.get(position);

        //baki set Image View

        holder.countryName.setText(effectedCountryInfo.getCountryName());
        holder.totalCases.setText(effectedCountryInfo.getTotalCaases());
        holder.totalDeaths.setText(effectedCountryInfo.getTotalDeaths());


    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedCountryInfoArrayList != null){
            totalItems = allEffectedCountryInfoArrayList.size();
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
                    onCountryCardItemClickInterface.onCountryCardItemClick(getAdapterPosition());
                }
            });
        }
    }

}
