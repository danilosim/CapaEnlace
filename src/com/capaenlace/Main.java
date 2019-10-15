package com.capaenlace;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Main {

    public static void main(String[] args) {

        /* Setup */

        CapaRed capaRedEmisor = new CapaRed();
        CapaRed capaRedReceptor = new CapaRed();
        CapaEnlace capaEnlaceEmisor = new CapaEnlace();
        CapaEnlace capaEnlaceReceptor = new CapaEnlace();
        CapaFisica capafisica = CapaFisica.getInstance(capaEnlaceEmisor, capaEnlaceReceptor);

        capaRedEmisor.setCapaEnlace(capaEnlaceEmisor);
        capaEnlaceEmisor.setCapaRed(capaRedEmisor);
        capaEnlaceEmisor.setCapaFisica(capafisica);
        capafisica.setCapaEnlaceUno(capaEnlaceEmisor);
        capafisica.setCapaEnlaceDos(capaEnlaceReceptor);
        capaEnlaceReceptor.setCapaFisica(capafisica);
        capaEnlaceReceptor.setCapaRed(capaRedReceptor);
        capaRedReceptor.setCapaEnlace(capaEnlaceReceptor);

        /* Envío de Mensajes*/

        capaRedEmisor.enviarDatos("Mensaje inicial");
        capaRedEmisor.enviarDatos("Mensaje final");

        capaEnlaceEmisor.start();
        capaEnlaceReceptor.start();


}
}
