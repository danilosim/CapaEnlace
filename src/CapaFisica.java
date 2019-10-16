import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CapaFisica {

    /* VARIABLES */

    private static CapaFisica capaFisica; //Como es un singleton guarda una instancia de si misma
    private int delay = 3; //Segundos de espera que simulan el retardo del medio
    private CapaEnlace capaEnlaceUno; //Capa de enlace a la que se conecta en un extremo
    private CapaEnlace capaEnlaceDos; //La otra capa de enlace a la que se conecto
    private boolean puedeEnviar = true; //Indica si el medio esta siendo ocupado
    private Trama tramaModificada; //Simula una trama erronea

    /* CONSTRUCTOR E INSTANCIA */

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

    /* MÉTODOS */

    //Envia datos por la capa física de un extremo a otro
    public synchronized void send(Trama trama, String nombreEmisor){
        if (!puedeEnviar){ //Si el canal está ocupado
            try {
                wait(); //Hace que el Thread espere
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else { //Si el canal está disponible
            puedeEnviar = false; //Se bloquea el canal
            try {
                System.out.println("Comienza a mandarse la trama " + trama.toString() + " desde la capa de enlace " + nombreEmisor);
                TimeUnit.SECONDS.sleep(delay); //Simula el retardo de propagación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Math.random() < 0.1){ //Simula un error de transmisión de la trama modificando un caracter del paquete. Estos errores se detectan con el checksum
                if(trama.getTipo()== Trama.Tipo.DATO){
                    tramaModificada = new Trama(trama.getPaquete(), trama.getSecuencia(), trama.getTipo(), trama.getChecksum());
                    do{
                        Random random = new Random();
                        int index = random.nextInt(tramaModificada.getPaquete().length());

                        Random r = new Random();
                        char c = (char)(r.nextInt(26) + 'a');
                        StringBuilder paqueteModificado = new StringBuilder(trama.getPaquete());
//                        paqueteModificado.deleteCharAt(index);
                        paqueteModificado.setCharAt(index, c); //Se m

                        tramaModificada.setPaquete(paqueteModificado.toString());
                    } while (Math.random() <0.5);
                }
            }
            //Depende de que capa de enlace llegue, es a cual se envía
            if(capaEnlaceUno.getNombreCapa().equals(nombreEmisor)){
                if(Math.random() > 0.1){ //Simula una perdida total de la trama, es decir no se envía
                    System.out.println("Llego: " + trama.toString() + " a la capa de enlace " + capaEnlaceDos.getNombreCapa());
                    capaEnlaceDos.recibirDatos((tramaModificada == null) ? trama : tramaModificada); //Se envía la trama a la capa de enlace
                }
            } else { //Se envia en el otro sentido
                if(Math.random() > 0.1){ //Simula una perdida total de la trama, es decir no se envía
                    System.out.println("Llego: " + trama.toString() + " a la capa de enlace " + capaEnlaceUno.getNombreCapa());
                    capaEnlaceUno.recibirDatos((tramaModificada == null) ? trama : tramaModificada); //Se envía la trama a la capa de enlace
                }
            }
            puedeEnviar = true; //Se libera el canal
            notifyAll(); //Se notifica a los Threads
        }

    }

    /* SETTERS Y GETTERS */

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
