package spero.shariq.com.myopmvp10;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import androidx.work.Worker;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by drsha on 19/8/2018.
 */

public class NotificationWorker extends Worker {

    SharedPreferences sharedPreferences;
    JourneyTime journeyTime;

    @NonNull
    @Override
    public Result doWork() {
        ShowNotificationforJp();
        return Result.SUCCESS;
    }



    void launchNotif()
    {
        getApplicationContext().startActivity(new Intent(getApplicationContext(), CustomDialogActivity.class));

    }
    private void ShowNotificationforJp() {
        //start showing notif at start and  stop at deadline
        Log.d("worker executed","notif on "+Thread.currentThread().getId());
        getApplicationContext().startActivity(new Intent(getApplicationContext(), CustomDialogActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        switch (sharedPreferences.getString("journey_point", "xxx")) {

            case "preop_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.PREOP_START) && Calendar.getInstance().getTime().before(journeyTime.PREOP_DEADLINE)) {
                    showNotification();
                }

            case "preop_GotoClinic":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.PREOP_START) && Calendar.getInstance().getTime().before(journeyTime.PREOP_DEADLINE)) {
                    showNotification();
                }


            case "preop_drug_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.PREOP_START) && Calendar.getInstance().getTime().before(journeyTime.PREOP_DEADLINE)) {
                    showNotification();
                }


            case "reminder1_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.REMINDER1_START) && Calendar.getInstance().getTime().before(journeyTime.REMINDER1_DEADLINE)) {
                    showNotification();
                }


            case "reminder2_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.REMINDER2_START) && Calendar.getInstance().getTime().before(journeyTime.REMINDER2_DEADLINE)) {
                    showNotification();
                }



            case "pod1_incomplete":

                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.POST_OP1_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP1_DEADLINE)) {
                    showNotification();
                }


            case "pod2_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.POST_OP2_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP2_DEADLINE)) {
                    showNotification();
                }


            case "pod3_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.POST_OP3_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP3_DEADLINE)) {
                    showNotification();
                }



            case "pod5_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.POST_OP5_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP5_DEADLINE)) {
                    showNotification();
                }



            case "pod10_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.POST_OP10_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP10_DEADLINE)) {
                    showNotification();
                }


            case "pod15_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if (Calendar.getInstance().getTime().after(journeyTime.POST_OP15_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP15_DEADLINE)) {
                    showNotification();
                }



        }
    }
    private void showNotification()
    {
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), TiledDashboard.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"personal");
        builder.setSmallIcon(android.R.drawable.ic_menu_report_image);
        builder.setContentTitle("IMPORTANT TASK FROM MYOP!");
        builder.setContentText("Please click here complete stage now!");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentIntent(pi);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(001,builder.build());

    }

}
