package com.elsewhat.leselekse.model;

/**
 * Created by Dagfinn Parnas on 11.09.2016.
 */
public class LeseLekse {
    private String lekseTitle;
    private int maalRepitisjoner;
    private int repitisjoner;
    private LeseLinje[] leseLinjer;

    public LeseLekse (String lekseTitle, int maalRepitisjoner, LeseLinje[] leseLinjer){
        this.lekseTitle=lekseTitle;
        this.maalRepitisjoner = maalRepitisjoner;
        this.leseLinjer = leseLinjer;
        repitisjoner =0;
    }

    /**
     * Registrer en utført reptisjon.
     * @returns true dersom en har nådd målet for repitisjoner
     */
    public boolean utfortRepitisjon(){
        repitisjoner++;

        if(repitisjoner>= maalRepitisjoner){
            return true;
        }
    }


}

class LeseLinje {
    private String tekst;
    private int fokusPaaStavelseNr;

    public LeseLinje(String tekst){
        this.tekst=tekst;
        fokusPaaStavelseNr=-1;
    }

}