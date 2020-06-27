package pucp.telecom.moviles.lab3.otro;

public class Medicion {

    private int duracion;
    private double[] medidas;

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double[] getMedidas() {
        return medidas;
    }

    public void setMedidas(double[] medidas) {
        this.medidas = medidas;
    }

    public Medicion(int duracion, double[] medidas) {
        this.duracion = duracion;
        this.medidas = medidas;
    }

    public Medicion() {
    }




}
