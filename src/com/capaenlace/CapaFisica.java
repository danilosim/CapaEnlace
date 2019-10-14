package com.capaenlace;

import java.util.concurrent.TimeUnit;

public class CapaFisica {

    private static CapaFisica capaFisica;
    private int delay = 10; //segundos
    private CapaEnlace capaEnlaceUno;
    private CapaEnlace capaEnlaceDos;

    private CapaFisica(CapaEnlace capaEnlaceUno, CapaEnlace capaEnlaceDos){
        this.capaEnlaceUno = capaEnlaceUno;
        this.capaEnlaceDos = capaEnlaceDos;
    }

    public static CapaFisica getInstance(CapaEnlace capaEnlaceUno, CapaEnlace capaEnlaceDos){
        if(capaFisica == null){
            capaFisica = new CapaFisica(capaEnlaceUno, capaEnlaceDos);
            return capaFisica;
        }else {
            return capaFisica;
        }
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public CapaEnlace getCapaEnlaceUno() {
        return capaEnlaceUno;
    }

    public void setCapaEnlaceUno(CapaEnlace capaEnlaceUno) {
        this.capaEnlaceUno = capaEnlaceUno;
    }

    public CapaEnlace getCapaEnlaceDos() {
        return capaEnlaceDos;
    }

    public void setCapaEnlaceDos(CapaEnlace capaEnlaceDos) {
        this.capaEnlaceDos = capaEnlaceDos;
    }

    public void send(String marco, CapaEnlace capaEnlaceEmisora){
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (capaEnlaceUno.equals(capaEnlaceEmisora)){
            capaEnlaceDos.recibirDatos(marco);
        } else {
            capaEnlaceUno.recibirDatos(marco);
        }
    }
}
