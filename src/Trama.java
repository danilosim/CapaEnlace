public class Trama {

    private String paquete;
    private int secuencia;
    public enum Tipo {DATO, ACK, NAK};
    private Tipo tipo;
    private long checksum;

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

    @Override
    public String toString() {
        return "Trama con mensaje  \"" + paquete +  "\", secuencia= " + secuencia +  ", tipo= " + tipo;
    }
}
