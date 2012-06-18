/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field.fieldgen;

import caregame.field.Tile;
import caregame.field.biome.Biome;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Markus
 */
public class FieldGen extends Canvas implements Runnable, KeyListener {

    private static PerlinNoise pn = new PerlinNoise(0x1000, 0);
    private static Random random = new Random(0);
    
    public static void initRandom(long seed) {
        random = new Random(seed);
    }
    
    public static byte[][] generateField(int sx, int sy, int level, Biome biome) {
        byte []map = new byte[sx*sy];
        byte []data = new byte[sx*sy];
        for (int y = 0; y < sy; y++) {
            for (int x = 0; x < sx; x++) {
//                float v = pn.pn2d(x + 0.05f, y + 0.05f);
                float v = (float) SimplexNoise.noise(x*0.02, y*0.02, level*0.02);
//                if( v < 0) v*=-1;
//                System.out.println("" + v);
                byte t = 0;
                if (v < -0.5) {
                    map[x+y*sx] = Tile.water.id;
                } else {
                    map[x+y*sx] = Tile.grass.id;
                }
            }
        }
        
        for (int i = 0; i < sx*sy / 400; i++) {
            int x = random.nextInt(sx);
            int y = random.nextInt(sy);
            for (int j = 0; j < 200; j++) {
                int xx = x + random.nextInt(15) - random.nextInt(15);
                int yy = y + random.nextInt(15) - random.nextInt(15);
                if (xx >= 0 && yy >= 0 && xx < sx && yy < sy) {
                    if (map[xx+yy*sx] == Tile.grass.id) {
                        map[xx+yy*sx] = Tile.tree.id;
                    }
                }
            }
        }
        
        return new byte[][]{map,data};
    }

    public static void main(String... args) {
        FieldGen test = new FieldGen();
        test.setMinimumSize(new Dimension(640, 480));
        test.setMaximumSize(new Dimension(640, 480));
        test.setPreferredSize(new Dimension(640, 480));
        test.addKeyListener(test);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(test, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        test.start();
    }
    
    private int level = 0;
    private double f = 0.02;

    private void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        createBufferStrategy(2);
        BufferStrategy bs = getBufferStrategy();
        while (true) {
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());

            for (int y = 0; y < 256; y++) {
                for (int x = 0; x < 256; x++) {
//                    float v = pn.pn2d(x+0.05f, y+0.05f);
//                    float v = pn.pn2d(x*0.99f, y*0.99f);
                    float v = (float) SimplexNoise.noise(x*f, y*f, level*f);
                    if (v < 0) v*=-1;
                    if (v > 1) v = 1;
                    g.setColor(new Color(v,v,v,1.0f));
                    g.fillRect(x*8,y*8,8,8);
                }
            }
            g.dispose();
            bs.show();
            try {
                Thread.sleep(4);
            } catch (InterruptedException ex) {
                Logger.getLogger(FieldGen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_Q) f += 0.01;
        if (ke.getKeyCode() == KeyEvent.VK_W) f -= 0.01;
        if (ke.getKeyCode() == KeyEvent.VK_1) level = 1;
        if (ke.getKeyCode() == KeyEvent.VK_2) level = 2;
        if (ke.getKeyCode() == KeyEvent.VK_3) level = 3;
        if (ke.getKeyCode() == KeyEvent.VK_4) level = 4;
        if (ke.getKeyCode() == KeyEvent.VK_5) level = 5;
        if (ke.getKeyCode() == KeyEvent.VK_6) level = 6;
        if (ke.getKeyCode() == KeyEvent.VK_7) level = 7;
        if (ke.getKeyCode() == KeyEvent.VK_8) level = 8;
        if (ke.getKeyCode() == KeyEvent.VK_9) level = 9;
        if (ke.getKeyCode() == KeyEvent.VK_0) level = 0;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    

}
