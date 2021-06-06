package biblioteca.entidades;

public class Lector {

    private int id_lector, dniLector;
    private String nombreLector, apellidoLector, direLector;
    private boolean estado_lector;

    public Lector() {
    }

    public Lector(int id_lector) {
        this.id_lector = id_lector;
    }

    public Lector(int dniLector, String nombreLector, String apellidoLector, String direLector, boolean estado_lector) {
        this.dniLector = dniLector;
        this.nombreLector = nombreLector;
        this.apellidoLector = apellidoLector;
        this.direLector = direLector;
        this.estado_lector = estado_lector;
    }

    public Lector(int id_lector, int dniLector, String nombreLector, String apellidoLector, String direLector, boolean estado_lector) {
        this.id_lector = id_lector;
        this.dniLector = dniLector;
        this.nombreLector = nombreLector;
        this.apellidoLector = apellidoLector;
        this.direLector = direLector;
        this.estado_lector = estado_lector;
    }

    public int getId_lector() {
        return id_lector;
    }

    public void setId_lector(int id_lector) {
        this.id_lector = id_lector;
    }

    public int getDniLector() {
        return dniLector;
    }

    public void setDniLector(int dniLector) {
        this.dniLector = dniLector;
    }

    public String getNombreLector() {
        return nombreLector;
    }

    public void setNombreLector(String nombreLector) {
        this.nombreLector = nombreLector;
    }

    public String getApellidoLector() {
        return apellidoLector;
    }

    public void setApellidoLector(String apellidoLector) {
        this.apellidoLector = apellidoLector;
    }

    public String getDireLector() {
        return direLector;
    }

    public void setDireLector(String direLector) {
        this.direLector = direLector;
    }

    public boolean getEstado_lector() {
        return estado_lector;
    }

    public void setEstado_lector(boolean estado_lector) {
        this.estado_lector = estado_lector;
    }

    @Override
    public String toString() {
        return "ID Lector: " + id_lector + "\ndniLector=" + dniLector + "\nnombreLector=" + nombreLector + "\napellidoLector=" + apellidoLector + "\ndireLector=" + direLector;
    }

}
