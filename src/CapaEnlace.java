import java.util.ArrayList;
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

    private boolean puedeEnviar = true;

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
        tramas.add(new Trama(paquete, numeroSecuencia, Trama.Tipo.DATO));
//        System.out.println("Se agrego la trama con numero de secuencia " + numeroSecuencia);
        numeroSecuencia++;
    }

    @Override
    public void run(){
        while(true){
            if(!tramas.isEmpty() && !yaEnviado){
                for(Trama trama : tramas){
                    if(trama.getSecuencia() == proximoEnviar) {
//                        System.out.println("Se mandan datos desde " + this.getName());
                        //                    System.out.println(currentThread().getState());
                        capaFisica.send(tramas.get(0), nombreCapa);
                        yaEnviado = true;
                        //                try {
                        //                    Thread.sleep(2000);
                        //                } catch (InterruptedException e) {
                        //                    e.printStackTrace();
                        //                }
                    }
                }
            } if(tramaACK != null){
//                System.out.println("Se manda ACK");
                capaFisica.send(tramaACK, nombreCapa);
                tramaACK = null;
            }
        }
    }


    public void recibirDatos(Trama trama){
        switch (trama.getTipo()){
            case ACK:
//                System.out.println("ACK");
                if (trama.getSecuencia() == proximoEnviar){
                    tramas.remove(0);
                    proximoEnviar++;
                    yaEnviado = false;
                }
                break;
            case NAK:
                System.out.println("NAK");
                break;
            case DATO:
                if(trama.getSecuencia() == proximoRecibir){
                    proximoRecibir++;
                    capaRed.recibirDatos(trama.getPaquete());
//                    System.out.println("Llegaron datos" + this.getNombreCapa());
                    tramaACK = new Trama(null, trama.getSecuencia(), Trama.Tipo.ACK);
                }
        }
    }


    }
