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
}
