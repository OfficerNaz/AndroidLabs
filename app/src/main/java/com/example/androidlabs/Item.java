package com.example.androidlabs;

import android.service.controls.actions.BooleanAction;

public class Item {

    protected String text;
    protected boolean isUrgent;
    protected long id;



    public Item(String text, boolean isUrgent, long id)
    {
        this.text = text;
        this.isUrgent = isUrgent;
        this.id = id;
    }

    public Item(String text, int isUrgent, long id) {

        this(text,  isUrgent == 1d, id);
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public int getUrgentAsInt(){
        return isUrgent?1:0;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }


}
