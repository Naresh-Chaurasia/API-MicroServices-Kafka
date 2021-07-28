package com;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteMe {

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        String strDate= formatter.format(date);
        System.out.println(strDate);
    }
}
