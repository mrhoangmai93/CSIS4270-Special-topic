package com.hoangtuthinhthao.languru.views.fragments.games;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.game.CreateGameBoard;
import com.hoangtuthinhthao.languru.controllers.helpers.GameCardAnimation;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GamePracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamePracticeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int numberOfWord;
    private ArrayList<Lesson> wordList;

    private OnGameCenterInteraction mListener;

    private long startTime = 0;
    //timer Textview
    private TextView timerTextView;
    private Button btnTimer;

    Handler handler = new Handler();

    long remainTime = 120000;
    //Declare timer
    CountDownTimer cTimer = null;

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(remainTime, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                timerTextView.setText(String.format("%d:%02d", minutes, seconds));
                remainTime = millisUntilFinished;
            }
            public void onFinish() {
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    public GamePracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamePracticeFragment.
     */
    public static GamePracticeFragment newInstance(int param1, ArrayList<Lesson> param2) {
        GamePracticeFragment fragment = new GamePracticeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberOfWord = getArguments().getInt(ARG_PARAM1, 6);
            wordList = (ArrayList<Lesson>) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_practice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timerTextView = view.findViewById(R.id.timerTextView);
        btnTimer = view.findViewById(R.id.btnIimer);

        btnTimer.setText("start");
        btnTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    cancelTimer();
                    b.setText("start");
                } else {
                    startTimer();
                    b.setText("stop");
                }
            }
        });

        final ImageView img = view.findViewById(R.id.img);
                img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameCardAnimation.flip(img, R.drawable.ic_game);
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        GameCardAnimation.flip(img, R.drawable.ic_email);
                    }

                }, 1500);
            }
        });
        CreateGameBoard gb = new CreateGameBoard();
        gb.createGame(getActivity(), wordList, 1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameCenterInteraction) {
            mListener = (OnGameCenterInteraction) context;
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
