package com.android.vismay.newsforce.Home.TopSourcesFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.vismay.newsforce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoxNewsFragment extends Fragment {


    public FoxNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fox_news, container, false);
    }

}
