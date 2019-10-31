package com.hoangtuthinhthao.languru.controllers.adapters;

import android.view.View;

/**
 * This interface for item click in dapter
 */
public interface ItemClickListener {
    /**
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     *
     * @param view
     * @param position
     */
    void onItemLongClick(View view, int position);
}
