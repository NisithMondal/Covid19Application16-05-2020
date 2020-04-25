package com.nisith.covid19application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nisith.covid19application.model.EffectedStateInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllStatesRecyclerViewAdapter extends RecyclerView.Adapter<AllStatesRecyclerViewAdapter.MyViewHoldar> {

    private ArrayList<EffectedStateInfo> allEffectedStateInfoArrayList;
    private OnStateCardItemClickInterface onStateCardItemClickInterface;


    public interface OnStateCardItemClickInterface {
        void onStateCardItemClick(int position);
    }

    public AllStatesRecyclerViewAdapter(ArrayList<EffectedStateInfo> allEffectedStateInfoArrayList, OnStateCardItemClickInterface onStateCardItemClickInterface){
        this.allEffectedStateInfoArrayList = allEffectedStateInfoArrayList;
        this.onStateCardItemClickInterface = onStateCardItemClickInterface;
    }

    @NonNull
    @Override
    public MyViewHoldar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_appearence_for_states,parent,false);
        return new MyViewHoldar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldar holder, int position) {
        EffectedStateInfo effectedStateInfo = allEffectedStateInfoArrayList.get(position);
        holder.stateName.setText(effectedStateInfo.getStateName());
        holder.totalCases.setText(effectedStateInfo.getConfirmedCases());
        holder.totalDeaths.setText(effectedStateInfo.getTotalDeaths());
        setImageThumbnailInRow(effectedStateInfo.getStateName().substring(0,1),holder.thumbnailTextView);
    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (allEffectedStateInfoArrayList != null){
            totalItems = allEffectedStateInfoArrayList.size();
        }
        return totalItems;
    }

    class MyViewHoldar extends RecyclerView.ViewHolder{

        TextView thumbnailTextView;
        TextView stateName, totalCases, totalDeaths;

        public MyViewHoldar(@NonNull View itemView) {
            super(itemView);
            thumbnailTextView = itemView.findViewById(R.id.thumbnail_text_view);
            stateName = itemView.findViewById(R.id.country_name_text_view);
            totalCases = itemView.findViewById(R.id.total_cases_text_view);
            totalDeaths = itemView.findViewById(R.id.total_deaths_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStateCardItemClickInterface.onStateCardItemClick(getAdapterPosition());
                }
            });
        }
    }


    private void setImageThumbnailInRow(String firstLetter,TextView thumbnailTextView){
        if (firstLetter.equalsIgnoreCase("A") || firstLetter.equalsIgnoreCase("F") || firstLetter.equalsIgnoreCase("K")
                || firstLetter.equalsIgnoreCase("Q") || firstLetter.equalsIgnoreCase("W")){

            thumbnailTextView.setBackgroundResource(R.drawable.ic_disk3);
            thumbnailTextView.setText(firstLetter);

        }else if (firstLetter.equalsIgnoreCase("B") || firstLetter.equalsIgnoreCase("G") || firstLetter.equalsIgnoreCase("L")
                || firstLetter.equalsIgnoreCase("R") || firstLetter.equalsIgnoreCase("X")){
            thumbnailTextView.setBackgroundResource(R.drawable.ic_disk1);
            thumbnailTextView.setText(firstLetter);

        }else if(firstLetter.equalsIgnoreCase("C") || firstLetter.equalsIgnoreCase("H") || firstLetter.equalsIgnoreCase("M")
                || firstLetter.equalsIgnoreCase("S") || firstLetter.equalsIgnoreCase("Y")){
            thumbnailTextView.setBackgroundResource(R.drawable.ic_disk2);
            thumbnailTextView.setText(firstLetter);

        }else if (firstLetter.equalsIgnoreCase("D") || firstLetter.equalsIgnoreCase("I") || firstLetter.equalsIgnoreCase("N")
                || firstLetter.equalsIgnoreCase("T") || firstLetter.equalsIgnoreCase("Z")){
            thumbnailTextView.setBackgroundResource(R.drawable.ic_disk4);
            thumbnailTextView.setText(firstLetter);

        }else if (firstLetter.equalsIgnoreCase("E") || firstLetter.equalsIgnoreCase("J") || firstLetter.equalsIgnoreCase("O")
                || firstLetter.equalsIgnoreCase("U") ){
            thumbnailTextView.setBackgroundResource(R.drawable.ic_disk5);
            thumbnailTextView.setText(firstLetter);

        }else if (firstLetter.equalsIgnoreCase("P") || firstLetter.equalsIgnoreCase("V")){
            thumbnailTextView.setBackgroundResource(R.drawable.ic_disk6);
            thumbnailTextView.setText(firstLetter);
        }
    }


}
