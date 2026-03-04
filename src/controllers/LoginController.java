
package controllers;

import model.Admin;


/**
 *
 * @author jhnf
 */
public class LoginController {

    public LoginController() {
    }
 
    public Admin login(int id, String password){
        Admin admin = new Admin();
        if (admin.getId() == id && admin.getPassword().equals(password))
            return admin;     
        return null;
    } 
}
