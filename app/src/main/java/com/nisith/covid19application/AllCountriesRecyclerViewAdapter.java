package com.nisith.covid19application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisith.covid19application.model.CountriesInfoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AllCountriesRecyclerViewAdapter extends RecyclerView.Adapter<AllCountriesRecyclerViewAdapter.MyViewHoldar> implements Filterable {

    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private List<CountriesInfoModel> anotherAllEffectedCountriesInfoList;
    private OnCountryCardItemClickInterface onCountryCardItemClickInterface;
    private CountryFlags countryFlags;


    public interface OnCountryCardItemClickInterface {
        void onCountryCardItemClick(int position, int countryFlagId);
    }

    public AllCountriesRecyclerViewAdapter(List<CountriesInfoModel> allEffectedCountriesInfoList, AppCompatActivity appCompatActivity){
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        this.onCountryCardItemClickInterface = (AllEffectedCountriesActivity) appCompatActivity;
        countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());
        Log.d("ABCD","allEffectedCountriesInfoList size= "+allEffectedCountriesInfoList.size());

    }


    public void setAnotherAllEffectedCountriesInfoList(List<CountriesInfoModel> anotherAllEffectedCountriesInfoList){
        this.anotherAllEffectedCountriesInfoList = new ArrayList<>(anotherAllEffectedCountriesInfoList);
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
        Log.d("ABCD","allEffectedCountriesInfoList size= "+allEffectedCountriesInfoList.size());


    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedCountriesInfoList != null){
            totalItems = allEffectedCountriesInfoList.size();
        }
        return totalItems;
    }


    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //This method run on background thread
            List<CountriesInfoModel> arrayList = new ArrayList<>();
            String inputString = constraint.toString().toLowerCase().trim();
            if (inputString.length() == 0){
                arrayList.addAll(anotherAllEffectedCountriesInfoList);

            }else {
                for (int i = 0; i<anotherAllEffectedCountriesInfoList.size(); i++){
                    CountriesInfoModel countriesInfoModel = anotherAllEffectedCountriesInfoList.get(i);
                    String countryName = countriesInfoModel.getCountryName();
                    if (countryName.toLowerCase().contains(inputString)){
                        arrayList.add(countriesInfoModel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = arrayList;
            Log.d("ABCD","arrayList size= "+arrayList.size());
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            //This method run on main thread i.e.ui thread thread
            allEffectedCountriesInfoList.clear();
            allEffectedCountriesInfoList.addAll((List<CountriesInfoModel>)filterResults.values);
            notifyDataSetChanged();
        }
    }


    @Override
    public Filter getFilter() {
        return new MyFilter();
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
