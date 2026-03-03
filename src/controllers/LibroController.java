package controllers;

import model.Libro;

import java.util.ArrayList;

public class LibroController {
    private ArrayList<Libro> listaLibros;

    public LibroController() {
        this.listaLibros = new ArrayList<>();
    }

    public boolean agregarLibro(Libro libro){
        for (Libro libros : this.listaLibros){
            if (libros.getId() == libro.getId()){
                return false;
            }
        }
        this.listaLibros.add(libro);
        return true;
    }

    public boolean eliminarLibro(int idLibro){

        for (int i = 0; i < listaLibros.size(); i++){
            if(((Libro)this.listaLibros.get(i)).getId() == idLibro){
                this.listaLibros.remove(i);
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
                return true;
            }
        }
        return false;

    }
}

