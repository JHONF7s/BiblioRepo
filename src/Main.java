import controllers.LibroController;
import controllers.ReservaController;
import controllers.ClienteController;
import model.Cliente;
import model.Libro;
import resources.data.Persistencia;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibroController libroController = new LibroController();
        ReservaController reservaController = new ReservaController();
        ClienteController clienteController = new ClienteController();

        int opcion = 0;

        while (opcion != 9) {
            System.out.println("----- MENÚ DE BIBLIOTECA -----");
            System.out.println("1. Registrar Libro");
            System.out.println("2. Eliminar Libro / Devolver");
            System.out.println("3. Registrar Reserva / Préstamo");
            System.out.println("4. Mostrar Libros");
            System.out.println("5. Mostrar Préstamos Activos");
            System.out.println("6. Consultar Multas de Cliente");
            System.out.println("7. Consultar Reservas de un Libro");
            System.out.println("8. Reporte General");
            System.out.println("9. Salir");
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
                        System.out.print("Estado (0=disponible, 2=retirado): ");
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
                        System.out.print("¿Desea eliminar un libro (1) o procesar una devolución (2)?: ");
                        int subOp = Integer.parseInt(scanner.nextLine());
                        if (subOp == 1) {
                            System.out.print("Ingrese ID del libro a eliminar: ");
                            int idEliminar = Integer.parseInt(scanner.nextLine());
                            if (libroController.eliminarLibro(idEliminar)) {
                                System.out.println("Libro eliminado exitosamente.");
                            } else {
                                System.out.println("Error: No se encontró el libro.");
                            }
                        } else {
                            System.out.print("Ingrese ID del préstamo (reserva) a devolver: ");
                            int idReservaEliminar = Integer.parseInt(scanner.nextLine());
                            if (reservaController.eliminarReserva(idReservaEliminar)) {
                                System.out.println("Devolución procesada exitosamente.");
                            } else {
                                System.out.println("Error: No se encontró el préstamo.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de número inválido.");
                    }
                    break;
                case 3:
                    System.out.println("--- Datos del Cliente ---");
                    System.out.print("Cédula: ");
                    String cedula = scanner.nextLine();
                    Cliente cliente = clienteController.buscarCliente(cedula);
                    if (cliente == null) {
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Teléfono: ");
                        String telefono = scanner.nextLine();
                        System.out.print("Dirección: ");
                        String direccion = scanner.nextLine();
                        cliente = new Cliente(cedula, nombre, telefono, direccion);
                        clienteController.agregarCliente(cliente);
                    } else {
                        System.out.println("Cliente encontrado: " + cliente.getNombre());
                    }

                    try {
                        System.out.print("Ingrese ID del libro a prestar/reservar: ");
                        int idLibroReserva = Integer.parseInt(scanner.nextLine());

                        Libro libroParaReserva = libroController.buscarLibro(idLibroReserva);
                        if (libroParaReserva == null) {
                            System.out.println("Error: No se encontró un libro con ese ID.");
                        } else {
                            if (reservaController.agregarReserva(cliente, libroParaReserva)) {
                                System.out.println("Operación realizada.");
                            } else {
                                System.out.println("Error: No se pudo realizar la operación.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de número inválido.");
                    }
                    break;
                case 4:
                    System.out.println("--- Lista de Libros ---");
                    javax.swing.table.DefaultTableModel modelLibros = libroController.populateTable();
                    for (int i = 0; i < modelLibros.getRowCount(); i++) {
                        System.out.println("ID: " + modelLibros.getValueAt(i, 0) + " | Titulo: " + modelLibros.getValueAt(i, 1) + " | Estado: " + modelLibros.getValueAt(i, 6));
                    }
                    break;
                case 5:
                    System.out.println("--- Lista de Préstamos Activos ---");
                    javax.swing.table.DefaultTableModel modelReservas = reservaController.populateTable();
                    for (int i = 0; i < modelReservas.getRowCount(); i++) {
                        System.out.println("ID: " + modelReservas.getValueAt(i, 0) + " | Cliente: " + modelReservas.getValueAt(i, 1) + " | Libro ID: " + modelReservas.getValueAt(i, 2) + " | Fecha: " + modelReservas.getValueAt(i, 3));
                    }
                    break;
                case 6:
                    System.out.print("Ingrese cédula del cliente: ");
                    String cMulta = scanner.nextLine();
                    Cliente clMulta = clienteController.buscarCliente(cMulta);
                    if (clMulta != null) {
                        System.out.println("Multas acumuladas para " + clMulta.getNombre() + ": $" + clMulta.getMultasAcumuladas());
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;
                case 7:
                    try {
                        System.out.print("Ingrese ID del libro: ");
                        int idL = Integer.parseInt(scanner.nextLine());
                        Libro lib = libroController.buscarLibro(idL);
                        if (lib != null) {
                            System.out.println("Lista de espera para '" + lib.getTitulo() + "':");
                            for (Cliente c : lib.getListaEsperaReservas()) {
                                System.out.println("- " + c.getNombre() + " (" + c.getCedula() + ")");
                            }
                        } else {
                            System.out.println("Libro no encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato inválido.");
                    }
                    break;
                case 8:
                    reservaController.generarReporteGeneral(
                        Persistencia.getInstancia().getLibros(),
                        Persistencia.getInstancia().getClientes(),
                        Persistencia.getInstancia().getHistorial()
                    );
                    break;
                case 9:
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