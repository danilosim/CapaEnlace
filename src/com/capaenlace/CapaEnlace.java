package com.capaenlace;

public class CapaEnlace {

    private CapaRed capaRed;
    private CapaFisica capaFisica;

    private String aEnviar;
    private String aRecibir;

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
        aEnviar = paquete;
        int longitud = paquete.length();
        if(puedeEnviar){
            capaFisica.send(String.valueOf(longitud), this);
        }
        capaFisica.send(paquete, this);
    }

    public void recibirDatos(String paquete){
        capaRed.recibirDatos(paquete);
        if (paquete != "Llegó bien"){
            capaFisica.send("Llegó bien", this);
        }

    }

}
