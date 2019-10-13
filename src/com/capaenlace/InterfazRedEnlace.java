package com.capaenlace;

public class InterfazRedEnlace {

    public static final CapaEnlace capaEnlace = new CapaEnlace();
    public static final CapaRed capaRed = new CapaRed();

    public void mandarCapaEnlace(String paquete){
        capaEnlace.enviarDatos(paquete);
    }

    public void mandarCapaRed(String paquete){
        capaRed.recibirDatos(paquete);
    }

}
