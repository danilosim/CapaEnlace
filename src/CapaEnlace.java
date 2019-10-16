import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class CapaEnlace extends Thread{

    private CapaRed capaRed;
    private CapaFisica capaFisica;
    private String nombreCapa;

    private volatile List<Trama> tramas = new ArrayList<>();
    private volatile Trama tramaACK;
    private volatile int numeroSecuencia = 0;
    private volatile int proximoEnviar = 0;
    private volatile int proximoRecibir = 0;
    private volatile boolean yaEnviado = false;
    private volatile boolean NAK = false;


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

    public void enviarDatos(String paquete){
        long checksum = CRC.calculateCRC(CRC.Parameters.CCITT, paquete.getBytes());
        tramas.add(new Trama(paquete, numeroSecuencia, Trama.Tipo.DATO, checksum));
//        System.out.println("Se agrego la trama con numero de secuencia " + numeroSecuencia);
        numeroSecuencia++;
    }

    @Override
    public void run(){

        while(true){
            if(tramaACK != null){
                capaFisica.send(tramaACK, nombreCapa);
                tramaACK = null;
            }
            if(!yaEnviado){
                Iterator tramaIterator = tramas.iterator();
                while(tramaIterator.hasNext()){
                    Trama trama = (Trama)tramaIterator.next();
                    if(trama.getSecuencia() == proximoEnviar) {
                        capaFisica.send(tramas.get(0), this.nombreCapa);
                        yaEnviado = true;


                        if(tramaACK != null){
                            capaFisica.send(tramaACK, this.nombreCapa);
                            tramaACK = null;
                        }

                        try {
                            Thread.sleep(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(tramaACK != null){
//                System.out.println("Se manda ACK");
                            capaFisica.send(tramaACK, nombreCapa);
                            tramaACK = null;
                        }

                        if(trama.getSecuencia() == proximoEnviar){
                            yaEnviado = false;
                        } else {
                            tramaIterator.remove();
                        }
                    }
                }
            }
        }
    }


    public void recibirDatos(Trama trama){
        switch (trama.getTipo()){
            case ACK:
//                System.out.println("ACK");
                if (trama.getSecuencia() == proximoEnviar){
//                    tramas.remove(0);
                    proximoEnviar++;
                    yaEnviado = false;
                }
                break;
            case NAK:
//                System.out.println("NAK");
                yaEnviado = false;
                break;
            case DATO:
                if(trama.getSecuencia() == proximoRecibir){
                    if(trama.getChecksum() == CRC.calculateCRC(CRC.Parameters.CCITT, trama.getPaquete().getBytes())){
                        proximoRecibir++;
                        capaRed.recibirDatos(trama.getPaquete());
//                    System.out.println("Llegaron datos" + this.getNombreCapa());
                        tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.ACK);
                    } else {
                        System.out.println("Llegaron datos erroneos: " + trama.toString() + " a la capa de enlace " + this.getNombreCapa());
                        tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.NAK);
                    }
                } else if(trama.getSecuencia() == proximoRecibir - 1){
                    tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.ACK);
                }
        }
    }


    }
