package spero.shariq.com.myopmvp10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class preop extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preop);
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);


    }

    public void clik(View view) {
        //TODO: update the server
        //[1]update UnixTimeOfLastSync in server
//        editor.putString("AppUNIXtimeofLastSync", ""+System.currentTimeMillis());
        //[2] update all preop vars in server
        //if response from server is UNSUCCESFUL .. initiate a onetimeworkrequest. and set journey_point  to "to wait..syncing with server.. ensure your phone is connected to internet!"
        //[3]update journey point from server response (preop_GotoClinic, preop-drug_incomplete or preop_complete)
//        editor.putString("journey_point","preop_complete");
//        editor.commit();
        UploadDataToServer();
//        startActivity(new Intent(this, MainActivity.class));



    }
    void UploadDataToServer()
    {
        progressDialog.setMessage("UPLOADING ...PLEASE WAIT");
        progressDialog.show();
        final String Token = sharedPreferences.getString("token","xxx");
//        Toast.makeText(getApplicationContext(),Token,Toast.LENGTH_LONG).show();
        Map<String, Object> ObjParams = new HashMap<>();
        ObjParams.put("op_name","test2678");//?should not send but let server determone
        ObjParams.put("UnixTimeOfLastSync",""+System.currentTimeMillis());
        ObjParams.put("isSick",false);
        ObjParams.put("isOnMeds",true);

        Call<JourneyData> call = apiInterface.putData("TOKEN "+Token,ObjParams);
        call.enqueue(new Callback<JourneyData>() {
            @Override
            public void onResponse(Call<JourneyData> call, Response<JourneyData> response) {
                if (response.isSuccessful()) {


                try {
                    //update from server response
                    editor.putString("AppUNIXtimeofLastSync", ""+response.body().getUnixTimeOfLastSync());
                    editor.putString("journey_point", response.body().getJourney_point());
                    editor.putString("op_date", response.body().getOp_date());
                    editor.putString("op_name", response.body().getOp_name());
                    editor.putString("AboutOpSurgeryLinks", response.body().getAboutOpSurgeryLinks());
                    editor.putString("AboutAnesLinks", response.body().getAboutAnesLinks());
                    editor.putString("AboutPhysioLinks", response.body().getAboutPhysioLinks());
                    editor.putString("AboutWoundCareLinks", response.body().getAboutWoundCareLinks());
                    editor.putString("IsAlertFromServToPt", String.valueOf(response.body().isAlertFromServToPt()));
                    editor.putString("AlertMsgFromServToPt", response.body().getAlertMsgFromServToPt());
                    editor.putString("MsgFromServToPt", response.body().getMsgFromServToPt());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    editor.commit();

                }


                catch (Exception e){
                    editor.putString("journey_point", "to wait..syncing with server.. ensure your phone is connected to internet!");
                    //start onetimerequest
                    Toast.makeText(getApplicationContext(),"E:"+e.getMessage(),Toast.LENGTH_LONG).show();

                }
                //patch journey point at server
                    Map<String, Object> JpParams = new HashMap<>();
                    JpParams.put("journey_point",sharedPreferences.getString("journey_point", "xxx"));


                    Call<JourneyData> call2 = apiInterface.patchData("TOKEN " + Token,JpParams );
                    call2.enqueue(new Callback<JourneyData>() {
                        @Override
                        public void onResponse(Call<JourneyData> call, Response<JourneyData> response) {
                            progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<JourneyData> call, Throwable t) {

                        }
                    });
                }else {
                    //unsuccesful response
                    editor.putString("journey_point", "to wait..syncing with server.. ensure your phone is connected to internet!");
                    Toast.makeText(getApplicationContext(),"response fail!",Toast.LENGTH_LONG).show();

                }


        }


            @Override
            public void onFailure(Call<JourneyData> call, Throwable t) {
                //start onetimerequest
                Toast.makeText(getApplicationContext(),"t:"+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }




}
