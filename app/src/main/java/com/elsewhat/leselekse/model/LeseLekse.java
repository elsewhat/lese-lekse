package com.elsewhat.leselekse.model;

/**
 * Created by Dagfinn Parnas on 11.09.2016.
 */
public class LeseLekse {
    private String lekseTitle;
    private int maalRepitisjoner;
    private int repitisjoner;
    private LeseLinje[] leseLinjer;
    private int leseLinjeNaa;

    public LeseLekse (String lekseTitle, int maalRepitisjoner, LeseLinje[] leseLinjer){
        this.lekseTitle=lekseTitle;
        this.maalRepitisjoner = maalRepitisjoner;
        this.leseLinjer = leseLinjer;
        repitisjoner =0;
        leseLinjeNaa=0;

    }

    /**
     * Registrer en utført reptisjon.
     * @returns true dersom en har nådd målet for repitisjoner
     */
    public boolean utfortRepitisjon(){
        repitisjoner++;

        if(repitisjoner>= maalRepitisjoner){
            return true;
        }else {
            return false;
        }
    }

    public LeseLinje nesteLeseLinje(){
        leseLinjeNaa++;
        if(leseLinjeNaa > leseLinjer.length-1){
            return null;
        }else {
            return leseLinjer[leseLinjeNaa];
        }
    }

    public LeseLinje forrigeLeseLinje(){
        leseLinjeNaa--;
        if(leseLinjeNaa<0){
            leseLinjeNaa=0;
        }
        if(leseLinjeNaa > leseLinjer.length-1){
            return null;
        }else {
            return leseLinjer[leseLinjeNaa];
        }
    }

    public LeseLinje forsteLeseLinje(){
        leseLinjeNaa=0;
        if(leseLinjeNaa > leseLinjer.length-1){
            return null;
        }else {
            return leseLinjer[leseLinjeNaa];
        }
    }
}
