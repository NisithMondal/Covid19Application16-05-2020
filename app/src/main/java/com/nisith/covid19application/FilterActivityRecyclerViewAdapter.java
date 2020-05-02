package com.nisith.covid19application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.squareup.picasso.Picasso;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FilterActivityRecyclerViewAdapter extends RecyclerView.Adapter<FilterActivityRecyclerViewAdapter.MyViewHolder> {


    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private OnCountryCardItemClickInterface onCountryCardItemClickInterface;
    private CountryFlags countryFlags;


    public interface OnCountryCardItemClickInterface {
        void onCountryCardItemClick(int position, int countryFlagId);
    }

    public FilterActivityRecyclerViewAdapter(List<CountriesInfoModel> allEffectedCountriesInfoList, AppCompatActivity appCompatActivity){
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        this.onCountryCardItemClickInterface = (OnCountryCardItemClickInterface) appCompatActivity;
        countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());

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
        holder.positionNumberTextView.setText("No: "+String.valueOf(position+1));
        holder.countryName.setText(countryName);
        holder.totalCases.setText("Total Cases: "+effectedCountryInfo.getTotalCases());
        holder.totalDeaths.setText("Total Deaths: "+effectedCountryInfo.getTotalDeaths());
        holder.activeCasesTextView.setText("Active Cases: "+effectedCountryInfo.getActivCcases());
        holder.totalTestTextView.setText("Total Tests: "+effectedCountryInfo.getTotalTests());
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

        public MyViewHolder(@NonNull View itemView) {
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
                    onCountryCardItemClickInterface.onCountryCardItemClick(getAdapterPosition(),flagId);
                }
            });
        }
    }


}
