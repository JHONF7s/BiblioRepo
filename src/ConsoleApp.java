import controllers.*;
import model.Admin;
import model.Cliente;
import model.Libro;
import model.Reserva;
import resources.data.Persistencia;
import java.util.Scanner;
import java.util.List;

public class ConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LoginController loginCtrl = new LoginController();
    private static final ClienteController clienteCtrl = new ClienteController();
    private static final LibroController libroCtrl = new LibroController();
    private static final ReservaController reservaCtrl = new ReservaController();
    private static final HistorialController historialCtrl = new HistorialController();
    private static final ReporteController reporteCtrl = new ReporteController();

    public static void run() {
        System.out.println("========================================");
        System.out.println("   SISTEMA DE GESTIÓN BIBLIOREPO (CLI)  ");
        System.out.println("========================================");
        
        Admin admin = login();
        if (admin == null) {
            System.out.println("Error de autenticación. Saliendo del sistema.");
            return;
        }
        
        System.out.println("\nBienvenido/a, " + admin.getName());
        boolean salir = false;
        while (!salir) {
            printMainMenu();
            int opcion = readInt();
            switch (opcion) {
                case 1: menuClientes(); break;
                case 2: menuLibros(); break;
                case 3: menuPrestamosReservas(); break;
                case 4: menuHistorial(); break;
                case 5: System.out.println(reporteCtrl.generarReporteGeneral()); break;
                case 6: salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
        System.out.println("¡Gracias por usar BiblioRepo! Hasta pronto.");
    }

    private static Admin login() {
        System.out.println("\n--- Inicio de Sesión ---");
        System.out.print("ID de Administrador: ");
        int id = readInt();
        System.out.print("Contraseña: ");
        String pwd = scanner.nextLine();
        return loginCtrl.login(id, pwd);
    }

    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Por favor, ingrese un número válido: ");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1) Gestionar Clientes");
        System.out.println("2) Gestionar Libros");
        System.out.println("3) Préstamos y Reservas");
        System.out.println("4) Consultar Historial");
        System.out.println("5) Reporte General del Sistema");
        System.out.println("6) Salir");
        System.out.print("Seleccione una opción: ");
    }

    // --- SUBMENÚ CLIENTES ---
    private static void menuClientes() {
        while(true) {
            System.out.println("\n[ MENÚ DE CLIENTES ]");
            System.out.println("1) Listar Clientes");
            System.out.println("2) Agregar Cliente");
            System.out.println("3) Eliminar Cliente");
            System.out.println("4) Actualizar Cliente");
            System.out.println("5) Consultar Multas");
            System.out.println("6) Volver al Menú Principal");
            System.out.print("Seleccione: ");
            int opt = readInt();
            switch (opt) {
                case 1: listarClientes(); break;
                case 2: agregarCliente(); break;
                case 3: eliminarCliente(); break;
                case 4: actualizarCliente(); break;
                case 5: consultarMultas(); break;
                case 6: return;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarClientes() {
        List<Cliente> clientes = Persistencia.getInstancia().getClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n--- LISTADO DE CLIENTES ---");
        for (Cliente c : clientes) {
            String est = (c.getEstado() == 0) ? "Libre" : "Con préstamo";
            System.out.printf("Cédula: %-10s | Nombre: %-20s | Estado: %-12s | Multas: $%.2f%n", 
                c.getCedula(), c.getNombre(), est, c.getMultas());
        }
    }

    private static void agregarCliente() {
        System.out.print("Cédula: "); String ced = scanner.nextLine();
        System.out.print("Nombre: "); String nom = scanner.nextLine();
        System.out.print("Teléfono: "); String tel = scanner.nextLine();
        System.out.print("Dirección: "); String dir = scanner.nextLine();
        boolean ok = clienteCtrl.agregarCliente(ced, nom, tel, dir);
        System.out.println(ok ? "Cliente registrado exitosamente." : "Error: El cliente ya existe.");
    }

    private static void eliminarCliente() {
        System.out.print("Ingrese cédula del cliente a eliminar: "); String ced = scanner.nextLine();
        boolean ok = clienteCtrl.eliminarCliente(ced);
        System.out.println(ok ? "Cliente eliminado." : "Error: Cliente no encontrado.");
    }

    private static void actualizarCliente() {
        System.out.print("Cédula del cliente a actualizar: "); String ced = scanner.nextLine();
        Cliente c = clienteCtrl.buscarCliente(ced);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }
        
        System.out.print("Nuevo nombre [" + c.getNombre() + "] (Enter para mantener): "); 
        String nom = scanner.nextLine();
        System.out.print("Nuevo teléfono [" + c.getTelefono() + "] (Enter para mantener): "); 
        String tel = scanner.nextLine();
        
        if (!nom.isEmpty()) c.setNombre(nom);
        if (!tel.isEmpty()) c.setTelefono(tel);
        
        boolean ok = clienteCtrl.modificarCliente(c);
        System.out.println(ok ? "Datos actualizados." : "Error al actualizar.");
    }

    private static void consultarMultas() {
        System.out.print("Cédula del cliente: "); String ced = scanner.nextLine();
        Cliente c = clienteCtrl.buscarCliente(ced);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }
        System.out.printf("El cliente %s tiene un total de multas de: $%.2f%n", c.getNombre(), c.getMultas());
    }

    // --- SUBMENÚ LIBROS ---
    private static void menuLibros() {
        while(true) {
            System.out.println("\n[ MENÚ DE LIBROS ]");
            System.out.println("1) Listar Libros");
            System.out.println("2) Agregar Libro");
            System.out.println("3) Eliminar Libro");
            System.out.println("4) Búsqueda por Título/Autor (Fuzzy)");
            System.out.println("5) Volver al Menú Principal");
            System.out.print("Seleccione: ");
            int opt = readInt();
            switch (opt) {
                case 1: listarLibros(); break;
                case 2: agregarLibro(); break;
                case 3: eliminarLibro(); break;
                case 4: busquedaLibro(); break;
                case 5: return;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarLibros() {
        List<Libro> libros = Persistencia.getInstancia().getLibros();
        if (libros.isEmpty()) { System.out.println("No hay libros en el sistema."); return; }
        System.out.println("\n--- LISTADO DE LIBROS ---");
        for (Libro l : libros) {
            String est = switch(l.getEstado()) {
                case 0 -> "Disponible";
                case 1 -> "Reservado (En cola)";
                case 2 -> "Retirado (Prestado)";
                default -> "Desconocido";
            };
            System.out.printf("ID: %-4d | Título: %-25s | Autor: %-15s | Estado: %-15s | Usos: %d%n",
                l.getId(), l.getTitulo(), l.getAutor(), est, l.getUsos());
        }
    }

    private static void agregarLibro() {
        System.out.print("ID único (número): "); int id = readInt();
        System.out.print("Título: "); String tit = scanner.nextLine();
        System.out.print("Autor: "); String aut = scanner.nextLine();
        System.out.print("Categoría: "); String cat = scanner.nextLine();
        System.out.print("Editorial: "); String ed = scanner.nextLine();
        System.out.print("Año: "); int ano = readInt();
        
        Libro lib = new Libro(id, tit, aut, ed, ano, cat, 0);
        boolean ok = libroCtrl.agregarLibro(lib);
        System.out.println(ok ? "Libro agregado exitosamente." : "Error: El ID ya existe.");
    }

    private static void eliminarLibro() {
        System.out.print("ID del libro a eliminar: "); int id = readInt();
        boolean ok = libroCtrl.eliminarLibro(id);
        System.out.println(ok ? "Libro eliminado." : "Error: ID no encontrado.");
    }

    private static void busquedaLibro() {
        System.out.print("Ingrese término de búsqueda: ");
        String termino = scanner.nextLine();
        List<Libro> resultados = libroCtrl.busquedaFuzzy(termino);
        if (resultados.isEmpty()) { System.out.println("No se encontraron coincidencias."); return; }
        System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
        for (Libro l : resultados) {
            System.out.printf("[%d] %s - %s (%s)%n", l.getId(), l.getTitulo(), l.getAutor(), l.getCategoria());
        }
    }

    // --- SUBMENÚ PRÉSTAMOS Y RESERVAS ---
    private static void menuPrestamosReservas() {
        while(true) {
            System.out.println("\n[ PRÉSTAMOS Y RESERVAS ]");
            System.out.println("1) Listar Préstamos Activos");
            System.out.println("2) Registrar Nuevo Préstamo");
            System.out.println("3) Registrar Reserva (Lista de Espera)");
            System.out.println("4) Procesar Devolución (y cobrar multas)");
            System.out.println("5) Consultar Lista de Espera por Libro");
            System.out.println("6) Volver al Menú Principal");
            System.out.print("Seleccione: ");
            int opt = readInt();
            switch (opt) {
                case 1: listarPrestamosActivos(); break;
                case 2: registrarPrestamo(); break;
                case 3: registrarReserva(); break;
                case 4: procesarDevolucion(); break;
                case 5: consultarListaEspera(); break;
                case 6: return;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarPrestamosActivos() {
        List<Reserva> prestamos = Persistencia.getInstancia().getReservas();
        if (prestamos.isEmpty()) { System.out.println("No hay préstamos activos."); return; }
        System.out.println("\n--- PRÉSTAMOS ACTIVOS ---");
        for (Reserva r : prestamos) {
            System.out.printf("ID Prest: %-3d | Cliente: %-15s | Libro: %-20s | Vence: %s%n",
                r.getId(), r.getCliente().getNombre(), r.getLibro().getTitulo(), r.getFechaLimite());
        }
    }

    private static void registrarPrestamo() {
        System.out.print("Cédula del cliente: "); String ced = scanner.nextLine();
        Cliente cli = clienteCtrl.buscarCliente(ced);
        if (cli == null) { System.out.println("Cliente no registrado."); return; }
        
        System.out.print("ID del libro: "); int idLibro = readInt();
        Libro lib = libroCtrl.buscarLibro(idLibro);
        if (lib == null) { System.out.println("Libro no registrado."); return; }
        
        boolean ok = reservaCtrl.agregarReserva(cli, lib);
        if (ok) System.out.println("Préstamo registrado exitosamente. Fecha límite: 7 días a partir de hoy.");
        else System.out.println("No se pudo realizar el préstamo. El libro debe estar DISPONIBLE y el cliente LIBRE.");
    }

    private static void registrarReserva() {
        System.out.print("Cédula del cliente: "); String ced = scanner.nextLine();
        Cliente cli = clienteCtrl.buscarCliente(ced);
        if (cli == null) { System.out.println("Cliente no registrado."); return; }
        
        System.out.print("ID del libro a reservar: "); int idLibro = readInt();
        Libro lib = libroCtrl.buscarLibro(idLibro);
        if (lib == null) { System.out.println("Libro no registrado."); return; }
        
        boolean ok = reservaCtrl.registrarReservaEnCola(cli, lib);
        System.out.println(ok ? "Cliente añadido a la lista de espera del libro." : "No se pudo registrar la reserva.");
    }

    private static void procesarDevolucion() {
        System.out.print("Ingrese el ID del préstamo que se devuelve: "); int id = readInt();
        boolean ok = reservaCtrl.eliminarReserva(id);
        if (ok) {
            System.out.println("Devolución procesada correctamente.");
        } else {
            System.out.println("Error: No se encontró un préstamo activo con ese ID.");
        }
    }

    private static void consultarListaEspera() {
        System.out.print("ID del libro: "); int idLibro = readInt();
        Libro lib = libroCtrl.buscarLibro(idLibro);
        if (lib == null) { System.out.println("Libro no encontrado."); return; }
        
        List<Cliente> espera = lib.getListaEspera();
        if (espera.isEmpty()) {
            System.out.println("No hay clientes en espera para este libro.");
        } else {
            System.out.println("--- LISTA DE ESPERA PARA: " + lib.getTitulo() + " ---");
            for (int i = 0; i < espera.size(); i++) {
                System.out.println((i+1) + ") " + espera.get(i).getNombre() + " (" + espera.get(i).getCedula() + ")");
            }
        }
    }

    // --- HISTORIAL ---
    private static void menuHistorial() {
        System.out.print("Ingrese cédula del cliente: "); String ced = scanner.nextLine();
        List<Libro> historial = historialCtrl.historialPorCliente(ced);
        if (historial == null || historial.isEmpty()) {
            System.out.println("No hay historial registrado para este cliente.");
            return;
        }
        System.out.println("\n--- HISTORIAL DE LIBROS PRESTADOS ---");
        for (Libro l : historial) {
            System.out.println("• " + l.getTitulo() + " (" + l.getAutor() + ")");
        }
    }
}
