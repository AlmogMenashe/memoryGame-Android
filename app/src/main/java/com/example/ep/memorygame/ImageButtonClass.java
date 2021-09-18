package com.example.ep.memorygame;

/**
 * Created by EP on 22/11/2019.
 */

public class ImageButtonClass {

    int id;
    int arrId;

    ImageButtonClass(int id , int arrId)
    {
        this.arrId = arrId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getArrId() {
        return arrId;
    }
}
