package com.nisith.covid19application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CountryPickerRecyclerViewAdapter extends RecyclerView.Adapter<CountryPickerRecyclerViewAdapter.MyViewHolder> implements Filterable {



    private ArrayList<String> allEffectedCountriesNameArrayList;
    private ArrayList<String> anotherAllEffectedCountriesNameArrayList;
    private CountryFlags countryFlags;
    private OnCardItemClickListener onCardItemClickListener;



    public interface OnCardItemClickListener{
        void onCardItemClicked(int position,String countryName, int countryFlagId);
    }

    public CountryPickerRecyclerViewAdapter(AppCompatActivity appCompatActivity, ArrayList<String>  allEffectedCountriesNameArrayList ){
        this.allEffectedCountriesNameArrayList = allEffectedCountriesNameArrayList;
        this.anotherAllEffectedCountriesNameArrayList = new ArrayList<>(allEffectedCountriesNameArrayList);
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
        String countryName = allEffectedCountriesNameArrayList.get(position);
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
        if (allEffectedCountriesNameArrayList != null){
            totalItems = allEffectedCountriesNameArrayList.size();
        }
        return totalItems;
    }


    private class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //This method run on background thread
            ArrayList<String> arrayList = new ArrayList<>();
            String inputString = constraint.toString().toLowerCase().trim();
            if (inputString.length() == 0){
                arrayList.addAll(anotherAllEffectedCountriesNameArrayList);
            }else {
                for (String countryName : anotherAllEffectedCountriesNameArrayList) {
                    if (countryName.toLowerCase().contains(inputString)) {
                        arrayList.add(countryName);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = arrayList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            //This method run on main thread i.e.ui thread thread
            allEffectedCountriesNameArrayList.clear();
            allEffectedCountriesNameArrayList.addAll((ArrayList<String>)filterResults.values);
            notifyDataSetChanged();
        }
    }


    @Override
    public Filter getFilter() {
        return new MyFilter();
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
                    String countryName = allEffectedCountriesNameArrayList.get(getAdapterPosition());
                    int flagId = countryFlags.getCountryFlag(countryName);
                  onCardItemClickListener.onCardItemClicked(getAdapterPosition(),countryName,flagId);
                }
            });
        }
    }


}
