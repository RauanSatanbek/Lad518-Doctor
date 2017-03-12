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

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by Rauan Satanbek on 08.03.2017.
 */

public class Accepted extends Fragment {
    final int ACCEPTED_ID = 2;
    ArrayList<Info> info;
    RecyclerView.Adapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MyLogs", "Accepted created");
        View view = inflater.inflate(R.layout.fragment_accepted, container, false);
        RecyclerView recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerViewAccepted);

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

        adapter = new RecyclerViewAdapter(info, getActivity(), ACCEPTED_ID);
        recyclerViewNew.setAdapter(adapter);
        return view;
    }


        public void add(Info item) {
            info.add(item);
            adapter.notifyItemInserted(info.size());
        }
//    public infoTest remove(int position) {
//        infoTest item = Info.get(position);
//        Info.remove(position);
//        adapter.notifyItemRemoved(position);
//        return item;
//    }
}