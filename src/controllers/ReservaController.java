package controllers;

import model.Cliente;
import model.Libro;
import model.Reserva;
import controllers.HistorialController;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import resources.data.Persistencia;

public class ReservaController {
    private ArrayList<Reserva> reservas;
    private HistorialController controller;
    private static final double MULTA_POR_DIA = 5000.0;

    public ReservaController() {
        this.reservas = Persistencia.getInstancia().getReservas();
        controller = new HistorialController();
    }

    public boolean agregarReserva(Cliente cliente, Libro libro){
        if (libro == null || cliente == null) return false;

        // Si el libro esta disponible, es un prestamo inmediato
        if (libro.getEstado() == 0 && cliente.getEstado() == 0){
            Reserva reserva = new Reserva(cliente, libro);
            boolean respuesta = reservas.add(reserva);
            libro.incrementarUso();
            libro.setEstado(2); // Retirado
            cliente.setEstado(1); // Con prestamo
            controller.registrarHistorial(reserva);
            
            saveAll();
            return respuesta;
        }
        return false;
    }

    public boolean registrarReservaEnCola(Cliente cliente, Libro libro) {
        if (libro == null || cliente == null) return false;
        if (libro.getEstado() != 0) {
            libro.agregarAListaEspera(cliente);
            saveAll();
            return true;
        }
        return false;
    }

    public boolean eliminarReserva(int id){
        Reserva reserva = buscarReserva(id);
        if (reserva != null){
            // Calcular multa
            LocalDateTime ahora = LocalDateTime.now();
            if (ahora.isAfter(reserva.getFechaLimite())) {
                long diasRetraso = ChronoUnit.DAYS.between(reserva.getFechaLimite(), ahora);
                if (diasRetraso > 0) {
                    double multaTotal = diasRetraso * MULTA_POR_DIA;
                    reserva.getCliente().agregarMulta(multaTotal);
                    System.out.println("¡ATENCIÓN! El cliente " + reserva.getCliente().getNombre() + 
                                       " ha acumulado una multa de $" + multaTotal + " por " + diasRetraso + " días de retraso.");
                }
            }

            // Procesar devolución
            Libro libro = reserva.getLibro();
            Cliente cliente = reserva.getCliente();
            
            reserva.cancelar(); // Esto limpia cliente y libro en la reserva
            boolean respuesta = reservas.remove(reserva);
            
            // Ver si hay alguien en espera para este libro
            Cliente siguiente = libro.siguienteEnEspera();
            if (siguiente != null) {
                System.out.println("El libro '" + libro.getTitulo() + "' ahora está reservado para: " + siguiente.getNombre());
                // Podríamos crear una nueva reserva automáticamente, pero el enunciado dice "indicar mediante un mensaje"
                libro.setEstado(1); // Reservado para el siguiente
            } else {
                libro.setEstado(0); // Disponible
            }
            cliente.setEstado(0); // Cliente ahora libre

            saveAll();
            return respuesta;
        }
        return false;
    }

    private void saveAll() {
        Persistencia.getInstancia().writeHistorial();
        Persistencia.getInstancia().writeReservas();
        Persistencia.getInstancia().writeLibros();
        Persistencia.getInstancia().writeClientes();
    }

    public boolean modificarReserva(Reserva reservaNueva){
        Reserva reserva = buscarReserva(reservaNueva.getId());
        if (reserva != null){
            reserva.setCliente(reservaNueva.getCliente());
            reserva.setLibro(reservaNueva.getLibro());
            reserva.setDate(reservaNueva.getDate());
            reserva.setFechaLimite(reservaNueva.getFechaLimite());
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
        String[] columns = {"ID", "Cliente", "Libro", "Vence", "Estado"};
        DefaultTableModel table = new DefaultTableModel(columns, 0);
        for (Reserva reserva: reservas){
            Object[] row = {
                reserva.getId(),
                reserva.getCliente().getCedula(),
                reserva.getLibro().getId(),
                reserva.getFechaLimite().toString(),
                reserva.getLibro().getEstado()
            };
            table.addRow(row);
        }
        return table;
    }
}
