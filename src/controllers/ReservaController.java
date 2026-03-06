package controllers;

import model.Cliente;
import model.Libro;
import model.Reserva;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import resources.data.Persistencia;

public class ReservaController {
    private ArrayList<Reserva> reservas;

    public ReservaController() {
        this.reservas = (ArrayList<Reserva>) Persistencia.getInstancia().getReservas();
    }

    public boolean agregarReserva(Cliente cliente, Libro libro){
        if (libro == null) return false;
        
        if (libro.getEstado() == 0 && cliente.getEstado() == 0){
            Reserva reserva = new Reserva(cliente, libro);
            boolean respuesta = reservas.add(reserva);
            Persistencia.getInstancia().writeReservas();
            return respuesta;
        }
        return false;
    }

    public boolean eliminarReserva(int id){
        Reserva reserva = buscarReserva(id);
        if (reserva != null){
            reserva.cancelar();
            boolean repuesta = reservas.remove(reserva);
            Persistencia.getInstancia().writeReservas();
            return repuesta;
        }
        return false;
    }

    public boolean modificarReserva(Reserva reservaNueva){
        Reserva reserva = buscarReserva(reservaNueva.getId());
        if (reserva != null){
            reserva.setCliente(reservaNueva.getCliente());
            reserva.setLibro(reservaNueva.getLibro());
            reserva.setDate(reservaNueva.getDate());
            Persistencia.getInstancia().writeReservas();
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
    
    public DefaultTableModel populateTable(){
        String[] columns = {"ID", "Cliente", "Libro", "Date"};                
        DefaultTableModel table = new DefaultTableModel(columns, 0);                           
        
        for (Reserva reserva: reservas){
            
            Object[] row = {
                reserva.getId(),
                reserva.getCliente().getCedula(),
                reserva.getLibro().getId()
            };
            table.addRow(row);
        }
        return table;        
    }
}
