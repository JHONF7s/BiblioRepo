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
        System.out.println("=== Biblioteca Console Application ===");
        Admin admin = login();
        if (admin == null) {
            System.out.println("Login failed. Exiting.");
            return;
        }
        System.out.println("Welcome, " + admin.getName());
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = readInt();
            switch (choice) {
                case 1: clientMenu(); break;
                case 2: bookMenu(); break;
                case 3: reservationMenu(); break;
                case 4: historialMenu(); break;
                case 5: System.out.println(reporteCtrl.generarReporteGeneral()); break;
                case 6: exit = true; break;
                default: System.out.println("Invalid option");
            }
        }
        System.out.println("Goodbye!");
    }

    private static Admin login() {
        System.out.print("Enter admin ID: ");
        int id = readInt();
        System.out.print("Enter password: ");
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
                System.out.print("Please enter a number: ");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1) Manage Clients");
        System.out.println("2) Manage Books");
        System.out.println("3) Manage Loans & Reservations");
        System.out.println("4) View Historical Data");
        System.out.println("5) General Report");
        System.out.println("6) Exit");
        System.out.print("Select an option: ");
    }

    // Client sub‑menu
    private static void clientMenu() {
        while(true) {
            System.out.println("\n[Client Menu]");
            System.out.println("1) List Clients");
            System.out.println("2) Add Client");
            System.out.println("3) Delete Client");
            System.out.println("4) Update Client");
            System.out.println("5) View Fines (Multas)");
            System.out.println("6) Back");
            System.out.print("Select: ");
            int opt = readInt();
            switch (opt) {
                case 1: listClients(); break;
                case 2: addClient(); break;
                case 3: deleteClient(); break;
                case 4: updateClient(); break;
                case 5: viewFines(); break;
                case 6: return;
                default: System.out.println("Invalid");
            }
        }
    }

    private static void listClients() {
        List<Cliente> data = Persistencia.getInstancia().getClientes();
        if (data.isEmpty()) {
            System.out.println("No clients.");
            return;
        }
        for (Cliente c : data) {
            System.out.printf("Cedula: %s | Nombre: %s | Estado: %d | Multas: $%.2f%n", 
                c.getCedula(), c.getNombre(), c.getEstado(), c.getMultas());
        }
    }

    private static void addClient() {
        System.out.print("Cedula: "); String ced = scanner.nextLine();
        System.out.print("Nombre: "); String nom = scanner.nextLine();
        System.out.print("Telefono: "); String tel = scanner.nextLine();
        System.out.print("Direccion: "); String dir = scanner.nextLine();
        boolean ok = clienteCtrl.agregarCliente(ced, nom, tel, dir);
        System.out.println(ok ? "Client added" : "Client already exists");
    }

    private static void deleteClient() {
        System.out.print("Cedula to delete: "); String ced = scanner.nextLine();
        boolean ok = clienteCtrl.eliminarCliente(ced);
        System.out.println(ok ? "Deleted" : "Not found");
    }

    private static void updateClient() {
        System.out.print("Cedula to update: "); String ced = scanner.nextLine();
        Cliente c = clienteCtrl.buscarCliente(ced);
        if (c == null) { System.out.println("Not found"); return; }
        System.out.print("Nuevo nombre (" + c.getNombre() + "): "); String nom = scanner.nextLine();
        System.out.print("Nuevo telefono (" + c.getTelefono() + "): "); String tel = scanner.nextLine();
        if (!nom.isEmpty()) c.setNombre(nom);
        if (!tel.isEmpty()) c.setTelefono(tel);
        boolean ok = clienteCtrl.modificarCliente(c);
        System.out.println(ok ? "Updated" : "Update failed");
    }

    private static void viewFines() {
        System.out.print("Cedula del cliente: "); String ced = scanner.nextLine();
        Cliente c = clienteCtrl.buscarCliente(ced);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }
        System.out.printf("Total multas pendientes para %s: $%.2f%n", c.getNombre(), c.getMultas());
    }

    // Book sub‑menu
    private static void bookMenu() {
        while(true) {
            System.out.println("\n[Book Menu]");
            System.out.println("1) List Books");
            System.out.println("2) Add Book");
            System.out.println("3) Delete Book");
            System.out.println("4) Search (fuzzy)");
            System.out.println("5) Back");
            System.out.print("Select: ");
            int opt = readInt();
            switch (opt) {
                case 1: listBooks(); break;
                case 2: addBook(); break;
                case 3: deleteBook(); break;
                case 4: fuzzySearch(); break;
                case 5: return;
                default: System.out.println("Invalid");
            }
        }
    }

    private static void listBooks() {
        List<Libro> list = Persistencia.getInstancia().getLibros();
        if (list.isEmpty()) { System.out.println("No books."); return; }
        for (Libro l : list) {
            System.out.printf("%d - %s - %s - Estado:%d (0:Disp, 1:Res, 2:Retirado) - Usos:%d%n",
                l.getId(), l.getTitulo(), l.getAutor(), l.getEstado(), l.getUsos());
        }
    }

    private static void addBook() {
        System.out.print("ID: "); int id = readInt();
        System.out.print("Titulo: "); String tit = scanner.nextLine();
        System.out.print("Autor: "); String aut = scanner.nextLine();
        System.out.print("Categoria: "); String cat = scanner.nextLine();
        Libro lib = new Libro(id, tit, aut, "EAM", 2024, cat, 0);
        boolean ok = libroCtrl.agregarLibro(lib);
        System.out.println(ok ? "Book added" : "Duplicate ID");
    }

    private static void deleteBook() {
        System.out.print("ID to delete: "); int id = readInt();
        boolean ok = libroCtrl.eliminarLibro(id);
        System.out.println(ok ? "Deleted" : "Not found");
    }

    private static void fuzzySearch() {
        System.out.print("Enter search term: ");
        String term = scanner.nextLine();
        List<Libro> results = libroCtrl.busquedaFuzzy(term);
        if (results.isEmpty()) { System.out.println("No matches."); return; }
        for (Libro l : results) {
            System.out.printf("%d - %s - %s%n", l.getId(), l.getTitulo(), l.getAutor());
        }
    }

    // Reservation sub‑menu
    private static void reservationMenu() {
        while(true) {
            System.out.println("\n[Loan & Reservation Menu]");
            System.out.println("1) List Active Loans");
            System.out.println("2) Process New Loan (Prestamo)");
            System.out.println("3) Register Waiting List (Reserva)");
            System.out.println("4) Process Return (Devolución)");
            System.out.println("5) View Waiting List for a Book");
            System.out.println("6) Back");
            System.out.print("Select: ");
            int opt = readInt();
            switch (opt) {
                case 1: listLoans(); break;
                case 2: addLoan(); break;
                case 3: addWaitingList(); break;
                case 4: processReturn(); break;
                case 5: viewWaitingList(); break;
                case 6: return;
                default: System.out.println("Invalid");
            }
        }
    }

    private static void listLoans() {
        List<Reserva> list = Persistencia.getInstancia().getReservas();
        if (list.isEmpty()) { System.out.println("No active loans."); return; }
        for (Reserva r : list) {
            System.out.printf("ID:%d - Cliente:%s (%s) - Libro:%s - Vence:%s%n",
                r.getId(), r.getCliente().getCedula(), r.getCliente().getNombre(), 
                r.getLibro().getTitulo(), r.getFechaLimite());
        }
    }

    private static void addLoan() {
        System.out.print("Cedula cliente: "); String ced = scanner.nextLine();
        Cliente cli = clienteCtrl.buscarCliente(ced);
        if (cli == null) { System.out.println("Cliente no encontrado."); return; }
        System.out.print("ID Libro: "); int idLibro = readInt();
        Libro lib = libroCtrl.buscarLibro(idLibro);
        if (lib == null) { System.out.println("Libro no encontrado."); return; }
        
        boolean ok = reservaCtrl.agregarReserva(cli, lib);
        if (ok) System.out.println("Prestamo registrado exitosamente.");
        else System.out.println("No se pudo registrar. Verifique que el libro esté disponible y el cliente no tenga otro préstamo.");
    }

    private static void addWaitingList() {
        System.out.print("Cedula cliente: "); String ced = scanner.nextLine();
        Cliente cli = clienteCtrl.buscarCliente(ced);
        if (cli == null) { System.out.println("Cliente no encontrado."); return; }
        System.out.print("ID Libro a reservar: "); int idLibro = readInt();
        Libro lib = libroCtrl.buscarLibro(idLibro);
        if (lib == null) { System.out.println("Libro no encontrado."); return; }
        
        boolean ok = reservaCtrl.registrarReservaEnCola(cli, lib);
        System.out.println(ok ? "Cliente añadido a la lista de espera." : "No se pudo añadir.");
    }

    private static void processReturn() {
        System.out.print("ID del prestamo a devolver: "); int id = readInt();
        boolean ok = reservaCtrl.eliminarReserva(id);
        System.out.println(ok ? "Devolución procesada." : "ID de préstamo no encontrado.");
    }

    private static void viewWaitingList() {
        System.out.print("ID Libro: "); int idLibro = readInt();
        Libro lib = libroCtrl.buscarLibro(idLibro);
        if (lib == null) { System.out.println("Libro no encontrado."); return; }
        
        List<Cliente> espera = lib.getListaEspera();
        if (espera.isEmpty()) {
            System.out.println("No hay nadie en espera para este libro.");
        } else {
            System.out.println("Clientes en espera:");
            for (int i = 0; i < espera.size(); i++) {
                System.out.println((i+1) + ") " + espera.get(i).getNombre());
            }
        }
    }

    private static void historialMenu() {
        System.out.print("Cedula cliente: "); String ced = scanner.nextLine();
        List<Libro> historial = historialCtrl.historialPorCliente(ced);
        if (historial == null || historial.isEmpty()) {
            System.out.println("No history for this client.");
            return;
        }
        System.out.println("Historical Books Borrowed:");
        for (Libro l : historial) {
            System.out.println("- " + l.getTitulo());
        }
    }
}
