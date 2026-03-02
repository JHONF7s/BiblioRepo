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

    public boolean eliminarLibro(){

        return false;
    }

    public boolean modificarLibro(){

        return false;
    }

    public Libro buscarLibro(){
        return null;
    }
}

