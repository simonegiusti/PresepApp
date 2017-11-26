package it.rockopera.presepapp;



public class DataModel {

    Integer Id;
    Integer Timer_start;
    String Sw_name;
    Integer Sw_state;


    //('id' INTEGER, 'timer_start' INTEGER, 'sw_name' TEXT, 'sw_state' INTEGER);");

    public DataModel(Integer id, Integer timer_start, String sw_name, Integer sw_state) {
        this.Id=id;
        this.Timer_start=timer_start;
        this.Sw_name=sw_name;
        this.Sw_state=sw_state;


    }


    public Integer getID() {
        return Id;
    }

    public Integer getTimer_start() {
        return Timer_start;
    }

    public String getSW() {
        return Sw_name;
    }

    public Integer getState() {
        return Sw_state;
    }




}
