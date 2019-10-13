package com.capaenlace;

public class CapaRed {

    private InterfazRedEnlace interfaz;

    public InterfazRedEnlace getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(InterfazRedEnlace interfaz) {
        this.interfaz = interfaz;
    }

    public void enviarDatos(String paquete){
        interfaz.mandarCapaEnlace(paquete);
    }

    public void recibirDatos(String paquete){

    }

}
