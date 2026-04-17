package controllers;

import model.Cliente;
import resources.data.Persistencia;
import java.util.ArrayList;

public class ClienteController {
    private ArrayList<Cliente> clientes;

    public ClienteController() {
        this.clientes = Persistencia.getInstancia().getClientes();
    }

    public boolean agregarCliente(Cliente cliente){
        if (buscarCliente(cliente.getCedula()) != null) return false;
        boolean respuesta = clientes.add(cliente);
        Persistencia.getInstancia().writeClientes();
        return respuesta;
    }

    public Cliente buscarCliente(String cedula){
        for(Cliente cliente : clientes){
            if(cliente.getCedula().equals(cedula)){
                return cliente;
            }
        }
        return null;
    }

    public ArrayList<Cliente> getListaClientes() {
        return clientes;
    }
}

