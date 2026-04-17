
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
    private static final String DATA_PATH = "/data/";

    ArrayList<Cliente> clientes;
    ArrayList<Libro> libros;
    ArrayList<Reserva> reservas;
    ArrayList<ArrayList<Libro>> historial;
    
    private Persistencia() {
        ensureDataDirExists();
        testWritePermission();
        clientes = readClientes();
        reservas = readReservas();
        libros = readLibros();
        historial = readHistorial();
    }

    private void ensureDataDirExists() {
        try {
            File dir = new File(DATA_PATH);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                System.out.println("Directorio de datos creado en " + DATA_PATH + ": " + created);
            }
            dir.setWritable(true, false);
            dir.setReadable(true, false);
        } catch (Exception e) {
            System.err.println("Error crítico al preparar el directorio de datos: " + e.getMessage());
        }
    }

    private void testWritePermission() {
        try {
            File testFile = new File(DATA_PATH + ".test");
            if (testFile.createNewFile()) {
                testFile.delete();
                System.out.println("Permisos de escritura confirmados en: " + DATA_PATH);
            }
        } catch (Exception e) {
            System.err.println("ADVERTENCIA: No hay permisos de escritura en " + DATA_PATH);
            System.err.println("Verifique los permisos de su carpeta local mountada.");
        }
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
    
    
     private ArrayList<Cliente> readClientes(){
        try{
            FileInputStream file = new FileInputStream(DATA_PATH + "clientes.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            ArrayList<Cliente> res = (ArrayList<Cliente>) reader.readObject();
            reader.close();
            file.close();
            return res;
        } catch (Exception e){
            return new ArrayList<Cliente>();
        }        
    }
     
      private ArrayList<Reserva> readReservas(){
        try{
            FileInputStream file = new FileInputStream(DATA_PATH + "reservas.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            ArrayList<Reserva> res = (ArrayList<Reserva>) reader.readObject();
            reader.close();
            file.close();
            return res;
        } catch (Exception e){
            return new ArrayList<Reserva>();
        }        
    }
      
    private ArrayList<Libro> readLibros(){
        try{
            FileInputStream file = new FileInputStream(DATA_PATH + "libros.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            ArrayList<Libro> res = (ArrayList<Libro>) reader.readObject();
            reader.close();
            file.close();
            return res;
        } catch (Exception e){
            return new ArrayList<Libro>();
        }        
    }
    
    private ArrayList<ArrayList<Libro>> readHistorial(){
        try{
            FileInputStream file = new FileInputStream(DATA_PATH + "historial.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            ArrayList<ArrayList<Libro>> res = (ArrayList<ArrayList<Libro>>) reader.readObject();
            reader.close();
            file.close();
            return res;
        } catch (Exception e){
            return new ArrayList<ArrayList<Libro>>();
        }        
    }
    
     
    public void writeClientes(){
        try{
            FileOutputStream file = new FileOutputStream(DATA_PATH + "clientes.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(clientes);
            writer.close();
            file.close();
        } catch (Exception e){
            System.err.println("Error al escribir clientes: " + e.getMessage());
        }        
    }
    
    public void writeReservas(){
        try{
            FileOutputStream file = new FileOutputStream(DATA_PATH + "reservas.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(reservas);
            writer.close();
            file.close();
        } catch (Exception e){
            System.err.println("Error al escribir reservas: " + e.getMessage());
        }        
    }
    
    public void writeLibros(){
        try{
            FileOutputStream file = new FileOutputStream(DATA_PATH + "libros.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(libros);
            writer.close();
            file.close();
        } catch (Exception e){
            System.err.println("Error al escribir libros: " + e.getMessage());
        }        
    }
    
    public void writeHistorial(){
        try{
            FileOutputStream file = new FileOutputStream(DATA_PATH + "historial.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(historial);
            writer.close();
            file.close();
        } catch (Exception e){
            System.err.println("Error al escribir historial: " + e.getMessage());
        }        
    }
}
