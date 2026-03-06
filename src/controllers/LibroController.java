package controllers;

import model.Libro;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import resources.data.Persistencia;

public class LibroController {
    private ArrayList<Libro> listaLibros;

    public LibroController() {
        this.listaLibros = Persistencia.getInstancia().getLibros();
    }

    public boolean agregarLibro(Libro libro){
        for (Libro libros : this.listaLibros){
            if (libros.getId() == libro.getId()){
                return false;
            }
        }
        boolean respuesta = this.listaLibros.add(libro);
        Persistencia.getInstancia().writeLibros();
        return respuesta;
    }

    public boolean eliminarLibro(int idLibro){

        for (int i = 0; i < listaLibros.size(); i++){
            if(((Libro)this.listaLibros.get(i)).getId() == idLibro){
                this.listaLibros.remove(i);
                Persistencia.getInstancia().writeLibros();
                return true;
            }
        }

        return false;
    }

    public Libro buscarLibro(int idLibro){
        for (Libro libros : this.listaLibros){
            if (libros.getId() == idLibro){
                return libros;
            }
        }
        return null;
    }

    public boolean modificarLibro(Libro libro){
        for (int i = 0; i < listaLibros.size(); i++){
            if (listaLibros.get(i).getId() == libro.getId()){
                listaLibros.set(i, libro);
                Persistencia.getInstancia().writeLibros();
                return true;
            }
        }
        return false;

    }
    
     public DefaultTableModel populateTable(){
        String[] columns = {"ID", "Titulo", "Autor", "Estado"};                
        DefaultTableModel table = new DefaultTableModel(columns, 0);                           
        
        for (Libro libro: listaLibros){
            
            Object[] row = {
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getEstado()
            };
            table.addRow(row);
        }
        return table;        
    }
}

