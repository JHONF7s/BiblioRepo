package controllers;

import model.Cliente;
import resources.data.Persistencia;
import java.util.ArrayList;

public class ClienteController {
    private ArrayList<Cliente> clientes;

    public ClienteController() {
        this.clientes = new ArrayList<>();
        clientes = (ArrayList<Cliente>) Persistencia.getInstancia().getClientes();
    }

    public boolean agregarCliente(String cedula, String nombre, String telefono, String direccion){
        for(Cliente cliente : clientes){
            if(cliente.getCedula().equals(cedula)){
                return false;
            }
        }
        boolean respuesta = clientes.add(new Cliente(cedula, nombre, telefono, direccion));
        Persistencia.getInstancia().writeClientes();
        return respuesta;
    }

    public boolean eliminarCliente(String cedula){
        for(Cliente cliente : clientes){
            if(cliente.getCedula().equals(cedula)){
                boolean respuesta = clientes.remove(cliente);
                Persistencia.getInstancia().writeClientes();
                return respuesta;
            }
        }
        return false;
    }


    public boolean modificarCliente(Cliente cliente){
        Cliente nuevoCliente = buscarCliente(cliente.getCedula());
        if(nuevoCliente != null){
            nuevoCliente.setNombre(cliente.getNombre());
            nuevoCliente.setTelefono(cliente.getTelefono());
            nuevoCliente.setDireccion(cliente.getDireccion());
            Persistencia.getInstancia().writeClientes();
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(String cedula){
        for(Cliente cliente : clientes){
            if(cliente.getCedula().equals(cedula)){
                return cliente;
            }
        }
        return null;
    }
}

