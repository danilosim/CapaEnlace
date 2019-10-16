import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class CapaEnlace extends Thread{

    /* VARIABLES */

    private CapaRed capaRed; //Capa de Red a la que esta conectado
    private CapaFisica capaFisica; //Capa Física única
    private String nombreCapa; //Nombre de la Capa de Enlace para identificación

    private volatile List<Trama> tramas = new ArrayList<>(); //Lista de Tramas a enviar
    private volatile Trama tramaACK; //Trama ACK o NAK a enviar
    private volatile int numeroSecuencia = 0; //numero de secuencia para el proximo paquete que llegue de la capa de red
    private volatile int proximoEnviar = 0; //el numero de secuencia de la proxima trama a enviar
    private volatile int proximoRecibir = 0; //el numero de secuencia de la proxima trama a recibir por capa física
    private volatile boolean yaEnviado = false; //si ya se envió la trama que se debía enviar

    /* SETTERS Y GETTERS */

    public String getNombreCapa() {
        return nombreCapa;
    }

    public void setNombreCapa(String nombreCapa) {
        this.nombreCapa = nombreCapa;
    }

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

    /* MÉTODOS */

    //SE ENCARGA DE RECIBIR LOS PAQUETES DESDE LA CAPA DE RED
    public void enviarDatos(String paquete){
        long checksum = CRC.calculateCRC(CRC.Parameters.CCITT, paquete.getBytes()); //Calcula el checksum
        tramas.add(new Trama(paquete, numeroSecuencia, Trama.Tipo.DATO, checksum)); //Crea la trama correspondiente
        numeroSecuencia++; //Incrementa el numero de secuencia para el proximo paquete que llegue desde la capa de red
    }

    //SE ENCARGA DE RECIBIR LAS TRAMAS QUE LLEGAN DESDE LA CAPA FÍSICA
    public void recibirDatos(Trama trama){
        switch (trama.getTipo()){ //Segun el tipo de trama que llega desde la capa física es la acción que realiza
            case ACK: //SI ES ACK
                //Si la secuencia de la trama que llego es igual a la que se mando, significa que la trama llego bien
                if (trama.getSecuencia() == proximoEnviar){
                    proximoEnviar++; //Ya se puede enviar la siguiente trama
                    yaEnviado = false; //Esa trama todavía no se manda
                }
                break;
            case NAK: //SI ES NAK
                yaEnviado = false; //Se setea en falsa para enviar la trama erronea de nuevo
                break;
            case DATO: //SI ES DATO
                //Si la secuencia de la trama recibida es igual a la que tenía que recibir, debo mandar un ACK
                if(trama.getSecuencia() == proximoRecibir){
                    //Tambien debo verificar que el checksum sea igual
                    if(trama.getChecksum() == CRC.calculateCRC(CRC.Parameters.CCITT, trama.getPaquete().getBytes())){
                        proximoRecibir++; //Ahora debo recibir una trama con el siguiente número de secuencia
                        capaRed.recibirDatos(trama.getPaquete()); // Desencapsulo y mando el paquete a la capa de red
                        tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.ACK); //Creo la trama ACK a enviar
                    } else { //Si el checksum no coincide debo mandar un NAK
                        System.out.println("Llegaron datos erroneos: " + trama.toString() + " a la capa de enlace " + this.getNombreCapa());
                        tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.NAK); //Creo el NAK a enviar
                    }
                } else if(trama.getSecuencia() == proximoRecibir - 1){ //Si recibo una trama duplicada debe ser porque no llegó el ACK
                    tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.ACK); //Vuelvo a crear el ACK a enviar
                }
        }
    }

    //THREAD QUE CORRE SIEMPRE Y ENVIA TRAMAS POR CAPA DE ENLACE
    @Override
    public void run(){

        while(true){ //Nunca deja de ejecutar

            if(tramaACK != null){ //Si hay tramas ACK o NAK debo enviarlas antes y luego borrarlas
                capaFisica.send(tramaACK, nombreCapa);
                tramaACK = null;
            }

            if(!yaEnviado){ //Si todavia no se envía la trama a enviar entonces...
                //Se itera la lista de tramas
                Iterator tramaIterator = tramas.iterator();
                while(tramaIterator.hasNext()){ //Siempre que exista alguna trama a enviar entonces...
                    Trama trama = (Trama)tramaIterator.next();
                    if(trama.getSecuencia() == proximoEnviar) { //Si la trama es la proxima a enviar
                        capaFisica.send(tramas.get(0), this.nombreCapa); //La envío a capa física
                        yaEnviado = true; //Seteo el booleano que indica que ya se envió

                        if(tramaACK != null){ //Reintento enviar ACKs y NAKs
                            capaFisica.send(tramaACK, this.nombreCapa);
                            tramaACK = null;
                        }

                        try { //Duerme al Thread por 20 segundos simulando un Timer
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(trama.getSecuencia() == proximoEnviar){ //Si el proximo a enviar sigue igual, significa que no llegó
                            yaEnviado = false; //Se setea en false para que vuelva a enviarse la trama
                        } else {
                            tramaIterator.remove(); //Si se envió correctamente entonces se la borra del buffer
                        }

                        if(tramaACK != null){ //Reintento enviar ACKs y NAKs
                            capaFisica.send(tramaACK, nombreCapa);
                            tramaACK = null;
                        }
                    }
                }
            }
        }
    }





}
