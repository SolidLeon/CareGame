/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public interface ListItem {
    public void renderInventory(Graphics g, int x, int y);
    public int getWidth();
}
