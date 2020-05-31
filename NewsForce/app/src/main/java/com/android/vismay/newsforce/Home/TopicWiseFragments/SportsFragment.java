package com.android.vismay.newsforce.Home.TopicWiseFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.vismay.newsforce.MainActivity;
import com.android.vismay.newsforce.R;
import com.android.vismay.newsforce.Utilities.ApiClient;
import com.android.vismay.newsforce.Utilities.ApiInterface;
import com.android.vismay.newsforce.Utilities.NewsModel;
import com.android.vismay.newsforce.Utilities.NewsRecyclerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewsModel model;
    ApiInterface apiInterface;
    NewsRecyclerAdapter adapter;
    public SportsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sports, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView_sports_frag);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        String url="https://newsapi.org/v2/";
        fetchInformation(url,"en","5c16d55fbe864cafa673f4937c31bc87");
        return rootView;
    }

    private void fetchInformation(String url, String en, String s) {
        apiInterface= ApiClient.getApiClient(url).create(ApiInterface.class);
        Call<NewsModel> call=apiInterface.getSportsNews("sports","en","5c16d55fbe864cafa673f4937c31bc87");
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                model=response.body();

                for(int i=0;i<model.getArticles().size();i++)
                {
                    if(model.getArticles().get(i).getContent()==null)
                    {
                        model.getArticles().remove(i);
                        i--;
                    }
                }

                adapter=new NewsRecyclerAdapter(model, getActivity());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

}
