package com.elsewhat.leselekse.model;

/**
 * Created by Dagfinn Parnas on 11.09.2016.
 */
public class LeseLekse {
    private String lekseTitle;
    private int maalRepitisjoner;
    private int repitisjoner;
    private LeseLinje[] leseLinjer;
    private int leseLinjeNaaIndeks;
    private LeseLinje leseLinjeNaa;


    public LeseLekse (String lekseTitle, int maalRepitisjoner, LeseLinje[] leseLinjer){
        this.lekseTitle=lekseTitle;
        this.maalRepitisjoner = maalRepitisjoner;
        this.leseLinjer = leseLinjer;
        repitisjoner =0;
        leseLinjeNaaIndeks=0;

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

    public LeseLinje hentLeseLinje(){
         return leseLinjeNaa;
    }

    public boolean erSisteLeseLinje(){
        if(leseLinjeNaaIndeks >= leseLinjer.length-1){
            return true;
        }else {
            return false;
        }
    }

    public LeseLinje nesteLeseLinje(){
        leseLinjeNaaIndeks++;
        if(leseLinjeNaaIndeks > leseLinjer.length-1){
            leseLinjeNaaIndeks=leseLinjer.length-1;
        }

        leseLinjeNaa = leseLinjer[leseLinjeNaaIndeks];
        leseLinjeNaa.nullstillMarkertOrd();
        return leseLinjeNaa;
    }

    public LeseLinje forrigeLeseLinje(){
        leseLinjeNaaIndeks--;
        if(leseLinjeNaaIndeks<0){
            leseLinjeNaaIndeks=0;
        }

        leseLinjeNaa = leseLinjer[leseLinjeNaaIndeks];
        leseLinjeNaa.nullstillMarkertOrd();
        return leseLinjeNaa;
    }

    public LeseLinje forsteLeseLinje(){
        leseLinjeNaaIndeks=0;
        leseLinjeNaa = leseLinjer[leseLinjeNaaIndeks];
        leseLinjeNaa.nullstillMarkertOrd();
        return leseLinjeNaa;
    }


}
