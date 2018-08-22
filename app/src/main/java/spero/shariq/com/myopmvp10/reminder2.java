package spero.shariq.com.myopmvp10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reminder2 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder2);
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
    }



    public void clik(View view) {
        UploadDataToServer();
    }


    void UploadDataToServer()
    {
        progressDialog.setMessage("UPLOADING ...PLEASE WAIT");
        progressDialog.show();
        final String Token = sharedPreferences.getString("token","xxx");

        //patch journey point at server
        Map<String, Object> JpParams = new HashMap<>();
        JpParams.put("journey_point","op_incomplete");
        JpParams.put("UnixTimeOfLastSync",""+System.currentTimeMillis());
        JpParams.put("IsAlertFromPtToServ",false);
        JpParams.put("AlertMsgFromPtToServ","");





        Call<JourneyData> call2 = apiInterface.patchData("TOKEN " + Token,JpParams );
        call2.enqueue(new Callback<JourneyData>() {
            @Override
            public void onResponse(Call<JourneyData> call, Response<JourneyData> response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }

            }

            @Override
            public void onFailure(Call<JourneyData> call, Throwable t) {

            }
        });
    }
}
