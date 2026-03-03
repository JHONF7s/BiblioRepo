package controllers;

import model.Cliente;

import java.util.ArrayList;

public class ClienteController {
    private ArrayList<Cliente> clientes;

    public ClienteController() {
        this.clientes = new ArrayList<>();
    }

    public boolean agregarCliente(int cedula, String nombre, int telefono, String direccion){
        for(Cliente cliente : clientes){
            if(cliente.getCedula() == cedula){
                return false;
            }
        }
        return clientes.add(new Cliente(cedula, nombre, telefono, direccion));
    }

    public boolean eliminarCliente(int cedula){
        for(Cliente cliente : clientes){
            if(cliente.getCedula() == cedula){
                clientes.remove(cliente);
                return true;
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
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(int cedula){
        for(Cliente cliente : clientes){
            if(cliente.getCedula() == cedula){
                return cliente;
            }
        }
        return null;
    }
}

