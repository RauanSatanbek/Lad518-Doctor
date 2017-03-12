package com.example.rauansatanbek.lab518_task1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class New extends Fragment {
    final int NEW_ID = 1;
    ArrayList<Info> items_info;
    RecyclerView.Adapter adapter;
    ProgressBar progressBarNewFragment;
    JSONObject object;
    // не используется
        interface AddIntmToAdapter {
            void addItem(RecyclerView.Adapter adapter, JSONObject object, ArrayList<Info> items_info) throws JSONException;
        }
        AddIntmToAdapter addIntmTOAdapter;
    New(Activity activity) {
        addIntmTOAdapter = (AddIntmToAdapter) activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        RecyclerView recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerViewNew);
        progressBarNewFragment = (ProgressBar) view.findViewById(R.id.progressBarNewFragment);

        loadJSON();
        recyclerViewNew.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNew.setLayoutManager(mLayoutManager);
        SlideInRightAnimator animator = new SlideInRightAnimator();
        animator.setInterpolator(new OvershootInterpolator());
// or recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f));
        recyclerViewNew.setItemAnimator(animator);

        recyclerViewNew.getItemAnimator().setAddDuration(300);
        recyclerViewNew.getItemAnimator().setRemoveDuration(300);
        recyclerViewNew.getItemAnimator().setMoveDuration(500);
        recyclerViewNew.getItemAnimator().setChangeDuration(500);

        items_info = new ArrayList<>();
        adapter = new RecyclerViewAdapter(items_info, getActivity(), NEW_ID);
        recyclerViewNew.setAdapter(adapter);


        Log.d("MyLogs", "New created");
        return view;
    }
    //Remove item of adapter
        public Info remove(int position) {
            Info item = items_info.get(position);
            items_info.remove(position);
            adapter.notifyItemRemoved(position);
            return item;
        }


    void loadJSON() {
        progressBarNewFragment.setVisibility(View.VISIBLE);
        String url ="https://rauan-android-backend.herokuapp.com/api/info";
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest searchMsg= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("MyLogs", response.toString());
                Info info;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        info = new Info(obj);
                        int position = items_info.size();
                        items_info.add(position,info);
                    } catch (JSONException e) {
                        Log.d("MyLogs", "error = " + e.toString());
                        e.printStackTrace();
                    }
                }
                progressBarNewFragment.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MyLogs", "Error: " + error.getMessage());
            }
        });
        queue.add(searchMsg);
    }
}
