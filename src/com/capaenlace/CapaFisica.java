package com.capaenlace;

import java.math.BigInteger;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class CapaFisica {

    private int delay = 10; //segundos

    public void send(BigInteger marco){
        if(Math.random()<0.1){
            //modifica el integer simulando un error
        }
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(marco);
    }
}
