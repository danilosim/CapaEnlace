import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CapaFisica {

    private static CapaFisica capaFisica;
    private int delay = 3; //segundos
    private CapaEnlace capaEnlaceUno;
    private CapaEnlace capaEnlaceDos;
    private boolean puedeEnviar = true;
    private Trama tramaModificada;


    private CapaFisica(CapaEnlace capaEnlaceUno, CapaEnlace capaEnlaceDos){
        this.capaEnlaceUno = capaEnlaceUno;
        this.capaEnlaceDos = capaEnlaceDos;
    }

    static CapaFisica getInstance(CapaEnlace capaEnlaceUno, CapaEnlace capaEnlaceDos){
        if(capaFisica == null){
            capaFisica = new CapaFisica(capaEnlaceUno, capaEnlaceDos);
            return capaFisica;
        }else {
            return capaFisica;
        }
    }

    public synchronized void send(Trama trama, String nombreEmisor){
        if (!puedeEnviar){
            try {
                System.out.println(Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            puedeEnviar = false;
            try {
                System.out.println("Comienza a mandarse la trama " + trama.toString() + " desde la capa de enlace " + nombreEmisor);
                TimeUnit.SECONDS.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(capaEnlaceUno.getNombreCapa().equals(nombreEmisor)){
                if(Math.random() < 0.8){

                    tramaModificada = new Trama(trama.getPaquete(), trama.getSecuencia(), trama.getTipo(), trama.getChecksum());
                    do{
                        Random random = new Random();
                        int index = random.nextInt(tramaModificada.getPaquete().length());

                        Random r = new Random();
                        char c = (char)(r.nextInt(26) + 'a');
                        StringBuilder paqueteModificado = new StringBuilder(trama.getPaquete());
//                        paqueteModificado.deleteCharAt(index);
                        paqueteModificado.setCharAt(index, c);

                        tramaModificada.setPaquete(paqueteModificado.toString());
                    } while (Math.random() <0.5);
                }
                if(Math.random() > 0.1){
                    System.out.println("Llego: " + trama.toString() + " a la capa de enlace " + capaEnlaceDos.getNombreCapa());
                    capaEnlaceDos.recibirDatos((tramaModificada == null) ? trama : tramaModificada);
                }
            } else {
                if(Math.random() > 0.1){
                    System.out.println("Llego: " + trama.toString() + " a la capa de enlace " + capaEnlaceUno.getNombreCapa());
                    capaEnlaceUno.recibirDatos((tramaModificada == null) ? trama : tramaModificada);
                }
            }
            puedeEnviar = true;
            notifyAll();
        }

    }


    public boolean getPuedeEnviar() {
        return puedeEnviar;
    }

    public void setPuedeEnviar(boolean puedeEnviar) {
        this.puedeEnviar = puedeEnviar;
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

}
