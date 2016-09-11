package com.elsewhat.leselekse.model;

/**
 * Created by Dagfinn Parnas on 11.09.2016.
 */
public class LeseLinje {

    private String tekst;
    private int fokusPaaStavelseNr;

    public LeseLinje(String tekst){
        this.tekst=tekst;
        fokusPaaStavelseNr=-1;
    }

    public String hentTekst(){
        return tekst;
    }

}
