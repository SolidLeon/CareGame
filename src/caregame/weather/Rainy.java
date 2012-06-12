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
    public float getRainAmount() {
        return 0.05f;
    }
    @Override
    public String toString() {
        return "RAINY";
    }
    
    
    
    
}
