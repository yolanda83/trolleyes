package net.daw.bean;

import com.google.gson.annotations.Expose;

public class TipousuarioBean {

    @Expose
    private int id;
    @Expose
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
