package com.android.vismay.newsforce.Home.TopicWiseFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.vismay.newsforce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryNewsFragment extends Fragment {


    public CountryNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_news, container, false);
    }

}