package com.arrange.sports.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kan212 on 17/7/25.
 */

public class FeedTab implements Serializable{

    public String title;
    public String id;

    public FeedTab(String title,String id){
        this.title = title;
        this.id = id;
    }

    public static List<FeedTab> getDefaultList(){
        List<FeedTab> list = new ArrayList<>();
        list.add(new FeedTab("feed1","1"));
        list.add(new FeedTab("feed2","2"));
        list.add(new FeedTab("feed3","3"));
        list.add(new FeedTab("feed4","4"));
        list.add(new FeedTab("feed5","5"));
        list.add(new FeedTab("feed6","6"));
        list.add(new FeedTab("feed7","7"));
        list.add(new FeedTab("feed8","8"));
        return list;
    }

}
