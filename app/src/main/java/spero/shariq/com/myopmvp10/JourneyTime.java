package spero.shariq.com.myopmvp10;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by drsha on 19/8/2018.
 */

public class JourneyTime {
    SharedPreferences sharedPreferences;
    public Date OP_DATE,
            PREOP_START,
            PREOP_DEADLINE,
            REMINDER1_START,
            REMINDER1_DEADLINE,
            REMINDER2_START,
            REMINDER2_DEADLINE,
            POST_OP1_START,
            POST_OP1_DEADLINE,
            POST_OP2_START,
            POST_OP2_DEADLINE,
            POST_OP3_START,
            POST_OP3_DEADLINE,
            POST_OP5_START,
            POST_OP5_DEADLINE,
            POST_OP10_START,
            POST_OP10_DEADLINE,
            POST_OP15_START,
            POST_OP15_DEADLINE;


    public JourneyTime(Context context)
    {

        sharedPreferences = context.getSharedPreferences("mypref", 0);
        this.OP_DATE = getDateFrom(sharedPreferences.getString("op_date","2000-01-01") );
        this.PREOP_START = DateFromDate(-30,OP_DATE);
        this.PREOP_DEADLINE = DateFromDate(-8,OP_DATE);
        this.REMINDER1_START = DateFromDate(-4,OP_DATE);
        this.REMINDER1_DEADLINE = DeadlineFromDate(18,REMINDER1_START);
        this.REMINDER2_START = DateFromDate(-2,OP_DATE);
        this.REMINDER2_DEADLINE = DeadlineFromDate(18,REMINDER2_START);//set deadline to midnite
        this.POST_OP1_START = DateFromDate(1,OP_DATE);
        this.POST_OP1_DEADLINE = DeadlineFromDate(18,POST_OP1_START);//set deadline to midnite
        this.POST_OP2_START = DateFromDate(2,OP_DATE);
        this.POST_OP2_DEADLINE = DeadlineFromDate(18,POST_OP2_START);//set deadline to midnite
        this.POST_OP3_START = DateFromDate(3,OP_DATE);
        this.POST_OP3_DEADLINE = DeadlineFromDate(18,POST_OP3_START);//set deadline to midnite
        this.POST_OP5_START = DateFromDate(5,OP_DATE);
        this.POST_OP5_DEADLINE = DeadlineFromDate(18,POST_OP5_START);
        this.POST_OP10_START = DateFromDate(10,OP_DATE);
        this.POST_OP10_DEADLINE = DeadlineFromDate(18,POST_OP10_START);//set deadline to midnite
        this.POST_OP15_START = DateFromDate(15,OP_DATE);
        this.POST_OP15_DEADLINE = DeadlineFromDate(18,POST_OP15_START);//set deadline to midnite

    }

    Date getDateFrom(String StringDate)
    {
        //get Date from string
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

       Date date = null;
        try {
            date = format.parse(StringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    Date DateFromDate(int days, Date OriginalDate)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(OriginalDate);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    Date DeadlineFromDate(int hours, Date OriginalDate)
    {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(OriginalDate);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }

}
