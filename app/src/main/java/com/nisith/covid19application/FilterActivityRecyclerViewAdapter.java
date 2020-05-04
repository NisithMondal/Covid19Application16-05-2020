package com.nisith.covid19application;

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
import java.util.Collection;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FilterActivityRecyclerViewAdapter extends RecyclerView.Adapter<FilterActivityRecyclerViewAdapter.MyViewHolder> implements Filterable {

    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private List<CountriesInfoModel> anotherAllEffectedCountriesInfoList;
    private OnCountryCardItemClickInterface onCountryCardItemClickInterface;
    private CountryFlags countryFlags;
    private String filterType;




    public interface OnCountryCardItemClickInterface {
        void onCountryCardItemClick(int position,int positionNumberTextViewValue, int countryFlagId);
    }

    public FilterActivityRecyclerViewAdapter(List<CountriesInfoModel> allEffectedCountriesInfoList, AppCompatActivity appCompatActivity){
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        anotherAllEffectedCountriesInfoList = new ArrayList<>();
        this.onCountryCardItemClickInterface = (OnCountryCardItemClickInterface) appCompatActivity;
        countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());
    }


    public void setFilterType(String filterType){
        this.filterType = filterType;
    }



    public void setAnotherAllEffectedCountriesInfoList(List<CountriesInfoModel> anotherAllEffectedCountriesInfoList){
        this.anotherAllEffectedCountriesInfoList.clear();
        this.anotherAllEffectedCountriesInfoList.addAll(anotherAllEffectedCountriesInfoList);
    }


    @NonNull
    @Override
    public FilterActivityRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_filter_country_activity,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FilterActivityRecyclerViewAdapter.MyViewHolder holder, int position) {

        CountriesInfoModel effectedCountryInfo = allEffectedCountriesInfoList.get(position);

        String countryName = effectedCountryInfo.getCountryName();
        int flagId = countryFlags.getCountryFlag(countryName);
        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(holder.flagImageThumbnail);
        }else {
            holder.flagImageThumbnail.setImageResource(R.drawable.ic_defalt_flag);
        }
        int objectPosition = anotherAllEffectedCountriesInfoList.indexOf(effectedCountryInfo);// this line is to solve position number text view value
        holder.positionNumberTextView.setText("NO: "+String.valueOf(objectPosition+1));
        holder.countryName.setText(countryName);
        holder.totalCases.setText("Total Cases: "+effectedCountryInfo.getTotalCases());
        holder.totalDeaths.setText("Total Deaths: "+effectedCountryInfo.getTotalDeaths());
        holder.activeCasesTextView.setText("Active Cases: "+effectedCountryInfo.getActivCcases());
        holder.totalTestTextView.setText("Total Tests: "+effectedCountryInfo.getTotalTests());
        setDotIconOnCardView(holder);
    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedCountriesInfoList != null){
            totalItems = allEffectedCountriesInfoList.size();
        }
        return totalItems;
    }







    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView flagImageThumbnail;
        TextView countryName, totalCases, totalDeaths,activeCasesTextView, totalTestTextView;
        TextView positionNumberTextView;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            flagImageThumbnail = itemView.findViewById(R.id.flag_image_view);
            positionNumberTextView = itemView.findViewById(R.id.position_number_text_view);
            countryName = itemView.findViewById(R.id.country_name_text_view);
            totalCases = itemView.findViewById(R.id.total_cases_text_view);
            totalDeaths = itemView.findViewById(R.id.total_deaths_text_view);
            activeCasesTextView = itemView.findViewById(R.id.active_cases_text_view);
            totalTestTextView = itemView.findViewById(R.id.total_tests_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flagId = countryFlags.getCountryFlag(allEffectedCountriesInfoList.get(getAdapterPosition()).getCountryName());
                    int positionNumberTextViewValue = anotherAllEffectedCountriesInfoList.indexOf(allEffectedCountriesInfoList.get(getAdapterPosition())) + 1;
                    onCountryCardItemClickInterface.onCountryCardItemClick(getAdapterPosition(),positionNumberTextViewValue,flagId);
                }
            });
        }
    }



    @Override
    public Filter getFilter() {
        return new MyFilter();
    }


    private class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //This method runs on background Thread
            String inputString = constraint.toString().trim().toLowerCase();
            List<CountriesInfoModel> countriesInfoModelList = new ArrayList<>();
            if (inputString.length() == 0){
                countriesInfoModelList.addAll(anotherAllEffectedCountriesInfoList);

            }else {
                for (CountriesInfoModel item : anotherAllEffectedCountriesInfoList) {
                    if (item.getCountryName().toLowerCase().contains(inputString)) {
                        countriesInfoModelList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = countriesInfoModelList;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            //This method runs on UI Thread i.e. main Thread
            allEffectedCountriesInfoList.clear();
            allEffectedCountriesInfoList.addAll((List<CountriesInfoModel>) filterResults.values);
            notifyDataSetChanged();

        }
    }










    private void setDotIconOnCardView(FilterActivityRecyclerViewAdapter.MyViewHolder holder){
        if (filterType.equalsIgnoreCase("Total Cases")){
            holder.totalCases.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_green,0,0,0);
            holder.totalDeaths.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.activeCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.totalTestTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
        }else if (filterType.equalsIgnoreCase("Total Deaths")){
            holder.totalCases.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.totalDeaths.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_green,0,0,0);
            holder.activeCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.totalTestTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);

        }else if (filterType.equalsIgnoreCase("Active Cases")){
            holder.totalCases.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.totalDeaths.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.activeCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_green,0,0,0);
            holder.totalTestTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);

        }else {
            holder.totalCases.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.totalDeaths.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.activeCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_white,0,0,0);
            holder.totalTestTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_dot_green,0,0,0);

        }
    }



}
