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
import android.widget.Toast;

import java.util.Calendar;

import androidx.work.Worker;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by drsha on 19/8/2018.
 */

public class NotificationWorker extends Worker {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    JourneyTime journeyTime;

    @NonNull
    @Override
    public Result doWork() {
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
        journeyTime = new JourneyTime(getApplicationContext());

        if(IsShowAlertToCompleteCurrentStage(getApplicationContext(),sharedPreferences))
        {

            Toast.makeText(getApplicationContext(),"Please compleete"+sharedPreferences.getString("journey_point","xxx"),Toast.LENGTH_LONG).show();


        }
        return Result.SUCCESS;
    }

//    public void showNotification(String Notification, Context context, Activity activity) {
//        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, activity.class), 0);
//        Resources r = getResources();
//        Notification notification = new NotificationCompat.Builder(context)
//                .setTicker("some shit")
//                .setSmallIcon(android.R.drawable.ic_menu_report_image)
//                .setContentTitle("MYOP NOTIFICATION")
//                .setContentText(Notification)
//                .setContentIntent(pi)
//                .setAutoCancel(true)
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notification);
//    }


    boolean IsShowAlertToCompleteCurrentStage(Context context, SharedPreferences sharedPreferences)
    {
        sharedPreferences = context.getSharedPreferences("mypref", 0);
        boolean res;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (sharedPreferences.getString("journey_point","xxx") )
        {

            case "preop_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.PREOP_START) && Calendar.getInstance().getTime().before(journeyTime.PREOP_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert_msg_from_pt_to_server;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed Preop stage!");
                    res = false;
                }
                return res;

            case "preop_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;

            case "preop-drug_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.PREOP_START) && Calendar.getInstance().getTime().before(journeyTime.PREOP_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed Preop upload drug photo stage!");
                    res = false;
                }
                return res;

            case "preop-drug_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;

            case "reminder1_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.REMINDER1_START) && Calendar.getInstance().getTime().before(journeyTime.REMINDER1_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed reminder1 stage!");
                    res = false;
                }
                return res;


            case "reminder1_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;


            case "reminder2_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.REMINDER2_START) && Calendar.getInstance().getTime().before(journeyTime.REMINDER2_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed reminder2 stage!");
                    res = false;
                }
                return res;



            case "reminder2_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;




            case "op_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;





            case "pod1_incomplete":

                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.POST_OP1_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP1_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed pod1 stage!");
                    res = false;
                }
                return res;


            case "pod1_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;




            case "pod2_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.POST_OP2_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP2_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed pod2 stage!");
                    res = false;
                }
                return res;

            case "pod2_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;




            case "pod3_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.POST_OP3_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP3_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed pod3 stage!");
                    res = false;
                }
                return res;

            case "pod3_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;




            case "pod5_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.POST_OP5_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP5_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed pod5 stage!");
                    res = false;
                }
                return res;



            case "pod5_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;




            case "pod10_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.POST_OP10_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP10_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed pod10 stage!");
                    res = false;
                }
                return res;


            case "pod10_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;


            case "pod15_incomplete":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.POST_OP15_START) && Calendar.getInstance().getTime().before(journeyTime.POST_OP15_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Missed pod15 stage!");
                    res = false;
                }
                return res;


            case "pod15_complete":
                //set "complete the stage alert" boolean to false;
                editor.putBoolean("IsAlertFromPtToServ",false);
                return false;


            default:
                return false;




        }
    }

}
