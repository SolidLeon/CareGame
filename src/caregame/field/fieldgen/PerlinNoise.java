/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field.fieldgen;

import java.util.Random;

/**
 *
 * @author Markus
 */
public class PerlinNoise {
    private short []p;
    private float []gx;
    private float []gy;

    private int size;
    private Random random;

    public PerlinNoise(int size, long seed) {
        this.size = size;
        p = new short[size];
        gx = new float[size];
        gy = new float[size];
        random = new Random(seed);
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < size; i++) {
            p[i] = (short)(i&0xFF);
        }

        for (int i = 0; i < size; i++) {
            int j = random.nextInt() & 0x0FFF;
            int nSwap = p[i];
            p[i] = p[j];
            p[j] = (short) (nSwap&0xFF);
        }

        //Generate gradiant lookup tables
        for (int i = 0; i < size; i++) {
            gx[i] = ((float)(random.nextInt())) / (Integer.MAX_VALUE / 2) - 1.0f;
            gy[i] = ((float)(random.nextInt())) / (Integer.MAX_VALUE / 2) - 1.0f;
        }
    }

    public float pn1d(float x) {
        int qx0 = (int) Math.floor(x);
        int qx1 = qx0 + 1;
        float tx0 = x - (float) qx0;
        float tx1 = tx0 - 1;

        qx0 = qx0 & 0x0FFF;
        qx1 = qx1 & 0x0FFF;

        float v0 = gx[qx0] * tx0;
        float v1 = gx[qx1] * tx1;

        float wx = (3-2*tx0)*tx0*tx0;
        float v = v0 - wx * (v0 - v1);
        return v;
    }

    public float pn2d(float x, float y) {
//        System.out.println("PN2D: " + x + "/" + y);
        int qx0 = (int) Math.floor(x);
        int qx1 = qx0 + 1;
        float tx0 = x - (float) qx0;
        float tx1 = tx0 - 1;

        int qy0 = (int) Math.floor(y);
        int qy1 = qy0 + 1;
        float ty0 = y - (float) qy0;
        float ty1 = ty0 - 1;

        qx0 = qx0 & 0x0FFF;
        qx1 = qx1 & 0x0FFF;

        qy0 = qy0 & 0x0FFF;
        qy1 = qy1 & 0x0FFF;

        //permute
        int q00 = p[(qy0 + p[qx0]) & 0x0FFF];
        int q01 = p[(qy0 + p[qx1]) & 0x0FFF];

        int q10 = p[(qy1 + p[qx0]) & 0x0FFF];
        int q11 = p[(qy1 + p[qx1]) & 0x0FFF];

        //compute dot-product
        float v00 = gx[q00] * tx0 + gy[q00] * ty0;
        float v01 = gx[q01] * tx1 + gy[q01] * ty0;

        float v10 = gx[q10] * tx0 + gy[q10] * ty1;
        float v11 = gx[q11] * tx1 + gy[q11] * ty1;

        //Modulate with weight function
        float wx = (3.0f - 2.0f * tx0) * tx0 * tx0;
        float v0 = v00 - wx * (v00 - v01);
        float v1 = v10 - wx * (v10 - v11);

        float wy = (3.0f - 2.0f * ty0) * ty0 * ty0;
        float v = v0 - wy * (v0 - v1);

//        System.out.println("--> V: " + v);
        return v;
    }
}
