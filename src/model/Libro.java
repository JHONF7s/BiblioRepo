package model;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String editorial;
    private int anoPublicacion;
    private String categoria;
    private int estado; // 0 = disponible, 1 = reservador, 2 = retirado

    public Libro (int id, String titulo, String autor, String editorial, int anoPublicacion, String categoria, int estado){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anoPublicacion = anoPublicacion;
        this.categoria = categoria;
        this.estado = estado;
    }

    public void cambiarEstado(int estado){
        this.estado = estado;
        System.out.println("Cambiando estado del libro a : " + estado)
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
