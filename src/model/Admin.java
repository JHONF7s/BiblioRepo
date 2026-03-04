
package model;

/**
 *
 * @author jhnf
 */
public class Admin {
    private int id;
    private String name;
    private final String password = "123";
    
    public Admin(){
        id = 123;
        name = "Admin";                      
    }    
    public int getId() {return id;}
    public String getName() {return name;}
    public String getPassword() {return password;}
}
