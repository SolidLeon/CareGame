/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field.biome;

import caregame.weather.Weather;

/**
 *
 * @author Markus
 */
public class ForestBiome extends Biome {
    
    public Weather getRandomWeather() {
        int r = random.nextInt(2);
        if (r == 0) return Weather.sunny;
        if (r == 1) return Weather.rainy;
        return null;
    }
    
}
