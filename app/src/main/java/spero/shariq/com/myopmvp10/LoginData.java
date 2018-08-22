package spero.shariq.com.myopmvp10;

import com.google.gson.annotations.SerializedName;

/**
 * Created by drsha on 18/8/2018.
 */

public class LoginData {
    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }
}
