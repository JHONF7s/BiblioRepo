package resources.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Cliente;
import model.Libro;
import model.Reserva;

public class Persistencia {
    private static final Persistencia Instance = new Persistencia();
    private static final String DATA_DIR = "data/";

    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    ArrayList<Cliente> clientes;
    ArrayList<Libro> libros;
    ArrayList<Reserva> reservas;
    ArrayList<ArrayList<Libro>> historial;
    
    private Persistencia() {
        clientes = readClientes();
        reservas = readReservas();
        libros = readLibros();
        historial = readHistorial();
    }

    public ArrayList<ArrayList<Libro>> getHistorial(){
        return historial;
    }
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    public static Persistencia getInstancia(){return Instance;}
    
    private Object readObject(String filename) {
        try {
            FileInputStream file = new FileInputStream(DATA_DIR + filename);
            ObjectInputStream reader = new ObjectInputStream(file);
            Object obj = reader.readObject();
            reader.close();
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    private void writeObject(Object obj, String filename) {
        try {
            FileOutputStream file = new FileOutputStream(DATA_DIR + filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(obj);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Cliente> readClientes(){
        Object obj = readObject("clientes.dat");
        if (obj instanceof ArrayList) {
            ArrayList list = (ArrayList) obj;
            if (!list.isEmpty() && list.get(0) instanceof String) {
                // Migración de String a Cliente
                ArrayList<Cliente> newList = new ArrayList<>();
                for (Object s : list) {
                    String[] parts = ((String) s).split(",");
                    if (parts.length >= 4) {
                        Cliente c = new Cliente(parts[0], parts[1], parts[2], parts[3]);
                        if (parts.length > 4) c.setEstado(Integer.parseInt(parts[4]));
                        newList.add(c);
                    }
                }
                return newList;
            }
            return (ArrayList<Cliente>) obj;
        }
        return new ArrayList<Cliente>();
    }
     
    private ArrayList<Reserva> readReservas(){
        Object obj = readObject("reservas.dat");
        return obj != null ? (ArrayList<Reserva>) obj : new ArrayList<Reserva>();
    }
      
    private ArrayList<Libro> readLibros(){
        Object obj = readObject("libros.dat");
        return obj != null ? (ArrayList<Libro>) obj : new ArrayList<Libro>();
    }
    
    private ArrayList<ArrayList<Libro>> readHistorial(){
        Object obj = readObject("historial.dat");
        return obj != null ? (ArrayList<ArrayList<Libro>>) obj : new ArrayList<ArrayList<Libro>>();
    }
     
    public void writeClientes(){ writeObject(clientes, "clientes.dat"); }
    public void writeReservas(){ writeObject(reservas, "reservas.dat"); }
    public void writeLibros(){ writeObject(libros, "libros.dat"); }
    public void writeHistorial(){ writeObject(historial, "historial.dat"); }
}
