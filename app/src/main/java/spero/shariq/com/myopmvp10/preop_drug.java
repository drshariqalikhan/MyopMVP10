package spero.shariq.com.myopmvp10;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static spero.shariq.com.myopmvp10.MainActivity.REQUEST_PERMISSION;

public class preop_drug extends AppCompatActivity {
    ImageView imageView;
    LinearLayout MedicationFreqLayout;
    android.widget.Spinner Spinner;
    Button uploadtoserverbtn,cancel_actionbtn;
    Bitmap bitmap;
    String imagepath;
    //
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preop_drug);
        imageView = findViewById(R.id.imageview);
        uploadtoserverbtn = findViewById(R.id.uploadtoserverbtn);
        cancel_actionbtn = findViewById(R.id.cancel_actionbtn);
        MedicationFreqLayout = findViewById(R.id.MedicationFreqLayout);
        Spinner = findViewById(R.id.Spinner);
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(this);

        uploadtoserverbtn.setEnabled(false);
        MedicationFreqLayout.setVisibility(View.GONE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

    }

    public void uploadbtnClick(View view) {
        UploadTheImage();
    }

    public void CancelClick(View view) {
        UploadDataToServer();
    }

    public void imgclick(View view) {
        SelectTheImage();

    }

    //helper methods for Image Select
    void SelectTheImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
        uploadtoserverbtn.setEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if(data == null)
            {
                Toast.makeText(this,"data null",Toast.LENGTH_LONG).show();
                return;
            }

            Uri uri = data.getData();
            try {
                //img.setEnabled(true);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);


                imageView.setImageBitmap(bitmap);
                MedicationFreqLayout.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imagepath = getRealPathFromUri(uri);
        }

    }


    String getRealPathFromUri(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;

    }

    //helper methods for Image upload

    void UploadTheImage()
    {
        final String Token = sharedPreferences.getString("token","xxx");
        progressDialog.setMessage("UPLOADING IMAGE...PLEASE WAIT");
        progressDialog.show();
        File file = new File(imagepath);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",Spinner.getSelectedItem().toString()+".jpg",requestBody);

        Call<List<MedImageModel>> call = apiInterface.uploadImage("TOKEN "+Token,body);
        call.enqueue(new Callback<List<MedImageModel>>() {
            @Override
            public void onResponse(Call<List<MedImageModel>> call, Response<List<MedImageModel>> response) {
                if(response.isSuccessful())
                {

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"image uploaded!",Toast.LENGTH_LONG).show();
                    uploadtoserverbtn.setEnabled(false);
                    cancel_actionbtn.setEnabled(true);
                    imageView.setImageResource(R.drawable.searchgallery);

                    Spinner.setSelection(0);
                    MedicationFreqLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<MedImageModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Noooooooooo!",Toast.LENGTH_LONG).show();
            }
        });
    }

    void UploadDataToServer()
    {
        progressDialog.setMessage("UPLOADING ...PLEASE WAIT");
        progressDialog.show();
        final String Token = sharedPreferences.getString("token","xxx");

        //patch journey point at server
        Map<String, Object> JpParams = new HashMap<>();
        JpParams.put("journey_point","reminder1_incomplete");
        JpParams.put("UnixTimeOfLastSync",""+System.currentTimeMillis());
        JpParams.put("IsAlertFromPtToServ",true);
        JpParams.put("AlertMsgFromPtToServ","Please Review Patient Medications");





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
