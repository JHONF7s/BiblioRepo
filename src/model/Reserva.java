package model;
import java.io.Serializable;
import model.Cliente;

import java.time.LocalDateTime;

public class Reserva implements Serializable{
    private int id;
    private Cliente cliente;
    private Libro libro;
    private LocalDateTime date;
    private LocalDateTime fechaLimite;

    public Reserva(Cliente cliente, Libro libro){
        this.cliente = cliente;
        this.libro = libro;
        this.date = LocalDateTime.now();
        this.fechaLimite = this.date.plusDays(7);
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
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
        libro.decrementarUsos();
        cliente = null;
        libro = null;
        date = null;
    }
}
