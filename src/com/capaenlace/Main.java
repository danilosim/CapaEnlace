package com.capaenlace;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Main {

    public static void main(String[] args) {

        CapaRed capaRedEmisor = new CapaRed();
        CapaRed capaRedReceptor = new CapaRed();
        CapaEnlace capaEnlaceEmisor = new CapaEnlace();
        CapaEnlace capaEnlaceReceptor = new CapaEnlace();
        InterfazRedEnlace redEnlaceIda = new InterfazRedEnlace();
        InterfazRedEnlace redEnlaceVuelta = new InterfazRedEnlace();
        capaRedEmisor.setInterfaz();


        CapaFisica cfisica = new CapaFisica();
        cfisica.send(new BigInteger("1001"));


}
}
