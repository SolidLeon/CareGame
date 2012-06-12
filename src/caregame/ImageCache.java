/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Markus
 */
public class ImageCache {
    private static ImageCache instance = new ImageCache();
    
    public static ImageCache get() {
        return instance;
    }
    
    private ImageCache() {}
    
    private Map<String, Sprite> sprites = new HashMap<String, Sprite>();
    
    private String texturePackPath = null;

    public void setTexturePackPath(String texturePackPath) {
        this.texturePackPath = texturePackPath;
        sprites.clear();
    }

    public String getTexturePackPath() {
        return texturePackPath;
    }
    
    
    
    public Sprite get(String ref) {
        if (sprites.containsKey(ref)) {
            return sprites.get(ref);
        }
        try {
            System.out.println("REF: " +ref);
            System.out.println("TPP: " + texturePackPath);
            BufferedImage img;
            File f = new File(texturePackPath, ref);
            if (texturePackPath == null || texturePackPath.isEmpty() || !f.exists() || !f.canRead())
                img = ImageIO.read(ImageCache.class.getResource("/caregame/res/" + ref));
            else{
                img = ImageIO.read(f);
            }
            if (img != null) {
                Sprite s = new Sprite(img);
                sprites.put(ref, s);
                return s;
            }
        } catch (Exception ex) {
            System.out.println("GET IMG: " + texturePackPath + ref);
            Logger.getLogger(ImageCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
