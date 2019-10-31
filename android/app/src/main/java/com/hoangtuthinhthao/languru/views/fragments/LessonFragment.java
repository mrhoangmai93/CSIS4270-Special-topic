package com.hoangtuthinhthao.languru.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.adapters.ItemClickListener;
import com.hoangtuthinhthao.languru.controllers.adapters.RecyclerTouchListener;
import com.hoangtuthinhthao.languru.controllers.adapters.SingleImageRVAdapter;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LessonFragment} interface
 * to handle interaction events.
 * Use the {@link LessonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String topicName;
    private ArrayList<Lesson> lessonList;

    private OnFragmentInteractionListener mListener;

    private RecyclerView rv;
    private TextView txtTopicName, word, wordDescription;

    static CarouselLayoutManager layoutManager;

    public LessonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonFragment.
     */
    public static LessonFragment newInstance(String param1, ArrayList<Lesson> param2) {
        LessonFragment fragment = new LessonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topicName = getArguments().getString(ARG_PARAM1);
            lessonList = (ArrayList<Lesson>) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Find Views
        rv = view.findViewById(R.id.topicRecyclerView);
        txtTopicName = view.findViewById(R.id.topicName);
        word = view.findViewById(R.id.lessonName);
        wordDescription = view.findViewById(R.id.lessonDescription);

        txtTopicName.setText(topicName);

        SingleImageRVAdapter adapter = new SingleImageRVAdapter(getContext(), lessonList, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rv.setHasFixedSize(true);
        layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);

        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    int centerPosition = layoutManager.getCenterItemPosition();
                    displayInfo(centerPosition);
                }
            }
        });
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                rv, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();
                Log.i("position",String.valueOf(position ));
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

        //display information of first word
        displayInfo(0);

        //back button pressed
        view.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackButtonPressed();
            }
        });
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

    /**
     * This function display all information of center image
     * @param position
     */
    private void displayInfo(int position) {
        word.setText(lessonList.get(position).getWord());
        wordDescription.setText(lessonList.get(position).getDescription());
    }

}
