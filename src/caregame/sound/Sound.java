/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.sound;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author Markus
 */
public class Sound {
    
    public static final Sound craft = new Sound("/caregame/res/craft.wav");
    
    private AudioClip clip;
    
    private Sound(String name) {
        clip = Applet.newAudioClip(Sound.class.getResource(name));
    }

    public void play() {
        new Thread() {

            @Override
            public void run() {
                clip.play();
            }
            
        }.start(); 
    }
}
