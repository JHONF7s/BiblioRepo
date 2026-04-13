import controllers.LibroController;
import controllers.ReservaController;
import model.Cliente;
import model.Libro;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibroController libroController = new LibroController();
        ReservaController reservaController = new ReservaController();

        int opcion = 0;

        while (opcion != 7) {
            System.out.println("----- MENÚ DE BIBLIOTECA -----");
            System.out.println("1. Registrar Libro");
            System.out.println("2. Eliminar Libro");
            System.out.println("3. Registrar Reserva");
            System.out.println("4. Eliminar Reserva");
            System.out.println("5. Mostrar Libros");
            System.out.println("6. Mostrar Reservas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.\n");
                continue;
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("ID del libro (entero): ");
                        int idLibro = Integer.parseInt(scanner.nextLine());
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("Editorial: ");
                        String editorial = scanner.nextLine();
                        System.out.print("Año de publicación: ");
                        int ano = Integer.parseInt(scanner.nextLine());
                        System.out.print("Categoría: ");
                        String categoria = scanner.nextLine();
                        System.out.print("Estado (0=disponible, 1=reservado, 2=retirado): ");
                        int estado = Integer.parseInt(scanner.nextLine());

                        Libro nuevoLibro = new Libro(idLibro, titulo, autor, editorial, ano, categoria, estado);
                        if (libroController.agregarLibro(nuevoLibro)) {
                            System.out.println("Libro registrado exitosamente.");
                        } else {
                            System.out.println("Error: Ya existe un libro con ese ID.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de número inválido.");
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Ingrese ID del libro a eliminar: ");
                        int idEliminar = Integer.parseInt(scanner.nextLine());
                        if (libroController.eliminarLibro(idEliminar)) {
                            System.out.println("Libro eliminado exitosamente.");
                        } else {
                            System.out.println("Error: No se encontró el libro.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de número inválido.");
                    }
                    break;
                case 3:
                    System.out.println("--- Datos del Cliente ---");
                    System.out.print("Cédula: ");
                    String cedula = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Dirección: ");
                    String direccion = scanner.nextLine();
                    Cliente cliente = new Cliente(cedula, nombre, telefono, direccion);

                    try {
                        System.out.print("Ingrese ID del libro a reservar: ");
                        int idLibroReserva = Integer.parseInt(scanner.nextLine());

                        Libro libroParaReserva = libroController.buscarLibro(idLibroReserva);
                        if (libroParaReserva == null) {
                            System.out.println("Error: No se encontró un libro con ese ID.");
                        } else {
                            if (reservaController.agregarReserva(cliente, libroParaReserva)) {
                                System.out.println("Reserva registrada exitosamente.");
                            } else {
                                System.out.println("Error: No se pudo realizar la reserva. (Verifique que el libro y el cliente estén disponibles).");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de número inválido.");
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Ingrese ID de la reserva a eliminar: ");
                        int idReservaEliminar = Integer.parseInt(scanner.nextLine());
                        if (reservaController.eliminarReserva(idReservaEliminar)) {
                            System.out.println("Reserva eliminada exitosamente.");
                        } else {
                            System.out.println("Error: No se encontró la reserva.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de número inválido.");
                    }
                    break;
                case 5:
                    System.out.println("--- Lista de Libros ---");
                    javax.swing.table.DefaultTableModel modelLibros = libroController.populateTable();
                    for (int i = 0; i < modelLibros.getColumnCount(); i++) {
                        System.out.print(modelLibros.getColumnName(i) + "\t");
                    }
                    System.out.println();
                    for (int i = 0; i < modelLibros.getRowCount(); i++) {
                        for (int j = 0; j < modelLibros.getColumnCount(); j++) {
                            System.out.print(modelLibros.getValueAt(i, j) + "\t");
                        }
                        System.out.println();
                    }
                    break;
                case 6:
                    System.out.println("--- Lista de Reservas ---");
                    javax.swing.table.DefaultTableModel modelReservas = reservaController.populateTable();
                    for (int i = 0; i < modelReservas.getColumnCount(); i++) {
                        System.out.print(modelReservas.getColumnName(i) + "\t");
                    }
                    System.out.println();
                    for (int i = 0; i < modelReservas.getRowCount(); i++) {
                        for (int j = 0; j < modelReservas.getColumnCount(); j++) {
                            System.out.print(modelReservas.getValueAt(i, j) + "\t");
                        }
                        System.out.println();
                    }
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
            System.out.println();
        }
        scanner.close();
    }
}