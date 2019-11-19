package com.hoangtuthinhthao.languru.views.fragments.games;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hoangtuthinhthao.languru.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChalengeGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChalengeGameFragment extends Fragment {

    private OnGameCenterInteraction mListener;
    private Button btnJoin, btnCreate, backBtn;
    private EditText joinCode, createCode;
    public ChalengeGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ChalengeGameFragment.
     */
    public static ChalengeGameFragment newInstance() {
        ChalengeGameFragment fragment = new ChalengeGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String code="";
        btnJoin = view.findViewById(R.id.btnJoinGame);
        btnCreate = view.findViewById(R.id.btnCreateGame);
        joinCode = view.findViewById(R.id.inputJoinRoomCode);
        createCode = view.findViewById(R.id.inputCreateRoomCode);
        backBtn = view.findViewById(R.id.btnBack);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChalengeGameInteraction("join", joinCode.getText().toString());
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChalengeGameInteraction("create", createCode.getText().toString());
            }
        });

        //back button pressed
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackButtonPressed();
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



}
