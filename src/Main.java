import java.math.BigInteger;
import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        /* Setup */

        CapaRed capaRedEmisor = new CapaRed();
        CapaRed capaRedReceptor = new CapaRed();
        CapaEnlace capaEnlaceEmisor = new CapaEnlace();
        capaEnlaceEmisor.setNombreCapa("Emisor");
        CapaEnlace capaEnlaceReceptor = new CapaEnlace();
        capaEnlaceReceptor.setNombreCapa("Receptor");
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
        capaRedEmisor.enviarDatos("Mensaje del medio");
        capaRedEmisor.enviarDatos("Mensaje final");

        capaRedReceptor.enviarDatos("De vuelta 1");
        capaRedReceptor.enviarDatos("De vuelta 2");

        capaEnlaceEmisor.setName("ThreadEmisor");
        capaEnlaceReceptor.setName("ThreadReceptor");

        capaEnlaceEmisor.start();
        capaEnlaceReceptor.start();

//        TimeUnit.SECONDS.sleep(60);
//
//        capaRedReceptor.enviarDatos("Mensaje en otro sentido");
//        capaRedEmisor.enviarDatos("Mensaje añadido despues");



    }



}

