/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public class Texturepacks {
    public static List<Texturepack> getTexturePacks(String path) {
        System.out.println("LOAD TP: " + path);
        List<Texturepack> lst = new ArrayList<Texturepack>();
        
        File f = new File(path);
            
        System.out.println(" -> " + f.getAbsolutePath());
        if (f.exists() && f.isDirectory()) {
            String []filesAndDirs = f.list();
            for (int i = 0; i < filesAndDirs.length; i++) {
                File ff = new File(f.getAbsolutePath(), filesAndDirs[i]);
                System.out.println("::" + ff.getAbsolutePath());
                System.out.println("  ="+ff.isDirectory());
                if (ff.isDirectory()) {
                    lst.add(new Texturepack(ff));
                }
            }
            
        }
        
        return lst;
    }
    
}
