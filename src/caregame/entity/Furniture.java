/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.Game;
import caregame.ImageCache;
import caregame.Sprite;
import caregame.item.FurnitureItem;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class Furniture extends Entity{
    public String name;
    public String sprite;
    private int pushTime;
    private int pushDir;

    public Furniture(String name) {
        this.name = name;
        xr = 16;
        yr = 12;
    }

    @Override
    public void tick() {
        if (pushDir == Player.DIR_WEST) move(-1, 0);
        if (pushDir == Player.DIR_EAST) move(1, 0);
        if (pushDir == Player.DIR_SOUTH) move(0, 1);
        if (pushDir == Player.DIR_NORTH) move(0, -1);
        pushDir = -1;
        if (pushTime > 0) pushTime--;
    }
    
    

    @Override
    public boolean blocks(Entity e) {
        return true;
    }

    @Override
    protected void touchedBy(Entity e) {
        if (e instanceof Player && pushTime == 0) {
            pushDir = ((Player) e).dir;
            pushTime = 10;
        }
    }
    
    
    

    @Override
    public void render(Graphics g) {
        Sprite s = ImageCache.get().get(sprite);
        s.render(g, x-xr, y+yr-s.getHeight());
        if (Game.DEBUG) {
            g.drawRect(x-xr, y-yr, xr+xr, yr+yr);
            g.drawLine(x-xr, y-yr, x + xr, y + yr);
            g.drawLine(x-xr,y+yr,x+xr,y-yr);
        }
    }

    
    
    @Override
    public void write(DataOutputStream out) throws IOException {
//        out.writeInt(OPCODES.OP_ENTITY_FURNITURE);
        super.write(out); //x,y
        out.writeUTF(name);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        super.read(in);
        name = in.readUTF();
    }

    @Override
    public void hurt(Creature c, int dmg, int attackDir) {
        remove();
        field.add(new ItemEntity(new FurnitureItem(this), x + random.nextInt(10) + 3, y + random.nextInt(10) + 3));
    }
    
}
