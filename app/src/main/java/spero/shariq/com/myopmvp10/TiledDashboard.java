package spero.shariq.com.myopmvp10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class TiledDashboard extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button StageIconBtn,StageBtn,OpDetailBtn;
    JourneyTime journeyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiled_dashboard);
        sharedPreferences = getApplicationContext().getSharedPreferences("mypref", 0);
        journeyTime = new JourneyTime(this);

        StageIconBtn = findViewById(R.id.StageIconBtn);
        OpDetailBtn = findViewById(R.id.OpDetailBtn);
        //show alert dialogue
        if(IsShowAlertToCompleteCurrentStage(this,sharedPreferences))
        {
            showAlertDialogue(this, sharedPreferences);
        }
        StageIconBtn.setText(sharedPreferences.getString("journey_point","xxx"));
//        OpDetailBtn.setText(daysToOrFromNow(getUnixTimeFor(sharedPreferences.getString("op_date","xxx"))));
        OpDetailBtn.setText("CLICK HERE TO COMPLETE STAGE BEFORE: \n"+getTimeDeadline(this,sharedPreferences));

//        String stm = ""+(getUnixTimeFor(sharedPreferences.getString("op_date","xxx"))-System.currentTimeMillis()/1000);
//        long ms= System.currentTimeMillis()-getUnixTimeFor(sharedPreferences.getString("op_date","xxx"));
//        long ms = getUnixTimeFor(sharedPreferences.getString("op_date","xxx"))-System.currentTimeMillis();
//        int days = (int) (ms / (1000*60*60*24));
//        Toast.makeText(this,daysToOrFromNow(getUnixTimeFor(sharedPreferences.getString("op_date","xxx"))),Toast.LENGTH_LONG).show();
//        Toast.makeText(this,sharedPreferences.getString("op_date","xxx"),Toast.LENGTH_LONG).show();
//        OpDetailBtn.setText(stm);
    }

    private void showAlertDialogue(final Context context, SharedPreferences sharedPreferences) {
        sharedPreferences = context.getSharedPreferences("mypref", 0);
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("THINGS YOU NEED TO DO");
        alertDialog.setMessage("please complete "+ sharedPreferences.getString("journey_point","xxx"));

        final SharedPreferences finalSharedPreferences = sharedPreferences;
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "COMPLETE TASKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                redirectToStage(finalSharedPreferences.getString("journey_point","xxx"),context);

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "LATER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    boolean IsShowAlertToCompleteCurrentStage(Context context, SharedPreferences sharedPreferences)
    {
        
        new JourneyTime(context);
        sharedPreferences = context.getSharedPreferences("mypref", 0);
        boolean res;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (sharedPreferences.getString("journey_point","xxx") )
        {
            case "to wait..syncing with server.. ensure your phone is connected to internet!":
                res = true;
                return res;

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
            case "preop_GotoClinic":
                //if current time is between than stage start & deadline : set alert dialogue and set show notification to TRUE
                if(Calendar.getInstance().getTime().after(journeyTime.PREOP_START) && Calendar.getInstance().getTime().before(journeyTime.PREOP_DEADLINE))
                {
                    editor.putBoolean("IsAlertFromPtToServ",false);
                    res= true;
                }
                //else : set missed stage alert boolean to TRUE;
                else{
                    editor.putBoolean("IsAlertFromPtToServ",true);
                    editor.putString("AlertMsgFromPtToServ", "Need to book preop clinic appointment!");
                    res = false;
                }
                return res;

            case "preop_drug_incomplete":
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

//            case "preop-drug_complete":
//                //set "complete the stage alert" boolean to false;
//                editor.putBoolean("IsAlertFromPtToServ",false);
//                return false;

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

    void redirectToStage(String Journey_point, Context context)
    {
        switch (Journey_point)
        {
            case "preop_incomplete":
                startActivity(new Intent(context, preop.class));
                break;
            case "preop_GotoClinic":
                startActivity(new Intent(context, preop_GotoClinic.class));
                break;
            case "preop_drug_incomplete":
                startActivity(new Intent(context, preop_drug.class));
                break;
            case "reminder1_incomplete":
                startActivity(new Intent(context, reminder1.class));
                break;
            case "reminder2_incomplete":
                startActivity(new Intent(context, reminder2.class));
                break;
            case "pod1_incomplete":
                startActivity(new Intent(context, pod1.class));
                break;
            case "pod2_incomplete":
                startActivity(new Intent(context, pod2.class));
                break;
            case "pod3_incomplete":
                startActivity(new Intent(context, pod3.class));
                break;
            case "pod5_incomplete":
                startActivity(new Intent(context, pod5.class));
                break;
            case "pod10_incomplete":
                startActivity(new Intent(context, pod10.class));
                break;
            case "pod15_incomplete":
                startActivity(new Intent(context, pod15.class));
                break;
        }
    }

    String  getTimeDeadline(Context context, SharedPreferences sharedPreferences)
    {
        new JourneyTime(context);
        sharedPreferences = context.getSharedPreferences("mypref", 0);
        String res;
        switch (sharedPreferences.getString("journey_point","xxx") )
        {
            case "to wait..syncing with server.. ensure your phone is connected to internet!":
                res = "UPLOADING DATA...";
                return res;

            case "preop_incomplete":
                res= journeyTime.PREOP_DEADLINE.toString();
                return res;


            case "preop_GotoClinic":
                res= journeyTime.PREOP_DEADLINE.toString();
                return res;

            case "preop-drug_incomplete":
                res= journeyTime.PREOP_DEADLINE.toString();
                return res;


            case "reminder1_incomplete":
                res= journeyTime.REMINDER1_DEADLINE.toString();
                return res;



            case "reminder2_incomplete":
                res= journeyTime.REMINDER2_DEADLINE.toString();
                return res;




            case "pod1_incomplete":
                res= journeyTime.POST_OP1_DEADLINE.toString();
                return res;



            case "pod2_incomplete":
                res= journeyTime.POST_OP2_DEADLINE.toString();
                return res;


            case "pod3_incomplete":
                res= journeyTime.POST_OP3_DEADLINE.toString();
                return res;


            case "pod5_incomplete":
                res= journeyTime.POST_OP5_DEADLINE.toString();
                return res;



            case "pod10_incomplete":
                res= journeyTime.POST_OP10_DEADLINE.toString();
                return res;



            case "pod15_incomplete":
                res= journeyTime.POST_OP15_DEADLINE.toString();
                return res;


            default:
                return "";




        }
    }

    public void opdetbtnclik(View view) {
        if(IsShowAlertToCompleteCurrentStage(this,sharedPreferences))
        {
            showAlertDialogue(this, sharedPreferences);
        }
        else
        {
            OpDetailBtn.setText("Please wait a few days...");
        }
    }

//    long getUnixTimeFor(String StringDate)
//    {
//        //get Date from string
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//        Date date = null;
//        try {
//            date = format.parse(StringDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        //get UTC from Date
//            return date.getTime();
//
//
//    }
//
//   int getDaysBetween(long t1,long t2)
//   {
//       return (int) ((t1-t2) / (1000*60*60*24));
//   }
//   String daysToOrFromNow(long time)
//   {
//       String res;
//
//       int val = (int) ((System.currentTimeMillis()-time) / (1000*60*60*24));
//       if(val < 0 )//time is future
//       {
//           res = abs(val)+"days to go for surgery";
//       }else{res = val+"days after surgery";}
//       return res;
//   }
}
