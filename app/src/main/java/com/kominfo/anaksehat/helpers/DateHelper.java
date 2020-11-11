package com.kominfo.anaksehat.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static String[] months = {"Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    public static String[] days = {"Hari", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu",
            "Minggu"};

    public static String[] englishDays = {"Day", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    public static String getDate(Date date){
        if(date==null)
            return "";
        SimpleDateFormat dfMonth = new SimpleDateFormat("M");
        SimpleDateFormat dfDay = new SimpleDateFormat("dd");
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");

        return dfDay.format(date)+" "+months[Integer.parseInt(dfMonth.format(date))]+" "
                + dfYear.format(date);
    }

    public static String getDateWithNameDay(Date date){
        if(date==null)
            return "";
        SimpleDateFormat dfDayofMonth = new SimpleDateFormat("EEE", Locale.US);
        SimpleDateFormat dfMonth = new SimpleDateFormat("M");
        SimpleDateFormat dfDay = new SimpleDateFormat("dd");
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");

        return days[getNumberDay(dfDayofMonth.format(date))]+", "+dfDay.format(date)+" "
                + months[Integer.parseInt(dfMonth.format(date))]+" "+ dfYear.format(date);
    }

    private static int getNumberDay(String name){
        for (int i=1;i<englishDays.length;i++) {
            if(name.compareToIgnoreCase(englishDays[i])==0){
                return i;
            }
        }
        return 0;
    }

    public static String getDateServer(Date date){
        if(date==null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public static String getRangeDate(Date start, Date end){
        if(start==null||end==null)
            return "";
        SimpleDateFormat dfDay = new SimpleDateFormat("dd");
        SimpleDateFormat dfMonth = new SimpleDateFormat("M");
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");

        return "("+dfDay.format(start)+" "+months[Integer.parseInt(dfMonth.format(start))] + " "
                + dfYear.format(start)+" - "+dfDay.format(end)+" "
                + months[Integer.parseInt(dfMonth.format(end))]+" "
                + dfYear.format(end)+")";
    }

    public static int getRangeMonth(Date start, Date end){
        if(start==null||end==null)
            return 0;
        SimpleDateFormat dfMonth = new SimpleDateFormat("M");
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");

        int startYear = Integer.parseInt(dfYear.format(start));
        int startMonth = Integer.parseInt(dfMonth.format(start));
        int endYear = Integer.parseInt(dfYear.format(end));
        int endMonth = Integer.parseInt(dfMonth.format(end));

        if(endYear>startYear)
            endMonth = endMonth+(endYear-startYear)*12;
        return endMonth - startMonth;

    }
}
