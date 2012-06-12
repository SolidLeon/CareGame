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
public class LevelTransitionScreen extends Screen {
    private int dir;
    private int time = 0;

    public LevelTransitionScreen(int dir) {
        this.dir = dir;
    }

    @Override
    public void tick() {
        time += 2;
        if (time == 30) game.changeLevel(dir);
        if (time == 60) game.setScreen(null);
    }

    @Override
    public void render(Graphics g) {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                int dd = (y+x%2 * 2 + x / 3) - time;
                if (dd < 0 && dd > -30) {
                    if (dir > 0) {
                        g.fillRect(x*8, y*8, 8, 8);
                    } else {
                        g.fillRect(x*8, game.getWidth() - y * 8 - 8, 8, 8);
                    }
                }
            }
        }
    }
    
    
    
}
