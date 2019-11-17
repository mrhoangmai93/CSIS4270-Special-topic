package com.hoangtuthinhthao.languru.controllers.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.loadServices.game.LoadGame;
import com.hoangtuthinhthao.languru.models.game.LoadGameCallback;
import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.views.activities.MainActivity;

import java.util.ArrayList;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        final long when = System.currentTimeMillis();
        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        LoadGame gameLoader = new LoadGame(context);
        gameLoader.byNumberWord(1, new LoadGameCallback() {
            @Override
            public void successLoadGame(int numberOfWord, ArrayList<Lesson> lessons) {
                NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_game)
                        .setContentTitle("Word of Today")
                        .setContentText(lessons.get(0).getWord())
                        .setAutoCancel(true).setWhen(when)
                        .setContentIntent(pendingIntent)
//              .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                        ;
                notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), mNotifyBuilder.build());
            }

            @Override
            public void failLoadGame(String message) {

            }
        });



    }

}
