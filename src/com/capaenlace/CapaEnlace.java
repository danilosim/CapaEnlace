package com.capaenlace;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class CapaEnlace extends Thread{

    private CapaRed capaRed;
    private CapaFisica capaFisica;

    private List<Trama> tramas = new ArrayList<>();
    private int numeroSecuencia = 0;
    private int proximoEnviar = 0;
    private int proximoRecibir = 0;

    private boolean puedeEnviar = true;

    public CapaRed getCapaRed() {
        return capaRed;
    }

    public void setCapaRed(CapaRed capaRed) {
        this.capaRed = capaRed;
    }

    public CapaFisica getCapaFisica() {
        return capaFisica;
    }

    public void setCapaFisica(CapaFisica capaFisica) {
        this.capaFisica = capaFisica;
    }

    public void enviarDatos(String paquete){
        tramas.add(new Trama(paquete, numeroSecuencia, Trama.Tipo.DATO));
        numeroSecuencia++;
    }

    public void recibirDatos(Trama trama, int secuencia){
        capaRed.recibirDatos(trama.getPaquete());
        if (!trama.getPaquete().equals("Llegó bien")){
            capaFisica.send("Llegó bien", this, secuencia);
        } else {
            proximoRecibir++;
        }

    }


    }
