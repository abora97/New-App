package com.example.newsapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public  Date getDateFromDepartureOrArrivalInquiryString (String date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date1=null;
        try {
            date1=dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String getTimeFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public  String getDateFromDate(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyy");
        return dateFormat.format(date);
    }
}
