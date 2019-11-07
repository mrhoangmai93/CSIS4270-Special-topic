package com.hoangtuthinhthao.languru.views.fragments.games;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.adapters.ItemClickListener;
import com.hoangtuthinhthao.languru.controllers.game.CreateGameBoard;
import com.hoangtuthinhthao.languru.controllers.game.GameCalculation;
import com.hoangtuthinhthao.languru.controllers.game.GameGridView;
import com.hoangtuthinhthao.languru.controllers.helpers.GameCardAnimation;
import com.hoangtuthinhthao.languru.models.game.Game;
import com.hoangtuthinhthao.languru.models.game.GameCell;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private TextView timerTextView, txtLevel;
    private Button btnTimer;

    private ImageView backBtn;

    //Game item click listener
    ItemClickListener itemClickListener;

    Handler handler = new Handler();

    long remainTime = 60000;
    //Declare timer
    CountDownTimer cTimer = null;

    private CreateGameBoard gb;
    private Game game;
    private int row;
    private int column;
    private int level = 1;
    private  boolean isGameRunning = false;

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
            //shuffle the wod list

            switch (numberOfWord) {
                case 10 :
                    row = 4;
                    column = 5;
                    level = 2;
                    remainTime = 120000;
                    break;
                case 15:
                    row = 5;
                    column = 6;
                    level = 3;
                    remainTime = 240000;
                    break;
                    default:
                        row = 3;
                        column = 4;
                        level = 1;
                        remainTime = 60000;
                        break;
            }
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
        txtLevel = view.findViewById(R.id.txtLevel);
        backBtn = view.findViewById(R.id.btnBack);
        txtLevel.setText("Level: " + level);

        btnTimer.setText("start");
        btnTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("pause")) {
                    cancelTimer();
                    b.setText("start");
                    isGameRunning = false;
                } else {
                    startTimer();
                    b.setText("pause");
                    isGameRunning = true;
                }
            }
        });
        //set the timer text
        setTextForTimer(remainTime);


        GameGridView mGridView = view.findViewById(R.id.gameBoardGrid);
        //Create Game object
        game = new Game(getContext(), row, column, wordList);
        final GameCalculation gameCalculation = new GameCalculation(getContext(), game);

        // Initialize item Click of cell
        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(isGameRunning)
                    gameCalculation.setCellClick(view, position);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        };
        //initial game complete call back
        GameCalculation.GameCompleteCallback completeCallback = new GameCalculation.GameCompleteCallback() {
            @Override
            public void onGameComplete() {
                Toast.makeText(getContext(), "Level Complete", Toast.LENGTH_SHORT).show();
                cancelTimer();
                mListener.onGameComplete(numberOfWord);
            }
        };
        gameCalculation.setCallback(completeCallback);

        //Create Game
        gb = new CreateGameBoard(getContext(), mGridView, itemClickListener);

        gb.createGame( game, row, column);

        //back button pressed
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Quit Game")
                        .setMessage("Would you like to quit this game?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListener.onBackButtonPressed();
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();
            }
        });
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

    /**
     * Start Game Function
     */
    private void startGame() {

    }

    /**
     *     start timer function
     */
    void startTimer() {
        cTimer = new CountDownTimer(remainTime, 1000) {
            public void onTick(long millisUntilFinished) {
                setTextForTimer(millisUntilFinished);
                remainTime = millisUntilFinished;
            }
            public void onFinish() {
                //stop the game
                isGameRunning = false;
                Toast.makeText(getContext(), "Time is Over!", Toast.LENGTH_SHORT).show();
                btnTimer.setVisibility(View.INVISIBLE);
            }
        };
        cTimer.start();
    }

    /**
     * Set the time to the text view
     * @param millisUntilFinished the remain time
     */
    private void setTextForTimer(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        timerTextView.setText(String.format("%d:%02d", minutes, seconds));
    }

    /**
     *  Cancel timer
     */
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}
