package model;

public class Cliente {
    private int cedula;
    private String nombre; 
    private int telefono;
    private String direccion;
    private String estado;

    public Cliente(int cedula, String nombre, int telefono, String direccion, String estado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }
    

    public int getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

