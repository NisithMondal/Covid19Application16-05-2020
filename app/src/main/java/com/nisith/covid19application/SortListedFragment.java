package com.nisith.covid19application;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SortListedFragment extends Fragment {

    private OnCloseFragmentListener closeFragmentListener;
    private RecyclerView recyclerView;
    private FilterActivityRecyclerViewAdapter recyclerViewAdapter;
    private TextView appToolbarTextView;


    public SortListedFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SortListedFragment(FilterActivityRecyclerViewAdapter recyclerViewAdapter){
        this.recyclerViewAdapter = recyclerViewAdapter;
    }




    public interface OnCloseFragmentListener{
        void onCloseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_listed, container, false);
        setUpLayout(view);
        return view;
    }

    private void setUpLayout(View view){
        Toolbar appToolbar = view.findViewById(R.id.app_toolbar);
        appToolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        appToolbarTextView.setTextSize(14);
        appToolbar.setNavigationIcon(R.drawable.ic_cross);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragmentListener.onCloseFragment();
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRecyclerViewWithAdapter(recyclerViewAdapter);
    }

    private void setUpRecyclerViewWithAdapter(FilterActivityRecyclerViewAdapter recyclerViewAdapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    public void setTootbarTitle(String toolbarTitle){
        appToolbarTextView.setText(toolbarTitle);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.closeFragmentListener = (OnCloseFragmentListener) context;
    }
}
