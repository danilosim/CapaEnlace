public class CapaRed {

    /* VARIABLES */

    private CapaEnlace capaEnlace; //Capa de enlace a la que se conecta

    /* SETTERS Y GETTERS */

    public CapaEnlace getCapaEnlace() {
        return capaEnlace;
    }

    public void setCapaEnlace(CapaEnlace capaEnlace) {
        this.capaEnlace = capaEnlace;
    }

    /* MÉTODOS */

    //Envía los paquetes a la capa de enlace
    public void enviarDatos(String paquete){
        capaEnlace.enviarDatos(paquete);
    }

    //Recibe datos desde la capa de enlace
    public void recibirDatos(String paquete){
        System.out.println("Capa de Red recibio paquete con mensaje: \"" + paquete + "\"");
    }

}
