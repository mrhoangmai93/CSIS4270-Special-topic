package com.hoangtuthinhthao.languru.controllers.timer;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TimerControl {

    public static Handler timerHandler = new Handler();
    //Declare timer
    public static CountDownTimer cTimer = null;
    private TextView timerTextView;
    private Context context;
    private long remainTime = 60000;
    private long startTime = 0;

    private TimerCallback timerCallback;
    private static long totalTime;

    public TimerControl(Context context, TextView timerTextView) {
        this.context = context;
        this.timerTextView = timerTextView;
    }

    public TimerControl(Context context) {
        this.context = context;
        totalTime = 0;
    }

    public TextView getTimerTextView() {
        return timerTextView;
    }

    public void setTimerTextView(TextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    public long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(long remainTime) {
        this.remainTime = remainTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTimerCallback(TimerCallback timerCallback) {
        this.timerCallback = timerCallback;
    }

    /**
     *     start timer function
     */
    public void startCountDownTimer() {
        cTimer = new CountDownTimer(remainTime, 1000) {
            public void onTick(long millisUntilFinished) {
                setTextForTimer(millisUntilFinished);
                remainTime = millisUntilFinished;
            }
            public void onFinish() {

                Toast.makeText(context, "Time is Over!", Toast.LENGTH_SHORT).show();
                timerCallback.onTimeOver();

            }
        };
        cTimer.start();
    }
    /**
     *  Cancel timer
     */
    public void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
    /**
     * Set the time to the text view
     * @param timeMillisecond the remain time
     */
    public void setTextForTimer(long timeMillisecond) {
        int seconds = (int) (timeMillisecond / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        timerTextView.setText(String.format("%d:%02d", minutes, seconds));
    }

    //timer Runnable
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime + totalTime;

            setTextForTimer(millis);

            timerHandler.postDelayed(this, 500);
        }
    };
    /**
     *     start timer function
     */
    public void startChallengeTimer() {
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    /**
     *      stop challenge timer
     */
    public void stopChallengeTimer() {
        totalTime = System.currentTimeMillis() - startTime + totalTime;
        timerHandler.removeCallbacks(timerRunnable);
    }

    public interface TimerCallback {
        void onTimeOver();
    }
}
