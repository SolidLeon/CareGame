/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import caregame.entity.*;
import caregame.field.*;
import caregame.item.FurnitureItem;
import caregame.item.Item;
import caregame.item.ResourceItem;
import caregame.item.ToolItem;
import caregame.item.ToolType;
import caregame.item.resource.Resource;
import caregame.screen.ListItem;
import caregame.weather.Weather;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public class Savegame implements ListItem {
    
    public static List<Savegame> getSavegamesPacks() {
        String path = "saves/";
        List<Savegame> lst = new ArrayList<Savegame>();
        
        File f = new File(path);
            
        System.out.println(" -> " + f.getAbsolutePath());
        if (f.exists() && f.isDirectory()) {
            File []files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File ff = files[i];
                System.out.println("::" + ff.getAbsolutePath());
                System.out.println("  ="+ff.isDirectory());
                if (ff.isFile()) {
                    lst.add(new Savegame(ff.getName().substring(0, ff.getName().length()-4)));//truncate .dat
                }
            }
            
        }
        
        return lst;
    }
    private DataOutputStream out;
    private DataInputStream in;
    private Game game;

    public String title;

    public Savegame(String title) {
        this.title = title;
    }
        
    public boolean save(Game game) throws IOException {
        this.game = game;
        File fS = new File("saves/");
        if (!fS.exists()) fS.mkdirs();
        File f = new File("saves/" + title + ".dat");
        if (!f.exists()){
            f.createNewFile();
        }
        System.out.println("SAVE TO: " + f.getAbsolutePath());
        out = new DataOutputStream(new FileOutputStream(f));
        
        saveHeader();
        saveInventory();
        saveField();
        saveFinish();
        return true;
    }
    public boolean saveHeader() throws IOException {
        if (out == null) return false;
        out.writeInt(Game.TILE_SIZE);
//        out.writeInt(game.cx);
//        out.writeInt(game.cy);
//        out.writeBoolean(game.selectedItem != null);
//        if (game.selectedItem != null) {
//            game.selectedItem.write(out);
//        }
        out.writeByte(game.weather.id);
        return true;
    }
    
    public boolean saveField() throws IOException {
        if (out == null) return false;
        
        /*
         * OPC
         * dw:width
         * dw:height
         * w*h:
         *  Field.write
         * dw:entities.size
         * entities.size:
         *  Entity.write
         */
        out.writeInt(OPCODES.OP_FIELD);
        out.writeInt(game.field.width);
        out.writeInt(game.field.height);
        for (int i = 0; i < game.field.field.length; i++) {
//            game.field.field[i].write(out);
        }
        out.writeInt(game.field.entities.size());
        for (int i = 0; i < game.field.entities.size(); i++) {
            game.field.entities.get(i).write(out);
        }
        
        return true;
    }
    public boolean saveInventory() throws IOException {
        if (out == null) return false;
//        game.inventory.write(out);
        
        
        return true;
    }
    
    public boolean saveFinish() throws IOException {
        if (out != null) {
            out.flush();
            out.close();
            return true;
        }
        return false;
    }
    
    public boolean load(Game game) throws IOException {
        this.game = game;
        File f = new File("saves/" + title + ".dat");
        System.out.println("LOAD: " + f.getAbsolutePath());
        if (!f.exists()){
            return false;
        }
        in = new DataInputStream(new FileInputStream(f));
        
        loadHeader();
        loadInventory();
        loadField();
        loadFinish();
        return true;
    }
    
    public boolean loadFinish() throws IOException {
        if (in == null) return false;
        in.close();
        return true;
    }
    
    
    public boolean loadHeader() throws IOException {
        Game.TILE_SIZE = in.readInt();
//        game.cx = in.readInt();
//        game.cy = in.readInt();
//        boolean hasSelectedItem = in.readBoolean();
//        if (hasSelectedItem) {
//            game.selectedItem = readItem();
//        }
        game.weather = Weather.weathers[in.readByte()];
        System.out.println("LOADED HEADER");
//        System.out.println(" c: " + game.cx + ", " + game.cy);
        return true;
    }
    
    public boolean loadInventory() throws IOException {
        if (in == null) return false;
        in.readInt();//opc
//        game.inventory.read(in);
        return true;
    }
        /*
         * OPC
         * dw:width
         * dw:height
         * w*h:
         *  Field.write
         * dw:entities.size
         * entities.size:
         *  Entity.write
         */
    public boolean loadField() throws IOException {
        if (in == null) return false;
        in.readInt(); //opc
        int w = in.readInt();
        int h = in.readInt();
        for (int i = 0; i < game.field.field.length; i++) {
            Tile t = null;
            int tileOpc = in.readInt();
            switch (tileOpc) {
//                case OPCODES.OP_TILE_FARM: t = new FarmTile(); break;
//                case OPCODES.OP_TILE_GRASS: t = new GrassTile(); break;
//                case OPCODES.OP_TILE_SEED: t = new SeedTile(null); break;
//                case OPCODES.OP_TILE_TREE: t = new TreeTile(); break;
//                case OPCODES.OP_TILE_WATER: t = new WaterTile(); break;
//                case OPCODES.OP_TILE_WOOD_PLANKS: t = new WoodPlanksTile(); break;
//                case OPCODES.OP_TILE_INVISIBLE_WALL: t = new InvisibleWallTile(); break;
                default:
                    throw new RuntimeException("ERROR: Unexpected OpCode for TILE: " + tileOpc);
            }
//            t.read(in);
//            game.field.field[i] = t;
        }
        int entities = in.readInt();
        System.out.println("LOAD entities: " + entities);
        for (int i = 0; i < entities; i++) {
            Entity e = null;
            int opc = in.readInt();
            switch (opc) {
                case OPCODES.OP_ENTITY_BAG: e = new Bag(); break;
                case OPCODES.OP_ENTITY_FURNITURE: e = new Furniture(""); break;
                case OPCODES.OP_ENTITY_WORKBENCH: e = new Workbench(); break;
//                case OPCODES.OP_ENTITY_PLAYER: e = new Player(game); break;
                default: continue;
            }
            e.read(in);
            game.field.add(e);
        }
        return true; 
    }
    
    private Item readItem() throws IOException {
        Item item = null;
        int type = in.readInt();
        
        switch (type) {
            case OPCODES.OP_ITEM_FURNITURE: 
                item = new FurnitureItem(null);
                item.read(in);
                break;
            case OPCODES.OP_ITEM_RESOURCE:
                item = new ResourceItem(Resource.wood);
                item.read(in);
                break;
            case OPCODES.OP_ITEM_TOOL:
                item = new ToolItem(ToolType.hoe, 0);
                item.read(in);
                break;
        }
        
        
        return item;
    }

    @Override
    public void renderInventory(Graphics g, int x, int y) {
        Font.render(g, title, x, y);
    }

    @Override
    public int getWidth() {
        return Font.getWidth(title);
    }
}
