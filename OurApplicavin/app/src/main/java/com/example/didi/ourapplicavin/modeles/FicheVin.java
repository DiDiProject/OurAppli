package com.example.didi.ourapplicavin.modeles;

/**
 * Created by didi on 05/12/2015.
 */
public class FicheVin {
    private Vin vin;
    private String detail;

    public FicheVin(Vin vin, String det){
        this.vin = vin;
        this.detail = det;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setVin(Vin vin) {
        this.vin = vin;
    }
}
