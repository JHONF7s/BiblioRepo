package controllers;

import model.Reserva;
import model.Libro;
import model.Cliente;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import resources.data.Persistencia;

public class HistorialController {
    private ArrayList<ArrayList<Libro>> historial;
    private ArrayList<Cliente> clientes;

    public HistorialController(){
            historial = Persistencia.getInstancia().getHistorial();
            clientes = Persistencia.getInstancia().getClientes();
    }

    public void registrarHistorial(Reserva reserva){
        String cedula = reserva.getCliente().getCedula();
        int index = buscarCliente(cedula);
        
        // If client doesn't exist in history list, we might need to expand historial
        while (historial.size() <= index) {
            historial.add(new ArrayList<>());
        }
        
        ArrayList<Libro> historialc = historial.get(index);
        historialc.add(reserva.getLibro());
    }
    
    public void quitarRegistro(Reserva reserva){
        // Logic to handle when a loan is active vs finished if needed
    }

    private int buscarCliente(String cedula){
        for (int i = 0; i < clientes.size(); i++)
            if (clientes.get(i).getCedula().equals(cedula))
                return i;

        return clientes.size();
    }

    public ArrayList<Libro> historialPorCliente(String cedula){
            int index = buscarCliente(cedula);
            return (index < historial.size()) ? historial.get(index) : null;
    }
    
    public DefaultTableModel populateTable(String cedula){
        String[] columns = {"ID", "Titulo"};
        DefaultTableModel table = new DefaultTableModel(columns, 0);
        ArrayList<Libro> libros = historialPorCliente(cedula);
        for (Libro libro: libros){
            Object[] row = {
                libro.getId(),
                libro.getTitulo(),
            };
            table.addRow(row);
        }
        return table;
    }
}
