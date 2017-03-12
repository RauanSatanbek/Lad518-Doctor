package com.example.rauansatanbek.lab518_task1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by Rauan Satanbek on 08.03.2017.
 */

public class Confirmed  extends Fragment {
    final int CONFIRMED_ID = 3;
    ArrayList<Info> info;
    RecyclerView.Adapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MyLogs", "Confirmed created");
        View view = inflater.inflate(R.layout.fragment_confirmed, container, false);
        RecyclerView recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerViewConfirmed);

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

        info = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(loadJSONFromAsset("Info"));
            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Log.d("MyLogs", "#1 - " + object.toString());
                Info info1 = new Info(object);
                info.add(info1);
            }
        } catch (JSONException e) {
            Log.d("MyLogs", "Error: " + e);
            e.printStackTrace();
        }
        adapter = new RecyclerViewAdapter(info, getActivity(), CONFIRMED_ID);
        recyclerViewNew.setAdapter(adapter);
        return view;
    }

    //loadJSONFromAsset
    public String loadJSONFromAsset(String name) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(name + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}