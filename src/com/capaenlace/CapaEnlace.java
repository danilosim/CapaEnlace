package com.capaenlace;

public class CapaEnlace {

    private InterfazRedEnlace interfazRed;
    private CapaFisica capaFisica;

    public InterfazRedEnlace getInterfazRed() {
        return interfazRed;
    }

    public void setInterfazRed(InterfazRedEnlace interfaz) {
        this.interfazRed = interfaz;
    }

    public void enviarDatos(String paquete){
        capaFisica.send(paquete);
    }

    public void recibirDatos(String paquete){
        interfazRed.mandarCapaRed(paquete);
    }

}
