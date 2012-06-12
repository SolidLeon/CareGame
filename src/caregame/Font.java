/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class Font {
    private static String[] charset = {
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ      ",
        "0123456789.,!?'\"-+=/\\%()<>:;    "
    };
    public static void render(Graphics g, String text, int x, int y) {
        text = text.toUpperCase();
        int tx;
        int ty; //first row
        Sprite s = ImageCache.get().get("font.png");
        for (int i = 0; i < text.length(); i++) {
            int idx = charset[0].indexOf(text.charAt(i));
            ty = 0;
            if (idx == -1) {
                idx = charset[1].indexOf(text.charAt(i));
                if (idx == -1) continue;
                ty = 8;
            }
            tx=idx*8;
            s.render(g, x+8*i, y, x+8*i+8, y+8, tx, ty, tx+8, ty+8);
        }
    }

    public static int getLineHeight() {
        return 16;
    }

    public static int getWidth(String s) {
        return 8*s.length();
    }
}
