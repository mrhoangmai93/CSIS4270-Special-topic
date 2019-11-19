package com.hoangtuthinhthao.languru.views.fragments.games;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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

import com.github.nkzawa.emitter.Emitter;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.adapters.ItemClickListener;
import com.hoangtuthinhthao.languru.controllers.game.CreateGameBoard;
import com.hoangtuthinhthao.languru.controllers.game.GameCalculation;
import com.hoangtuthinhthao.languru.controllers.game.GameGridView;
import com.hoangtuthinhthao.languru.controllers.timer.TimerControl;
import com.hoangtuthinhthao.languru.models.game.Game;
import com.hoangtuthinhthao.languru.models.io.IoResponse;
import com.hoangtuthinhthao.languru.models.io.Payload;
import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.views.activities.GameActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.hoangtuthinhthao.languru.views.activities.GameActivity.gamePayload;
import static com.hoangtuthinhthao.languru.views.activities.GameActivity.mSocket;
import static com.hoangtuthinhthao.languru.views.activities.GameActivity.timerControl;

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
    private static final String ARG_PARAM3 = "game_payload";
    private static final String ARG_PARAM4 = "game_Type";

    private int numberOfWord;
    private ArrayList<Lesson> wordList;
    private String gameType;
    private GameInteraction mListener;

    //timer Textview
    private TextView timerTextView, txtLevel, gameStatus, oppName, oppLevel, oppStatus;
    private Button btnTimer;

    private ImageView backBtn;

    //Game item click listener
    ItemClickListener itemClickListener;

    private long remainTime = 60000;

    private CreateGameBoard gb;
    private Game game;
    private int row;
    private int column;
    private int level = 1;
    private  boolean isGameRunning = false;
    // io listener
    private Emitter.Listener ioListener;

    //Progress dialog
    private ProgressDialog progressDialog;

    private ConstraintLayout opponentLayout;


    //timer callBack
    TimerControl.TimerCallback timerCallback = new TimerControl.TimerCallback() {
        @Override
        public void onTimeOver() {
            btnTimer.setVisibility(View.INVISIBLE);
            //stop the game
            isGameRunning = false;
        }
    };

    public GamePracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param gameType Game Type.
     * @param gameLevel Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamePracticeFragment.
     */
    public static GamePracticeFragment newInstance(String gameType, int gameLevel, ArrayList<Lesson> param2) {
        GamePracticeFragment fragment = new GamePracticeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, gameLevel);
        args.putSerializable(ARG_PARAM2, param2);
        args.putString(ARG_PARAM4, gameType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            numberOfWord = getArguments().getInt(ARG_PARAM1, 6);
            wordList = (ArrayList<Lesson>) getArguments().getSerializable(ARG_PARAM2);
            gameType = getArguments().getString(ARG_PARAM4);
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
        progressDialog = new ProgressDialog(getContext());
        opponentLayout = view.findViewById(R.id.opponentContent);
        timerTextView = view.findViewById(R.id.timerTextView);
        btnTimer = view.findViewById(R.id.btnIimer);
        txtLevel = view.findViewById(R.id.txtLevel);
        backBtn = view.findViewById(R.id.btnBack);
        gameStatus = view.findViewById(R.id.gameStatus);
        txtLevel.setText("Level: " + level);
        oppName = view.findViewById(R.id.txtOpponentName);
        oppLevel = view.findViewById(R.id.txtOppLevel);
        oppStatus = view.findViewById(R.id.txtOppStatus);
        //timer
//        timerControl = new TimerControl(getContext(), timerTextView );
        timerControl.setTimerTextView(timerTextView);
        timerControl.setTimerCallback(timerCallback);

        if(gameType.equals("practice")) {
            setGameStatus("Please press start!");
            btnTimer.setText("start");
            opponentLayout.setVisibility(View.GONE);
        } else {
            if(numberOfWord == 6) {
                gameStatus.setText("Please press Ready!");
                btnTimer.setText("Ready");
            } else {
                startGame();
            }
            //set opponent data
            oppName.setText(gamePayload.getName());
            oppLevel.setText("Level: " + gamePayload.getCurrentLevel());

        }

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnTimer.getText().equals("pause")) {

                    pauseGame();
                } else if (btnTimer.getText().equals("start")){
                    startGame();
                } else if (btnTimer.getText().equals("Ready")){
                    readyGame();
                }
            }
        });
        //set the timer text
        timerControl.setTextForTimer(remainTime);


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

                mListener.onGameComplete(numberOfWord);
                if(gameType.equals("practice")) {
                    timerControl.cancelTimer();
                } else {
                    //timerControl.startChallengeTimer();
                    mSocket.emit("player.levelUp");
                    if(numberOfWord == 15 ){
                        challengeGameOver("You win!");
                    }
                }
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

        //initialize io
        ioListener  = new Emitter.Listener() {

            @Override
            public void call(final Object... args) {
                handleCall(args);
            }
        };
        //add socket listen on event
        mSocket.on("action" ,ioListener);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GameInteraction) {
            mListener = (GameInteraction) context;
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
        gameStatus.setVisibility(View.GONE);
        btnTimer.setVisibility(View.VISIBLE);
        btnTimer.setText("pause");
        isGameRunning = true;

        if(gameType.equals("practice")) {
            //startCountDownTimer();
            timerControl.startCountDownTimer();
        } else {
            timerControl.startChallengeTimer();
            mSocket.emit("player.resume");
        }

    }

    /**
     * Pause Game
     */
    private void pauseGame() {

        setGameStatus("Game Pause");
        btnTimer.setText("start");
        if(gameType.equals("practice")) {
            //cancelTimer();
            timerControl.cancelTimer();
        } else {
            timerControl.stopChallengeTimer();
            mSocket.emit("player.pause");
        }

    }


    /**
     * set Game status
     * @param message status message
     */
    private void setGameStatus(String message) {
        gameStatus.setVisibility(View.VISIBLE);
        gameStatus.setText(message);
        isGameRunning = false;
    }

    /**
     * game ready
     */
    private void readyGame() {
        mSocket.emit("player.ready");
        btnTimer.setVisibility(View.INVISIBLE);
        progressDialog.setMessage("Waiting for other player to ready!");
        progressDialog.show();
    }

    // handle responses from socket
    private void handleCall(final Object... args) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject) args[0];
                Log.i("datafrag", data.toString());
                String type;
                try {
                    type = data.getString("type");

                    switch (type) {
                        case "SERVER_OPPONENT_READY" :
                            break;
                        case "SERVER_START_GAME":
                            progressDialog.dismiss();
                            oppStatus.setText("Playing");
                            startGame();
                            break;
                        case "SERVER_PAUSE_GAME":
                            oppStatus.setText("Pause");
                            pauseGame();
                            break;
                        case "SERVER_OPPONENT_LEVEL_UP":
                            opponentLevelUp();

                            break;
                        case "SERVER_OPPONENT_FINISH_GAME":
                            oppStatus.setText("Opponent Win");
                            challengeGameOver("Game Over! \n Opponent Win");
                            break;
                        case "SERVER_PLAYER_WIN":
                            challengeGameOver("Game Over! \n You Win");
                            break;

                    }

                } catch (JSONException e) {
                    return;
                }

            }
        });
    }

    private void opponentLevelUp() {
        gamePayload.setCurrentLevel(gamePayload.getCurrentLevel() + 1);
        oppLevel.setText("Level: " + gamePayload.getCurrentLevel());
    }

    private void challengeGameOver(String message) {
        setGameStatus(message);
        timerControl.stopChallengeTimer();
        btnTimer.setEnabled(false);
    }

}
