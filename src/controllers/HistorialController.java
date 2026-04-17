package controllers;

import model.Reserva;
import model.Libro;
import model.Cliente;
import java.util.ArrayList;
import java.util.List;
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
        int index = buscarClienteIndex(cedula);
        
        if (index >= historial.size()) {
            while (historial.size() <= index) {
                historial.add(new ArrayList<Libro>());
            }
        }
        
        historial.get(index).add(reserva.getLibro());
    }
    
    public void quitarRegistro(Reserva reserva){
        // No se suele quitar del historial, pero si se refiere a reservas activas, ya se maneja en ReservaController
    }

    private int buscarClienteIndex(String cedula){
        for (int i = 0; i < clientes.size(); i++)
            if (clientes.get(i).getCedula().equals(cedula))
                return i;

        return clientes.size();
    }

    public ArrayList<Libro> historialPorCliente(String cedula){
            int index = buscarClienteIndex(cedula);
            return (index < historial.size()) ? historial.get(index) : new ArrayList<>();
    }
}
