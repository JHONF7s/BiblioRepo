package model;

import java.io.Serializable;

public class Cliente implements Serializable {
<<<<<<< HEAD
    private int cedula;
    private String nombre; 
    private int telefono;
=======
    private String cedula;
    private String nombre;
    private String telefono;
>>>>>>> d1bcc31d0294256c266b66dab33abb7ba04e3750
    private String direccion;
    private int estado;

    public Cliente(String cedula, String nombre, String telefono, String direccion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = 0; // 0 libre | 1 con prestamo
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }


    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
}

