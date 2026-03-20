
package resources.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Cliente;
import model.Libro;
import model.Reserva;

/**
 *
 * @author jhnf
 */
public class Persistencia {
    private static final Persistencia Instance = new Persistencia();

    ArrayList<String> clientes;
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
    public ArrayList<String> getClientes() {
        return clientes;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    public static Persistencia getInstancia(){return Instance;}
    
    
     private ArrayList<String> readClientes(){
        try{
            FileInputStream file = new FileInputStream("src/resources/data/clientes.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            return (ArrayList<String>) reader.readObject();
        } catch (Exception e){
            return new ArrayList<String>();
        }        
    }
     
      private ArrayList<Reserva> readReservas(){
        try{
            FileInputStream file = new FileInputStream("src/resources/data/reservas.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            return (ArrayList<Reserva>) reader.readObject();
        } catch (Exception e){
            return new ArrayList<Reserva>();
        }        
    }
      
    private ArrayList<Libro> readLibros(){
        try{
            FileInputStream file = new FileInputStream("src/resources/data/libros.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            return (ArrayList<Libro>) reader.readObject();
        } catch (Exception e){
            return new ArrayList<Libro>();
        }        
    }
    
    private ArrayList<ArrayList<Libro>> readHistorial(){
        try{
            FileInputStream file = new FileInputStream("src/resources/data/historial.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            return (ArrayList<ArrayList<Libro>>) reader.readObject();
        } catch (Exception e){
            return new ArrayList<ArrayList<Libro>>();
        }        
    }
    
     
    public void writeClientes(){
        try{
            FileOutputStream file = new FileOutputStream("src/resources/data/clientes.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(clientes);
        } catch (Exception e){
            //e.printStackTrace();            
        }        
    }
    
    public void writeReservas(){
        try{
            FileOutputStream file = new FileOutputStream("src/resources/data/reservas.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(reservas);
        } catch (Exception e){
            //e.printStackTrace();            
        }        
    }
    
    public void writeLibros(){
        try{
            FileOutputStream file = new FileOutputStream("src/resources/data/libros.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(libros);
        } catch (Exception e){
            e.printStackTrace();            
        }        
    }
    
    public void writeHistorial(){
        try{
            FileOutputStream file = new FileOutputStream("src/resources/data/historial.dat");
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(historial);
        } catch (Exception e){
            e.printStackTrace();            
        }        
    }
    
    
}
