package com.technicalnisith.covid19updates;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technicalnisith.covid19updates.model.CountryInfoSearchHistoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AffectedCountryHistorySearchRecyclerViewAdapter extends RecyclerView.Adapter<AffectedCountryHistorySearchRecyclerViewAdapter.MyViewHolder> {

    private List<CountryInfoSearchHistoryModel> allAffectedCountryReportList;
    private OnCardItemClickListener cardItemClickListener;
    private CountryFlags countryFlags;
    private String countryName;


    public interface OnCardItemClickListener{
        void onCardItemClick(int position, String countryName, int flagId);
    }


    public AffectedCountryHistorySearchRecyclerViewAdapter(String countryName,List<CountryInfoSearchHistoryModel> allAffectedCountryReportList, AppCompatActivity appCompatActivity){
        this.countryName = countryName;
        this.allAffectedCountryReportList = allAffectedCountryReportList;
        this.cardItemClickListener = (OnCardItemClickListener) appCompatActivity;
        this.countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_history_search,parent,false);
        Log.d("QWE","onCreateViewHolder() is called");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("QWE","onBindViewHolder() is called");

        CountryInfoSearchHistoryModel countryReport = allAffectedCountryReportList.get(position);
        int flagId = countryFlags.getCountryFlag(countryName);
        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(holder.flagImageView);
        }else {
            holder.flagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
        holder.dateTextView.setText("Date: "+countryReport.getDate());
        holder.totalCasesTextView.setText("Total Cases: "+String.valueOf(countryReport.getTotalCases()));
        holder.totalRecoveredTextView.setText("Recovered: "+String.valueOf(countryReport.getRecovered()));
        holder.deathsTextView.setText("Total Deaths: "+String.valueOf(countryReport.getDeaths()));

    }

    @Override
    public int getItemCount() {
        Log.d("QWE","getItemCount() is called");
        int totalItem = 0;
        if (allAffectedCountryReportList != null){
            totalItem = allAffectedCountryReportList.size();
        }
        return totalItem;
    }


    public void setCountryName(String countryName){
        this.countryName = countryName;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView flagImageView;
        TextView dateTextView,totalCasesTextView,totalRecoveredTextView,deathsTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImageView = itemView.findViewById(R.id.flag_image_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            totalCasesTextView = itemView.findViewById(R.id.total_cases_text_view);
            totalRecoveredTextView = itemView.findViewById(R.id.total_recovered_text_view);
            deathsTextView = itemView.findViewById(R.id.deaths_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flagId = countryFlags.getCountryFlag(countryName);
                    cardItemClickListener.onCardItemClick(getAdapterPosition(),countryName,flagId);
                }
            });


        }
    }
}
