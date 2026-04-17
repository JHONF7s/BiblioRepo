package controllers;

import model.Cliente;
import model.Libro;
import model.Reserva;
import controllers.HistorialController;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import resources.data.Persistencia;

public class ReservaController {
    private ArrayList<Reserva> reservas;
    private HistorialController controller;

    public ReservaController() {
        this.reservas = (ArrayList<Reserva>) Persistencia.getInstancia().getReservas();
        controller = new HistorialController();
    }

    public boolean agregarReserva(Cliente cliente, Libro libro){
        if (libro == null) return false;

        if (libro.getEstado() == 0 && cliente.getEstado() == 0){
            Reserva reserva = new Reserva(cliente, libro);
            // Assign a unique ID correctly
            int maxId = 0;
            for (Reserva r : reservas) if (r.getId() > maxId) maxId = r.getId();
            reserva.setId(maxId + 1);
            
            boolean respuesta = reservas.add(reserva);
            libro.setEstado(2); // Set as borrowed/withdrawn
            cliente.setEstado(1); // Set as with loan
            libro.incrementarUso();
            controller.registrarHistorial(reserva);
            Persistencia.getInstancia().writeHistorial();
            Persistencia.getInstancia().writeReservas();
            Persistencia.getInstancia().writeLibros();
            Persistencia.getInstancia().writeClientes();
            return respuesta;
        } else if (libro.getEstado() != 0) {
            libro.agregarAReserva(cliente);
            System.out.println("Libro no disponible. Cliente " + cliente.getNombre() + " agregado a la lista de espera.");
            Persistencia.getInstancia().writeLibros();
            return true;
        }
        return false;
    }

    public boolean eliminarReserva(int id){
        Reserva reserva = buscarReserva(id);
        if (reserva != null){
            // Calcular multa
            java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
            if (ahora.isAfter(reserva.getFechaLimite())) {
                long diasRetraso = java.time.Duration.between(reserva.getFechaLimite(), ahora).toDays();
                if (diasRetraso > 0) {
                    double multa = diasRetraso * 1000.0; // Ejemplo: 1000 por día
                    reserva.getCliente().agregarMulta(multa);
                    System.out.println("Devolución tardía. Multa aplicada: " + multa);
                }
            }

            Libro libro = reserva.getLibro();
            controller.quitarRegistro(reserva);
            reserva.cancelar();
            boolean repuesta = reservas.remove(reserva);
            
            // Notificar siguiente en lista
            Cliente siguiente = libro.siguienteEnReserva();
            if (siguiente != null) {
                System.out.println("¡AVISO! El libro '" + libro.getTitulo() + "' ya está disponible. Siguiente en lista: " + siguiente.getNombre());
            }

            Persistencia.getInstancia().writeHistorial();
            Persistencia.getInstancia().writeReservas();
            Persistencia.getInstancia().writeLibros();
            Persistencia.getInstancia().writeClientes();
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

    public void generarReporteGeneral(ArrayList<Libro> todosLibros, ArrayList<Cliente> todosClientes, ArrayList<ArrayList<Libro>> historial) {
        System.out.println("----- REPORTE GENERAL DEL SISTEMA -----");
        System.out.println("Total de libros registrados: " + todosLibros.size());
        System.out.println("Total de clientes registrados: " + todosClientes.size());
        System.out.println("Cantidad de préstamos activos: " + reservas.size());

        int prestamosHistoricos = 0;
        for (ArrayList<Libro> h : historial) prestamosHistoricos += h.size();
        System.out.println("Total de préstamos históricos: " + prestamosHistoricos);

        // Cliente con más préstamos
        Cliente topCliente = null;
        int maxPrestamos = -1;
        for (int i = 0; i < todosClientes.size(); i++) {
            if (i < historial.size()) {
                int count = historial.get(i).size();
                if (count > maxPrestamos) {
                    maxPrestamos = count;
                    topCliente = todosClientes.get(i);
                }
            }
        }
        if (topCliente != null) {
            System.out.println("Cliente con más préstamos: " + topCliente.getNombre() + " (" + maxPrestamos + ")");
        }

        // Libro menos solicitado
        Libro leastRequested = null;
        int minUsos = Integer.MAX_VALUE;
        for (Libro l : todosLibros) {
            if (l.getUsos() < minUsos) {
                minUsos = l.getUsos();
                leastRequested = l;
            }
        }
        if (leastRequested != null) {
            System.out.println("Libro menos solicitado: " + leastRequested.getTitulo() + " (" + minUsos + " usos)");
        }
    }

    public DefaultTableModel populateTable(){
        String[] columns = {"ID", "Cliente", "Libro", "Date"};
        DefaultTableModel table = new DefaultTableModel(columns, 0);
        for (Reserva reserva: reservas){

            Object[] row = {
                reserva.getId(),
                reserva.getCliente().getCedula(),
                reserva.getLibro().getId(),
		reserva.getDate().toString()
            };
            table.addRow(row);
        }
        return table;
    }
}
