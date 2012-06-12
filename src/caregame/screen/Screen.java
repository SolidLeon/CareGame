/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 *
 * @author Markus
 */
public class Screen {
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    protected Game game;
    protected InputHandler input;
    
    public void init(Game game, InputHandler input) {
        this.game = game;
        this.input = input;
    }
    
    public void render(Graphics g) {
        
    }

    public void tick() {
        
    }

    public boolean isPausingGame() {
        return false;
    }
    
    public static void renderFrame(Graphics g, int x, int y, int width, int lines) {
        //corners 11x11 pixel
        Sprite s = ImageCache.get().get("menu.png");
        int lh = Font.getLineHeight();
        
        g.drawImage(s.img, x, y, x+11, y+11, 0, 0, 11, 11, null); //left top corner
        g.drawImage(s.img, x+11, y, x+width-11, y+11, 11, 0, 53, 11, null); //top
        g.drawImage(s.img, x+width-11, y, x+width, y+11, 53, 0, 64, 11, null); //right top corner
//        
        g.drawImage(s.img, x, y+11, x+11, y+11+lines*lh, 0, 11, 11, 53, null); //left side
        g.drawImage(s.img, x+11, y+11, x+width-11, y+11+lines*lh, 11, 11, 27, 27, null); //center
        g.drawImage(s.img, x+width-11, y+11, x+width, y+11+lines*lh, 53, 11, 64, 27, null); //right side
//        
//        
        g.drawImage(s.img, x, y+11+lh*lines, x+11, y+11+lh*lines+11, 0, 53, 11, 64, null); //left bottom corner
        g.drawImage(s.img, x+11, y+11+lh*lines, x+width-11, y+11+lh*lines+11, 11, 53, 53, 64, null); //bottom
        g.drawImage(s.img, x+width-11, y+11+lh*lines, x+width, y+11+lh*lines+11, 53, 53, 64, 64, null); //right bottom corner
    }
    
    public static void renderItems(Graphics g, int frameX, int frameY, int frameWidth, int alignment, int selection, List<? extends ListItem> list) {
        int offset = (selection / 10) * 10;
        int limit = offset + 10;
        if (limit > list.size()) limit = list.size();
        
        
        renderFrame(g, frameX, frameY, frameWidth, limit-offset);
        int ox = frameX + 11 + Font.getWidth("> ");
        Font.render(g, offset + "/"+limit+"/"+list.size(), 0, 0);
        for (int i = offset; i < limit; i++) {
            int ww = list.get(i).getWidth();
            int x = ox;
            int y = frameY + 11 + Font.getLineHeight()*(i-offset)  + (Font.getLineHeight() - 8) / 2;
            if (alignment == ALIGN_CENTER) {
                x = frameX + frameWidth / 2 - ww / 2;
            }
            if (alignment == ALIGN_RIGHT) {
                x = frameX + frameWidth - ww - 11;
            }
            if (selection == i) {
                Font.render(g, ">", frameX + 11, y);
                Font.render(g, "<", frameX + frameWidth - 11, y);
            }
            list.get(i).renderInventory(g, x, y);
        }
    }
    
    public static void renderItems(Graphics g, int frameX, int frameY, int frameWidth, int alignment, List<? extends ListItem> list) {
        int limit = 10;
        if (limit > list.size()) limit = list.size();
        
        renderFrame(g, frameX, frameY, frameWidth, limit);
        int ox = frameX + 11 + Font.getWidth("> ");
        for (int i = 0; i < limit; i++) {
            int ww = list.get(i).getWidth();
            int x = ox;
            int y = frameY + 11 + Font.getLineHeight()*i  + (Font.getLineHeight() - 8) / 2;
            if (alignment == ALIGN_CENTER) {
                x = frameX + frameWidth / 2 - ww / 2;
            }
            if (alignment == ALIGN_RIGHT) {
                x = frameX + frameWidth - ww - 11;
            }
            list.get(i).renderInventory(g, x, y);
        }
    }
    
    public static void renderFrameText(Graphics g, int frameX, int frameY, int frameWidth, int alignment, int selection, String ...lines) {
        renderFrame(g, frameX, frameY, frameWidth, lines.length);
        int oy = frameY + 11;
        for (int i = 0; i < lines.length; i++) {
            String text = lines[i];
            int x = frameX + 11 + Font.getWidth("> ");;
            int y = oy + Font.getLineHeight()*i + (Font.getLineHeight()-8)/2;
            if (alignment == ALIGN_CENTER) {
                x = frameX + frameWidth / 2 - Font.getWidth(text) / 2;
            }
            if (alignment == ALIGN_RIGHT) {
                x = frameX + frameWidth - Font.getWidth(text) - 11;
            }
            if (selection == i) {
                Font.render(g, ">", frameX + 11, y);
                Font.render(g, "<", frameX + frameWidth - 11, y);
            }
            Font.render(g, text, x, y);
        }
    }
    
    public static void renderFrameText(Graphics g, int frameX, int frameY, int frameWidth, int alignment, String ...lines) {
        renderFrame(g, frameX, frameY, frameWidth, lines.length);
        for (int i = 0; i < lines.length; i++) {
            String text = lines[i];
            int x = frameX + 11;
            int y = frameY + 11 + Font.getLineHeight()*i + (Font.getLineHeight()-8)/2;
            if (alignment == ALIGN_CENTER) {
                x = frameX + frameWidth / 2 - Font.getWidth(text) / 2;
            }
            if (alignment == ALIGN_RIGHT) {
                x = frameX + frameWidth - Font.getWidth(text) - 11;
            }
            Font.render(g, text, x, y);
        }
    }
    
    public static int getFrameHeight(int lines) {
        return 22 + lines * Font.getLineHeight();
    }
}
