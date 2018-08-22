package spero.shariq.com.myopmvp10;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConnectivityManager conMgr;
    ApiInterface apiInterface;



    public static final int REQUEST_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //Check for Internet & get Permission to use Internet
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //if no permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                    REQUEST_PERMISSION);


            if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                // notify user you are online

                isLoggedIn(apiInterface,sharedPreferences.getString("token","xxx") );



            } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                // notify user you are not online
                Toast.makeText(MainActivity.this,"user you are not online",Toast.LENGTH_LONG).show();
            }
        }
        //if internet permission is already granted
        else
        {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                // notify user you are online
                isLoggedIn(apiInterface, sharedPreferences.getString("token","xxx"));


            } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                // notify user you are not online
                Toast.makeText(MainActivity.this,"user you are not online",Toast.LENGTH_LONG).show();

            }
        }
                        //start periodic 30min sync with workmanager
//                        Constraints constraints = new Constraints.Builder()
//                                .setRequiredNetworkType(NetworkType.CONNECTED)
//                                .build();

    }

    private void isLoggedIn(final ApiInterface apiInterface,final String Token) {
        //TODO: Method sends request to server with Token from Shared Preferences 'token'
        // [1] returns TRUE if it successful.
        // [2] Syncs Vars in Shared Prefernces using SyncDataWithServer()

        Call<List<JourneyData>> call =  apiInterface.getData("TOKEN "+Token);


        call.enqueue(new Callback<List<JourneyData>>() {
            @Override
            public void onResponse(Call<List<JourneyData>> call, Response<List<JourneyData>> response)
            {

                if(response.isSuccessful())
                {
                    try{
                        SyncDataWithServer(response,editor);

                    }catch (Exception e){}
                    startActivity(new Intent(MainActivity.this, TiledDashboard.class));

                }else {


//                       Toast.makeText(MainActivity.this,"please log in...",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, login.class));
                }


            }

            @Override
            public void onFailure(Call<List<JourneyData>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_LONG).show();


            }
        });

    }

   void SyncDataWithServer(Response<List<JourneyData>> response, SharedPreferences.Editor editor)
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

            if(Long.parseLong(response.body().get(i).getUnixTimeOfLastSync()) > Long.parseLong(sharedPreferences.getString("AppUNIXtimeofLastSync","0")) )
            {
                editor.putString("journey_point", response.body().get(i).getJourney_point());
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
}
