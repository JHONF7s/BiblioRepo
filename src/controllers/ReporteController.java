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
        sb.append("\n====================================\n");
        sb.append("   REPORTE GENERAL DEL SISTEMA\n");
        sb.append("====================================\n");
        sb.append(String.format("%-30s: %d\n", "Total de libros registrados", libros.size()));
        sb.append(String.format("%-30s: %d\n", "Total de clientes registrados", clientes.size()));
        sb.append(String.format("%-30s: %d\n", "Préstamos activos", reservas.size()));
        
        int totalHistorico = 0;
        for (ArrayList<Libro> h : historial) {
            totalHistorico += h.size();
        }
        sb.append(String.format("%-30s: %d\n", "Total préstamos históricos", totalHistorico));

        sb.append("------------------------------------\n");

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
        if (topCliente != null && maxPrestamos > 0) {
            sb.append(String.format("Cliente con más préstamos: %s (%d)\n", topCliente.getNombre(), maxPrestamos));
        } else {
            sb.append("Cliente con más préstamos: N/A\n");
        }

        // Libro menos solicitado
        Libro leastLibro = null;
        int minUsos = Integer.MAX_VALUE;
        if (!libros.isEmpty()) {
            for (Libro l : libros) {
                if (l.getUsos() < minUsos) {
                    minUsos = l.getUsos();
                    leastLibro = l;
                }
            }
        }
        
        if (leastLibro != null) {
            sb.append(String.format("Libro menos solicitado: %s (%d usos)\n", leastLibro.getTitulo(), minUsos));
        } else {
            sb.append("Libro menos solicitado: N/A\n");
        }
        
        sb.append("====================================\n");
        return sb.toString();
    }
}
