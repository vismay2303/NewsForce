package com.android.vismay.newsforce.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.vismay.newsforce.MainActivity;
import com.android.vismay.newsforce.R;
import com.android.vismay.newsforce.Utilities.ApiClient;
import com.android.vismay.newsforce.Utilities.ApiInterface;
import com.android.vismay.newsforce.Utilities.NewsModel;
import com.android.vismay.newsforce.Utilities.NewsRecyclerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewsModel model;
    ApiInterface apiInterface;
    NewsRecyclerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView_home_frag);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout=rootView.findViewById(R.id.swiperefreshlayout_home);
        ((HomeActivity)getActivity()).setFragmentRefreshListener(new HomeActivity.FragmentRefreshListener(){
            @Override
            public void onRefresh(String s) {
                UpdateUI(s);
            }
        });
        return rootView;
    }
    public void UpdateUI(String command){
        String url="https://newsapi.org/v2/";
        fetchInformation(url,"en",getResources().getString(R.string.apikey),command);
    }
    private void fetchInformation(String url, String en, String s,String command) {
        apiInterface= ApiClient.getApiClient(url).create(ApiInterface.class);
        Call<NewsModel> call=apiInterface.getSearchWiseNews(command,"en","5c16d55fbe864cafa673f4937c31bc87");
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