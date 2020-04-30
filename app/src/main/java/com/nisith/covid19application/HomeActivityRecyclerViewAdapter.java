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

public class HomeActivityRecyclerViewAdapter extends RecyclerView.Adapter<HomeActivityRecyclerViewAdapter.MyViewHolder> {

    private List<Integer> mostEffectedCountriesIndexList;
    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private CountryFlags countryFlags;
    private OnGridViewClickEventListener gridViewClickEventListener;


    public interface OnGridViewClickEventListener{
        void onGridViewClick(int position, int flagId);
    }

    public HomeActivityRecyclerViewAdapter(List<Integer> mostEffectedCountriesIndexList, List<CountriesInfoModel> allEffectedCountriesInfoList, AppCompatActivity appCompatActivity){
        this.mostEffectedCountriesIndexList = mostEffectedCountriesIndexList;
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        countryFlags = new CountryFlags(appCompatActivity.getApplicationContext());
        this.gridViewClickEventListener = (OnGridViewClickEventListener) appCompatActivity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_appearence,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int mostEffectedCountryIndex = mostEffectedCountriesIndexList.get(position);
        int flagId = countryFlags.getCountryFlag(allEffectedCountriesInfoList.get(mostEffectedCountryIndex).getCountryName());
        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(holder.flagImageView);
        }else {
            holder.flagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
        holder.countryNameTextView.setText(allEffectedCountriesInfoList.get(mostEffectedCountryIndex).getCountryName());
        holder.totalCasesTextView.setText("Total Cases: "+allEffectedCountriesInfoList.get(mostEffectedCountryIndex).getTotalCases());
        holder.totalDeathsTextView.setText("Total Deaths:  "+allEffectedCountriesInfoList.get(mostEffectedCountryIndex).getTotalDeaths());
        holder.activeCasesTextView.setText("Active Cases: "+allEffectedCountriesInfoList.get(mostEffectedCountryIndex).getActivCcases());
    }

    @Override
    public int getItemCount() {
        int totalItem = 0;
        if (mostEffectedCountriesIndexList != null){
            totalItem = mostEffectedCountriesIndexList.size();
        }
        return totalItem;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView flagImageView;
        TextView countryNameTextView;
        TextView totalCasesTextView;
        TextView activeCasesTextView;
        TextView totalDeathsTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImageView = itemView.findViewById(R.id.image_view);
            totalCasesTextView = itemView.findViewById(R.id.total_cases_text_view);
            totalDeathsTextView = itemView.findViewById(R.id.total_deaths_text_view);
            countryNameTextView = itemView.findViewById(R.id.country_name_text_view);
            activeCasesTextView = itemView.findViewById(R.id.active_cases_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mostEffectedCountryIndex = mostEffectedCountriesIndexList.get(getAdapterPosition());
                    int flagId = countryFlags.getCountryFlag(allEffectedCountriesInfoList.get(mostEffectedCountryIndex).getCountryName());
                    gridViewClickEventListener.onGridViewClick(mostEffectedCountryIndex,flagId);
                }
            });
        }
    }

}
