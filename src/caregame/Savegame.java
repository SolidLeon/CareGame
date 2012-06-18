/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import caregame.entity.*;
import caregame.field.*;
import caregame.field.biome.Biome;
import caregame.field.fieldgen.FieldGen;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        int numFields = 0;
        for (int i = 0; i < game.fields.length; i++) {
            if (game.fields[i] != null) numFields++;
        }
        GameField []fields = game.fields;
        out.writeInt(game.gameTime);
        out.writeByte(game.weather.id);
        out.writeByte(game.worldName.length());
        for (int i = 0; i < game.worldName.length(); i++) {
            out.writeByte(game.worldName.charAt(i));
        }
        out.writeInt(game.currentLevel);
        out.writeShort(fields.length);
        out.writeShort(numFields);
        System.out.println("Fields: " + numFields + "/" + fields.length);
        for (int i = 0; i < fields.length; i++) {
            GameField field = fields[i];
            if (field == null) continue;
            out.writeInt(field.width);
            out.writeInt(field.height);
            out.writeInt(field.creatureDensity);
//            out.writeInt(field.biome);
            out.writeInt(field.level);
            out.writeByte(field.weather.id);
            
            for (int j = 0; j < field.width * field.height; j++) {
                out.writeByte(field.field[j]);
            }
            for (int j = 0; j < field.width * field.height; j++) {
                out.writeByte(field.data[j]);
            }
            out.writeInt(field.entities.size());
            for (int j = 0; j < field.entities.size(); j++) {
                Entity e = field.entities.get(j);
                e.write(out);
            }
        }
        
        out.flush();
        out.close();
        return true;
    }
    
    public boolean load(Game game, InputHandler input) throws IOException {
        this.game = game;
        File f = new File("saves/" + title + ".dat");
        System.out.println("LOAD: " + f.getAbsolutePath());
        if (!f.exists()){
            return false;
        }
        in = new DataInputStream(new FileInputStream(f));
        System.out.println("LOAD GAME");
        System.out.println("  " + title
                );
        game.gameTime = in.readInt();
        game.weather = Weather.weathers[in.readByte()];
        byte []bName = new byte[in.readByte()];
        for (int i = 0; i < bName.length; i++) {
            bName[i] = in.readByte();
        }
        game.worldName = new String(bName);
        FieldGen.initRandom(game.worldName.hashCode());
        
        int currentLevel = in.readInt();
        int fieldsLength = in.readShort();
        int numFields = in.readShort();
        
        System.out.println("Current Level: " + currentLevel);
        
        game.currentLevel = currentLevel;
        game.fields = new GameField[fieldsLength];
        System.out.println("Fields: " + numFields + "/" + fieldsLength);
        for (int i = 0; i < numFields; i++) {
            int w = in.readInt();
            int h = in.readInt();
            int creatureDensity = in.readInt();
//            int biome = in.readInt();
            int level = in.readInt();
            Weather weather = Weather.weathers[in.readByte()];
            GameField field = new GameField(Biome.forest, level, false);
            game.fields[level] = field;
            field.creatureDensity = creatureDensity;
            field.width = w;
            field.height = h;
            field.weather = weather;
            field.field = new byte[w*h];
            field.data = new byte[w*h];
            field.entitiesInTiles = new ArrayList[w*h];
            
            for (int j = 0; j < w*h; j++) {
                field.entitiesInTiles[j] = new ArrayList<Entity>();
            }
            for (int j = 0; j < w*h; j++) {
                field.field[j] = in.readByte();
            }
            for (int j = 0; j < w*h; j++) {
                field.data[j] = in.readByte();
            }
            int numEntities = in.readInt();
            for (int j = 0; j < numEntities; j++) {
                Entity entity = null;
                boolean deadRead = false;
                int entityType = in.readInt();
                System.out.println("Read Entity: " + String.format("%X", entityType));
                switch (entityType) {
                    // CREATURES
                    case OPCODES.OP_ENTITY_PLAYER: entity = new Player(game, input);
                        break;
                    case OPCODES.OP_ENTITY_PIG: entity = new Pig();
                        break;
                    //ITEM ENTITY
                    case OPCODES.OP_ENTITY_ITEM_ENTITY: entity = new ItemEntity(null, 0, 0);
                        break;
                    // FURNITURE
                    case OPCODES.OP_ENTITY_BAG: entity = new Bag();
                        break;
                    case OPCODES.OP_ENTITY_TORCH: entity = new Torch();
                        break;
                    case OPCODES.OP_ENTITY_WORKBENCH: entity = new Workbench();
                        break;
                    default: 
//                        Class clazz = null;
                        System.out.println("Search alternative class");
                        for (Class clazz : OPCODES.opcodes.keySet()) {
                            if (OPCODES.opcodes.get(clazz) == entityType) {
                                try {
                                    System.out.println("Class: " + clazz.getSimpleName());
                                    entity = (Entity) clazz.newInstance();
                                } catch (InstantiationException ex) {
                                    deadRead = true;
                                } catch (IllegalAccessException ex) {
                                    deadRead = true;
                                }
                            }
                            if (deadRead) {
                                break;
                            }
                        }
                        break;
                }
                if (entity == null) {
                    entity = new Entity();
                    System.out.println("No class def found!");
                }
                if (deadRead)
                    System.out.println("  DEAD READ");
                entity.read(in);
                if (!deadRead) {
                    if (entity instanceof Player) {
                        System.out.println("PLAYER ADDED");
                        System.out.println("  " + entity.x);
                        System.out.println("  " + entity.y);
                        System.out.println("  " + (entity.x>>5));
                        System.out.println("  " + (entity.y>>5));
                        game.player = (Player) entity;
                    }
                    field.add(entity);
                }
            }
        }
        game.field = game.fields[currentLevel];
        in.close();
        return true;
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
