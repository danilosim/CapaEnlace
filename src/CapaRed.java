public class CapaRed {

    private CapaEnlace capaEnlace;

    public CapaEnlace getCapaEnlace() {
        return capaEnlace;
    }

    public void setCapaEnlace(CapaEnlace capaEnlace) {
        this.capaEnlace = capaEnlace;
    }

    public void enviarDatos(String paquete){
        capaEnlace.enviarDatos(paquete);
    }

    public void recibirDatos(String paquete){
        System.out.println("Capa de Red recibio paquete con mensaje: \"" + paquete + "\"");
    }

}
