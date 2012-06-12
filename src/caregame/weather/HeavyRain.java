/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.weather;

/**
 *
 * @author Markus
 */
public class HeavyRain extends Weather {

    public HeavyRain(int id) {
        super(id);
    }

    
    
    @Override
    public float getRainAmount() {
        return 5.0f;
    }

    @Override
    public String toString() {
        return "HEAVY RAIN";
    }

    
    
}
