package controllers;

import model.Cliente;
import model.Libro;
import model.Reserva;
import resources.data.Persistencia;
import java.util.ArrayList;
import java.util.List;

public class ReporteController {
    private ArrayList<Libro> libros;
    private ArrayList<Cliente> clientes;
    private ArrayList<Reserva> reservas;
    private ArrayList<ArrayList<Libro>> historial;

    public ReporteController() {
        this.libros = Persistencia.getInstancia().getLibros();
        this.clientes = Persistencia.getInstancia().getClientes();
        this.reservas = Persistencia.getInstancia().getReservas();
        this.historial = Persistencia.getInstancia().getHistorial();
    }

    public String generarReporteGeneral() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE GENERAL DEL SISTEMA ===\n");
        sb.append("Total de libros registrados: ").append(libros.size()).append("\n");
        sb.append("Total de clientes registrados: ").append(clientes.size()).append("\n");
        sb.append("Préstamos activos: ").append(reservas.size()).append("\n");
        
        int totalHistorico = 0;
        for (ArrayList<Libro> h : historial) {
            totalHistorico += h.size();
        }
        sb.append("Total préstamos históricos: ").append(totalHistorico).append("\n");

        // Cliente con mayor número de préstamos
        Cliente topCliente = null;
        int maxPrestamos = -1;
        for (int i = 0; i < clientes.size(); i++) {
            int count = (i < historial.size()) ? historial.get(i).size() : 0;
            if (count > maxPrestamos) {
                maxPrestamos = count;
                topCliente = clientes.get(i);
            }
        }
        if (topCliente != null) {
            sb.append("Cliente con más préstamos: ").append(topCliente.getNombre()).append(" (").append(maxPrestamos).append(")\n");
        }

        // Libro menos solicitado
        Libro leastLibro = null;
        int minUsos = Integer.MAX_VALUE;
        for (Libro l : libros) {
            if (l.getUsos() < minUsos) {
                minUsos = l.getUsos();
                leastLibro = l;
            }
        }
        if (leastLibro != null) {
            sb.append("Libro menos solicitado: ").append(leastLibro.getTitulo()).append(" (").append(minUsos).append(" usos)\n");
        }
        
        sb.append("====================================");
        return sb.toString();
    }
}
