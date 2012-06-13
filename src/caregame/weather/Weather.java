/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class Weather {
    public static final Weather[] weathers = new Weather[3];
    private static final Random random = new Random();
    
    public static final Weather sunny = new Sunny(0);
    public static final Weather rainy = new Rainy(1);
    public static final Weather snow = new Snow(2);

    public static Weather getRandomWeather() {
        return weathers[random.nextInt(weathers.length)];
    }
    
    public final byte id;
    
    public Weather(int id) {
        this.id = (byte) id;
        weathers[id] = this;
    }
    
    public String getName() {
        return "";
    }
    
    public boolean isRaining() {
        return false;
    }
    
}
