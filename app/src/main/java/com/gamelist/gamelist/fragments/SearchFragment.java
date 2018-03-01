package com.gamelist.gamelist.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gamelist.gamelist.R;
import com.gamelist.gamelist.activities.DetailActivity;
import com.gamelist.gamelist.activities.MainActivity;
import com.gamelist.gamelist.adapter.SearchItemAdapter;
import com.gamelist.gamelist.models.SearchItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//  implements SearchItemAdapter.OnItemClickListener
// insertmethod ( implement method from warning
public class SearchFragment extends Fragment implements SearchItemAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private SearchItemAdapter mExampleAdapter;
    private ArrayList<SearchItem> mExampleList;
    private RequestQueue mRequestQueue;

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);


        EditText editText = rootView.findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();


//        SearchView searchView = rootView.findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Toast.makeText(getActivity(),"Our word : "+s,Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });






        return rootView;
    }

    private void filter(String text) {
        ArrayList<SearchItem> filteredList = new ArrayList<>();

        for (SearchItem item : mExampleList) {
            if (item.getCreator().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mExampleAdapter.filterList(filteredList);
    }

    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("user");
                                String imageUrl = hit.getString("webformatURL");
                                int likeCount = hit.getInt("likes");

                                mExampleList.add(new SearchItem(imageUrl, creatorName, likeCount));
                            }
                            // getActivity() to get context inside fragment
                           mExampleAdapter = new SearchItemAdapter(getActivity(), mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                            /****/

                            mExampleAdapter.setOnItemClickListener(SearchFragment.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    // When we click on item -- Intent starts Detail Activity with the values we pass
    @Override
    public void onItemClick(int position) {
// getActivity() to get context inside fragment
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        SearchItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);

    }
}