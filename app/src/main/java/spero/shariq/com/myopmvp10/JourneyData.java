package spero.shariq.com.myopmvp10;

import com.google.gson.annotations.SerializedName;

/**
 * Created by drsha on 18/8/2018.
 */

public class JourneyData {
    @SerializedName("journey_point")
    private String journey_point;

    @SerializedName("UnixTimeOfLastSync")
    private String UnixTimeOfLastSync;

    @SerializedName("op_date")
    private String op_date;

    @SerializedName("op_name")
    private String op_name;

    @SerializedName("AboutOpSurgeryLinks")
    private String AboutOpSurgeryLinks;

    @SerializedName("AboutAnesLinks")
    private String AboutAnesLinks;

    @SerializedName("AboutPhysioLinks")
    private String AboutPhysioLinks;

    @SerializedName("AboutWoundCareLinks")
    private String AboutWoundCareLinks;

    @SerializedName("IsAlertFromServToPt")
    private boolean IsAlertFromServToPt;

    @SerializedName("AlertMsgFromServToPt")
    private String AlertMsgFromServToPt;

    @SerializedName("MsgFromServToPt")
    private String MsgFromServToPt;








    public String getUnixTimeOfLastSync() {
        return UnixTimeOfLastSync;
    }

    public String getOp_date() {
        return op_date;
    }

    public String getAboutOpSurgeryLinks() {
        return AboutOpSurgeryLinks;
    }

    public String getAboutAnesLinks() {
        return AboutAnesLinks;
    }

    public String getAboutPhysioLinks() {
        return AboutPhysioLinks;
    }

    public String getAboutWoundCareLinks() {
        return AboutWoundCareLinks;
    }

    public boolean isAlertFromServToPt() {
        return IsAlertFromServToPt;
    }

    public String getAlertMsgFromServToPt() {
        return AlertMsgFromServToPt;
    }

    public String getMsgFromServToPt() {
        return MsgFromServToPt;
    }

    public String getJourney_point() {
        return journey_point;
    }

    public String getOp_name() {
        return op_name;
    }
}
