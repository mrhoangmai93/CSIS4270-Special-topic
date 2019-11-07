package com.hoangtuthinhthao.languru.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.adapters.ItemClickListener;
import com.hoangtuthinhthao.languru.controllers.adapters.TopicRVAdapter;
import com.hoangtuthinhthao.languru.models.responses.Progress;
import com.hoangtuthinhthao.languru.views.activities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Progress> topicList;

    private OnFragmentInteractionListener mListener;

    public TopicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param topicList Array list lessons
     * @return A new instance of fragment TopicFragment.
     */
    public static TopicFragment newInstance(ArrayList<Progress> topicList) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, topicList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topicList = (ArrayList<Progress>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.topicRecyclerView);
        TopicRVAdapter adapter = new TopicRVAdapter(topicList, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onRecyclerViewItemClick(view, position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
