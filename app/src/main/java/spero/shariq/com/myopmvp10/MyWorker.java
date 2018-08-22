package spero.shariq.com.myopmvp10;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import androidx.work.Worker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by drsha on 18/8/2018.
 */

public class MyWorker extends Worker{

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    JourneyTime journeyTime;

    @NonNull
    @Override
    public Result doWork() {
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        journeyTime = new JourneyTime(getApplicationContext());

        Call<List<JourneyData>> call =  apiInterface.getData("TOKEN "+sharedPreferences.getString("token","xxx"));
        call.enqueue(new Callback<List<JourneyData>>() {
            @Override
            public void onResponse(Call<List<JourneyData>> call, Response<List<JourneyData>> response) {
                if(response.isSuccessful()){
                   SyncDataWithServer(response,editor);
                   ShowNotificationforJp();

                }else
                {
                    //show that err
                }
            }

            @Override
            public void onFailure(Call<List<JourneyData>> call, Throwable t) {
                //show that err

            }
        });

        return Result.RETRY;
    }
    private void SyncDataWithServer(Response<List<JourneyData>> response, SharedPreferences.Editor editor)
    {
        //TODO:Syncs Vars in Shared Prefernces using SyncDataWithServer()
        //vars :
        // journey_point,
        // UnixTimeOfLastSync,
        // op_date,
        // op_name,
        // AboutOpSurgeryLinks,
        // AboutAnesLinks,
        // AboutPhysioLinks,
        // AboutWoundCareLinks
        // IsAlertFromServToPt,
        //AlertMsgFromServToPt,
        //MsgFromServToPt
        for(int i = 0; i < response.body().size(); i++)
        {
            //set values
            //TODO: compare APP's UNIX timeof last sync to UnixTimeOfLastSync to determine journey_point

            if(Long.parseLong(response.body().get(i).getUnixTimeOfLastSync()) > Long.parseLong(sharedPreferences.getString("AppUNIXtimeofLastSync","0"))  )
            {
                editor.putString("journey_point", response.body().get(i).getJourney_point());
            }else
            {

            }


            editor.putString("op_date", response.body().get(i).getOp_date());
            editor.putString("op_name", response.body().get(i).getOp_name());
            editor.putString("AboutOpSurgeryLinks", response.body().get(i).getAboutOpSurgeryLinks());
            editor.putString("AboutAnesLinks", response.body().get(i).getAboutAnesLinks());
            editor.putString("AboutPhysioLinks", response.body().get(i).getAboutPhysioLinks());
            editor.putString("AboutWoundCareLinks", response.body().get(i).getAboutWoundCareLinks());
            editor.putString("IsAlertFromServToPt", String.valueOf(response.body().get(i).isAlertFromServToPt()));
            editor.putString("AlertMsgFromServToPt", response.body().get(i).getAlertMsgFromServToPt());
            editor.putString("MsgFromServToPt", response.body().get(i).getMsgFromServToPt());


        }
        editor.commit();

    }

    void ShowNotificationforJp() {
        //start showing notif at start and  stop at deadline

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

