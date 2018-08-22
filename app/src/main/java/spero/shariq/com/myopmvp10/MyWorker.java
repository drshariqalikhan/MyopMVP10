package spero.shariq.com.myopmvp10;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import androidx.work.Worker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by drsha on 18/8/2018.
 */

public class MyWorker extends Worker{

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @NonNull
    @Override
    public Result doWork() {
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<JourneyData>> call =  apiInterface.getData("TOKEN "+sharedPreferences.getString("token","xxx"));
        call.enqueue(new Callback<List<JourneyData>>() {
            @Override
            public void onResponse(Call<List<JourneyData>> call, Response<List<JourneyData>> response) {
                if(response.isSuccessful()){
                   SyncDataWithServer(response,editor);

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

}
