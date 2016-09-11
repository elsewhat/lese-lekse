package com.elsewhat.leselekse.model;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by Dagfinn Parnas on 11.09.2016.
 */
public class LeseLinje {
    private static final String LOGTAG = "LeseLinje";

    private String tekst;
    private int fokusPaaStavelseNr;
    private int markerOrdIndeks;
    private int markertOrdStart=0;
    private int markertOrdSlutt=0;
    private int antallOrd;

    public LeseLinje(String tekst){
        this.tekst=tekst;
        fokusPaaStavelseNr=-1;
        markerOrdIndeks=0;
        antallOrd= antallOrd();
        Log.d(LOGTAG, "Antall ord"+antallOrd);
    }

    public Spannable hentTekst(CharacterStyle stilForMarkertOrd){
        Spannable leseTekstSpan = new SpannableString(tekst);
        if(markerOrdIndeks!=0){
            //spesial logikk for å markere et gitt ord. Trenger start og slutt indeks av ordet
            int startIndeks = 0;
            int ordIndeks=0;
            for (String token : tekst.split("[\u00A0 \n , -]")) {
                if (token.length() > 0) {
                    ordIndeks++;
                    if(ordIndeks==markerOrdIndeks) {
                        markertOrdStart = tekst.indexOf(token, startIndeks);
                        markertOrdSlutt = markertOrdStart + token.length();
                        //System.out.println("Ord:" + markerOrdIndeks +  " token:" + token + " start:" + markertOrdStart + " slutt:" + markertOrdSlutt);
                        break;
                    }else{
                        startIndeks = tekst.indexOf(token, startIndeks);
                    }
                }
            }


            leseTekstSpan.setSpan(stilForMarkertOrd, markertOrdStart, markertOrdSlutt, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return leseTekstSpan;
    }


    public void markerNesteOrd(){
        markerOrdIndeks++;
        if(markerOrdIndeks>antallOrd){
            markerOrdIndeks=0;
        }
    }

    public void nullstillMarkertOrd(){
        markerOrdIndeks=0;
    }

    public int antallOrd() {
        int antallOrd=0;
        for (String token : tekst.split("[\u00A0 \n , -]")) {
            if (token.length() > 0) {
                antallOrd++;
            }
        }
        return antallOrd;
    }


}
