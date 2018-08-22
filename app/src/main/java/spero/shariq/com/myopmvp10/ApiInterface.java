package spero.shariq.com.myopmvp10;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by drsha on 18/8/2018.
 */

public interface ApiInterface {

    //    @Headers("Authorization: Token b959d32cd0001f63b30e24da5d7ae40f89683c74")

    @POST("log/")
    Call<LoginData> loginwith(@Body Map<String, String> params);


    @POST("api/connect/")
    Call<List<JourneyData>> getData(@Header("Authorization") String authToken);

    @PATCH("api/connect/")
    Call<JourneyData> patchData(@Header("Authorization") String authToken,@Body Map<String, Object> Journey_point);


//    @PUT("api/connect/")
    @PUT("api/preop/")
    Call<JourneyData> putData(@Header("Authorization") String authToken,@Body Map<String, Object> params);

    @Multipart
    @PUT("api/medpic/")
    Call<List<MedImageModel>> uploadImage(@Header("Authorization") String authToken,@Part MultipartBody.Part image);

    @POST("api/medpic/")
    Call<List<MedImageModel>> getImage(@Header("Authorization") String authToken);
//    @POST("api/medpic/")
//    Call<List<ResponseBody>> getImage(@Header("Authorization") String authToken);
}
