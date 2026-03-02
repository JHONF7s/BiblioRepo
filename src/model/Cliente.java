package model;

public class Cliente {
    private int nombre;
    private String nombre; 
    private int telefono;
    private String direccion;
    private String estado;

    public Cliente(int nombre, String nombre, int telefono, String direccion, String estado) {
        this.nombre = nombre;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
    }

    public int getNombre() {
        return nombre;
    }
    
    public String getNombreString() {
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

    public void setNombre(int nombre) {
        this.nombre = nombre;
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
