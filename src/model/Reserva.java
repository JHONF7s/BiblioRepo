package model;
import model.Cliente;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Libro libro;
    private LocalDateTime date;

    public Reserva(Cliente cliente, Libro libro){
        this.cliente = cliente;
        this.libro = libro;
        date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void cancelar(){
        cliente.setEstado(0);
        libro.setEstado(0);
        cliente = null;
        libro = null;
        date = null;
    }
}
