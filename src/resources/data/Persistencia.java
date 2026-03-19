
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

    ArrayList<Cliente> clientes;
    ArrayList<Libro> libros;
    ArrayList<Reserva> reservas;
    
    private Persistencia() {
        clientes = readClientes();
        reservas = readReservas();
        libros = readLibros();
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
            FileInputStream file = new FileInputStream("src/resources/data/clientes.dat");
            ObjectInputStream reader = new ObjectInputStream(file);
            return (ArrayList<Cliente>) reader.readObject();
        } catch (Exception e){
            return new ArrayList<Cliente>();
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
    
    
}
