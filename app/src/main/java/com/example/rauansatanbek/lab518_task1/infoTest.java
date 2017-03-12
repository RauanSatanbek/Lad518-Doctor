package com.example.rauansatanbek.lab518_task1;

import java.util.ArrayList;

/**
 * Created by Rauan Satanbek on 09.03.2017.
 */

public class infoTest {
    String procedure, districts, dateFromUser, price;
    ArrayList<String> specialities;
    int status, answers_count;
    infoTest(String procedure, int answers_count, String districts, String dateFromUser, String price, ArrayList<String> specialities,  int status) {
        this.procedure = procedure;
        this.answers_count = answers_count;
        this.districts = districts;
        this.dateFromUser = dateFromUser;
        this.price = price;
        this.specialities = specialities;
        this.status = status;
    }
}
