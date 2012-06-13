/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.weather;

/**
 *
 * @author Markus
 */
public class Snow extends Weather {

    public Snow(int id) {
        super(id);
    }
    

    @Override
    public boolean isRaining() {
        return true;
    }
}
