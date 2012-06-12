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
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class ToolItem extends Item {
    
    public static final int MAX_LEVEL = 5;
    public static final String[] LEVEL_NAMES = { //
        "Wood", "Rock", "Iron", "Gold", "Gem"//
    };
    
    public ToolType type;
    public int level = 0;

    public ToolItem(ToolType type, int level) {
        this.type = type;
        this.level = level;
    }

    @Override
    public String getName() {
        return LEVEL_NAMES[level] + " " + type.name;
    }

    @Override
    public boolean matches(Item item) {
        if (item instanceof ToolItem) {
            ToolItem other = (ToolItem) item;
            if (other.type != type) return false;
            if (other.level != level) return false;
            return true;
        }
        return false;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(OPCODES.OP_ITEM_TOOL);
        out.writeByte(type.id);
        out.writeByte(level);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        type = ToolType.types[in.readByte()];
        level = in.readByte();
    }

    @Override
    public Sprite getSprite() {
        return ImageCache.get().get("tools.png");
    }
    
    @Override
    public void renderIcon(Graphics g, int x, int y) {
        getSprite().render(g, x, y, x+16, y+16, 8*type.sprite, 0, 8*type.sprite+8, 8);
    }

    @Override
    public void renderInventory(Graphics g, int x, int y) {
        getSprite().render(g, x, y, x+16, y+16, 8*type.sprite, 0, 8*type.sprite+8, 8);
        Font.render(g, "001 " + getName(), x+24, y);
    }

    @Override
    public boolean canAttack() {
        return true;
    }
    
    
    
}
