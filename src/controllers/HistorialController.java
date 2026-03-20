package controllers;

import model.Reserva;
import model.Libro;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import resources.data.Persistencia;

public class HistorialController {
    private ArrayList<ArrayList<Libro>> historial;
    private ArrayList<String> clientes;

    public HistorialController(){
            historial = Persistencia.getInstancia().getHistorial();
            clientes = Persistencia.getInstancia().getClientes();
    }

    public void registrarHistorial(Reserva reserva){
        String cedula = reserva.getCliente().getCedula();
        int index = buscarCliente(cedula);
        ArrayList<Libro> historialc = (index < historial.size()) ? historial.get(index) : new ArrayList<>();
        historialc.add(reserva.getLibro());

        if (!(index < historial.size()))
                historial.add(historialc);
    }
    
    public void quitarRegistro(Reserva reserva){
    
    }

    private int buscarCliente(String cedula){
        for (int i = 0; i < clientes.size(); i++)
            if (clientes.get(i).equals(cedula))
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
