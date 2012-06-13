/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.weather;

/**
 *
 * @author Markus
 */
public class Rainy extends Weather {

    public Rainy(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "RAINY";
    }

    @Override
    public boolean isRaining() {
        return true;
    }
    
    
    
    
}
