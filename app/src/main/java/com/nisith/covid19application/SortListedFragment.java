package com.nisith.covid19application;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.Objects;


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
        setHasOptionsMenu(true);
        setUpLayout(view);
        return view;
    }

    private void setUpLayout(View view){
        Toolbar appToolbar = view.findViewById(R.id.app_toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(appToolbar);
        appToolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        appToolbarTextView.setTextSize(15);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.setting_activity_menu,menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Enter Country Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Filter filter = recyclerViewAdapter.getFilter();
                filter.filter(searchView.getQuery());
                return true;
            }
        });

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


    public void setToolbarTitle(String toolbarTitle){
        appToolbarTextView.setText(toolbarTitle);
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.closeFragmentListener = (OnCloseFragmentListener) context;
    }
}
