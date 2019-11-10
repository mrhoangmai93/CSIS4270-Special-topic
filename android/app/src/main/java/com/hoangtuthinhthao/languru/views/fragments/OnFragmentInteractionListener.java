package com.hoangtuthinhthao.languru.views.fragments;

        import android.net.Uri;
        import android.view.View;

public interface OnFragmentInteractionListener {
    void onRecyclerViewItemClick(View view, int position);
    void onBackButtonPressed();
    void onSpeakerClick(String word);
}
