public class Trama {

    public enum Tipo {DATO, ACK, NAK}; //Enum que indica si la trama es de datos, ACK o NAK

    /* VARIABLES */

    private String paquete; //El paquete recibido desde la capa de red
    private int secuencia; //Número de secuendia de la trama
    private Tipo tipo; //Tipo de trama (Datos, ACK o NAK)
    private long checksum; //Suma de verificación de la trama

    /* CONSTRUCTORES */

    public Trama (String paquete, int secuencia, Tipo tipo){
        this.paquete = paquete;
        this.secuencia = secuencia;
        this.tipo = tipo;
    }

    public Trama (String paquete, int secuencia, Tipo tipo, long checksum){
        this.paquete = paquete;
        this.secuencia = secuencia;
        this.tipo = tipo;
        this.checksum = checksum;
    }

    /* SETTERS Y GETTERS */

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    /* TO_STRING */

    @Override
    public String toString() {
        return "Trama con mensaje  \"" + paquete +  "\", secuencia= " + secuencia +  ", tipo= " + tipo;
    }
}
