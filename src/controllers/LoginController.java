
package controllers;


/**
 *
 * @author jhnf
 */
public class LoginController {
    private final String password = "123";
    private final int id = 123;

    public LoginController() {
    }
 
    public boolean login(int id, String password){
        return (id == this.id && password.equals(this.password));            
    } 
}
