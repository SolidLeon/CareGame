/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item;

import caregame.Font;
import caregame.ImageCache;
import caregame.OPCODES;
import caregame.Sprite;
import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;
import caregame.item.resource.Resource;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class ResourceItem extends Item {
    public Resource resource;
    public int count;

    public ResourceItem(Resource resource, int count) {
        this.resource = resource;
        this.count = count;
    }

    public ResourceItem(Resource resource) {
        this(resource, 1);
    }
    
    @Override
    public String getName() {
        return resource.name;
    }

    @Override
    public void renderInventory(Graphics g, int x, int y) {
        getSprite().render(g, x, y-4, 16, 16);
        if (count > 999) {
            Font.render(g, String.format("%02d+", 99), x+24, y);
        } else {
            Font.render(g, String.format("%03d", count), x+24, y);
        }
        Font.render(g, getName(), x+32+Font.getWidth("000"), y);
    }

    @Override
    public Sprite getSprite() {
        return ImageCache.get().get(resource.sprite);
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(OPCODES.OP_ITEM_RESOURCE);//item type - resource item
        out.writeByte(resource.id);
        out.writeInt(count);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        //type field must be read before already, to detect that we read a RESORUCE ITEM
        this.resource = Resource.resources[in.readByte()];
        count = in.readInt();
    }

    @Override
    public boolean isDepleted() {
        return count == 0;
    }
    
    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        System.out.println("Interact On: " + resource.getClass().getSimpleName() + ":" + resource.name);
        if (resource.interactOn(tile, field, xt, yt, player, attackDir)) {
            count--;
            return true;
        }
        return false;
    }
    
}
