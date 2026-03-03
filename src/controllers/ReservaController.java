package controllers;

import model.Cliente;
import model.Libro;
import model.Reserva;
import java.util.ArrayList;

public class ReservaController {
    private ArrayList<Reserva> reservas;

    public ReservaController() {
        this.reservas = new ArrayList<>();
    }

    public boolean agregarReserva(Cliente cliente, Libro libro){
        if (libro.getEstado() == 0 && cliente.getEstado() == 0){
            Reserva reserva = new Reserva(cliente, libro);
            return reservas.add(reserva);
        }
        return false;
    }

    public boolean eliminarReserva(int id){
        Reserva reserva = buscarReserva(id);
        if (reserva != null){
            reserva.cancelar();
            reservas.remove(reserva);
            return true;
        }
        return false;
    }

    public boolean modificarReserva(Reserva reservaNueva){
        Reserva reserva = buscarReserva(reservaNueva.getId());
        if (reserva != null){
            reserva.setCliente(reservaNueva.getCliente());
            reserva.setLibro(reservaNueva.getLibro());
            reserva.setDate(reservaNueva.getDate());
            return true;
        }
        return false;
    }

    public Reserva buscarReserva(int id){
        for (Reserva reserva: reservas)
            if (reserva.getId() == id)
                return reserva;
        return null;
    }
}
