/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.*;
import caregame.crafting.Crafting;
import caregame.entity.particle.TextParticle;
import caregame.field.GameField;
import caregame.field.GrassTile;
import caregame.field.Tile;
import caregame.item.Item;
import caregame.screen.CraftingScreen;
import caregame.screen.InventoryScreen;
import caregame.screen.TitleScreen;
import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Markus
 */
public class Player extends Creature {

    public Game game;
    public Inventory inventory = new Inventory(6);
    public Item activeItem;
    private InputHandler input;
    
    private int interactRange = 24;
    private int attackRange = 8;
    
    private int maxStamina = 10;
    private int stamina = maxStamina;
    private int staminaRecharge = 0;
    private int staminaRechargeDelay = 0;
    private int attackTime, attackDir;
    private Item attackItem;
    private int onElevatorDelay;
    
    public int maxHunger = 50;
    public int hunger = maxHunger;
    private int hungerTime;
    private int hungerDamageTime;
    
    public Player(Game game, InputHandler input) {
        this.game = game;
        this.input = input;
        xr = 14;
        yr = 8;
        //xr = 24;
        //yr = 24;
    }

    @Override
    public int getLightRadius() {
        return 2;
    }

    @Override
    public float getLightIntensity() {
        return 0.3f;
    }
    
    
    
    public boolean payStamina(int cost) {
        if (cost == 0) return true;
        if (cost > stamina) return false;
        stamina -= cost;
        return true;
    }

    @Override
    public boolean canSwim() {
        return true;
    }
    
    @Override
    public void render(Graphics g) {
        int xo = x - 32;
        int yo = y - 60;
        if (isSwimming()) {
            yo += 30;
            ImageCache.get().get("blume_gelb.png").render(g, x-32, y-16, 64, 32);
            if (dir == DIR_WEST) ImageCache.get().get("spieler_seite_links.png").render(g, xo, yo, xo+64, yo+32, 0, 0, 64, 32);
            if (dir == DIR_EAST) ImageCache.get().get("spieler_seite_rechts.png").render(g, xo, yo, xo+64, yo+32, 0, 0, 64, 32);
            if (dir == DIR_NORTH) ImageCache.get().get("spieler_hinten.png").render(g, xo, yo, xo+64, yo+32, 0, 0, 64, 32);
            if (dir == DIR_SOUTH) ImageCache.get().get("spieler.png").render(g, xo, yo, xo+64, yo+32, 0, 0, 64, 32);
        } else {
            if (dir == DIR_WEST) ImageCache.get().get("spieler_seite_links.png").render(g, xo, yo, 64, 64);
            if (dir == DIR_EAST) ImageCache.get().get("spieler_seite_rechts.png").render(g, xo, yo,  64, 64);
            if (dir == DIR_NORTH) ImageCache.get().get("spieler_hinten.png").render(g, xo, yo, 64, 64);
            if (dir == DIR_SOUTH) ImageCache.get().get("spieler.png").render(g, xo, yo, 64, 64);
        }
        if (xo < 0) xo = 0;
        if (yo < 0) yo = 0;
        if (stamina >= 0 && stamina < 2)ImageCache.get().get("stamina/duenger1.png").render(g, xo, yo, 16, 16);
        if (stamina >= 2 && stamina < 4)ImageCache.get().get("stamina/duenger2.png").render(g, xo, yo, 16, 16);
        if (stamina >= 4 && stamina < 6)ImageCache.get().get("stamina/duenger3.png").render(g, xo, yo, 16, 16);
        if (stamina >= 6 && stamina < 8)ImageCache.get().get("stamina/duenger4.png").render(g, xo, yo, 16, 16);
        if (stamina >= 8 && stamina <= 10)ImageCache.get().get("stamina/duenger5.png").render(g, xo, yo, 16, 16);
        //center x,y point where the "step" happens (tiles)
//        g.fillRect(x, y, 1, 1);
        //draw hitbox
        if (Game.DEBUG) {
            //range
            int yo2 = -4;
//            if (dir == DIR_WEST) xt = (x - xr - range) >> 5;
//            if (dir == DIR_EAST) xt = (x + xr + range) >> 5;
//            if (dir == DIR_SOUTH) yt = (y + yr + range) >> 5;
//            if (dir == DIR_NORTH) yt = (y - yr - range) >> 5;
//            g.setColor(Color.blue);
            if (dir == DIR_WEST)g.drawLine(x - xr - interactRange, y+yo2, x, y+yo2);
            if (dir == DIR_EAST)g.drawLine(x, y+yo2, x + xr + interactRange, y+yo2);
            if (dir == DIR_SOUTH)g.drawLine(x, y+yo2, x, y + yr + interactRange+yo2);
            if (dir == DIR_NORTH)g.drawLine(x, y+yo2 - yr - interactRange, x, y+yo2);
            g.setColor(Color.red);
//            if (dir == DIR_WEST)g.drawLine(x - xr - attackRange, y, x, y);
//            if (dir == DIR_EAST)g.drawLine(x, y, x + xr + attackRange, y);
//            if (dir == DIR_SOUTH)g.drawLine(x, y, x, y + yr + attackRange);
//            if (dir == DIR_NORTH)g.drawLine(x, y - yr - attackRange, x, y);
            
            //move box
            g.setColor(Color.black);
            g.drawRect(x-xr, y-yr, xr+xr, yr+yr);
            g.drawLine(x-xr, y-yr, x + xr, y + yr);
            g.drawLine(x-xr,y+yr,x+xr,y-yr);
        }
//        g.fillRect(x,y,1,1);
//        g.drawLine(x,y,x+xr+8,y);
        if (attackTime > 0) {
            if (attackItem != null) {
                int xx = xo + 32 - 4;
                int yy = yo + 32 - 4;
                if (attackDir == DIR_EAST) xx = x + xr;
                if (attackDir == DIR_WEST) xx = x - xr - 16;
                if (attackDir == DIR_NORTH) yy = y - yr - 16;
                if (attackDir == DIR_SOUTH) yy = y + yr;
                attackItem.renderIcon(g, xx, yy);
            }
        }
        
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        super.write(out);
        out.writeInt(hunger);
        inventory.write(out);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        super.read(in);
        hunger = in.readInt();
        inventory = new Inventory();
        inventory.read(in);
    }
    
    

    @Override
    public boolean findStartPos(GameField field) {
        for(;;) {
            int xt = random.nextInt(field.width);
            int yt = random.nextInt(field.height);
            if (field.getTile(xt, yt) instanceof GrassTile) {
                this.x = xt * Game.TILE_SIZE;
                this.y = yt * Game.TILE_SIZE;
                return true;
            }
        }
    }
    

    @Override
    public void tick() {
        super.tick();
        
        Tile onTile = field.getTile(x>>5, y>>5);
        if (onTile == Tile.elevatorUpTile || onTile == Tile.elevatorDownTile) {
            if (onElevatorDelay == 0) {
                int d = 0;
                if (onTile == Tile.elevatorUpTile) d = 1;
                if (onTile == Tile.elevatorDownTile) d = -1;
                changeLevel(d);
                onElevatorDelay = 10;
                return;
            }
            onElevatorDelay = 10;
        } else {
            if (onElevatorDelay > 0) onElevatorDelay--;
        }
        
        if (hunger == 0 && hungerDamageTime == 0) {
            hungerDamageTime = 120;
            health--;
        }
        if (hungerDamageTime > 0) hungerDamageTime--;
        
        //10HU / 1min
        // 10 HU / 60sec
        // 1 HU / 6 sec
        // 1 HU / 6*60 => 360
        if (hungerTime == 0) {
            hunger--; if (hunger < 0) hunger = 0;
            hungerTime = 360;
        }
        if (hungerTime > 0) hungerTime--;
        
        if (stamina <= 0 && staminaRechargeDelay == 0 && staminaRecharge == 0) {
            staminaRechargeDelay = 40;
        }
        
        if (staminaRechargeDelay > 0) {
            staminaRechargeDelay--;
        }
        
        if (staminaRechargeDelay == 0) {
            staminaRecharge++;
            if (isSwimming()) staminaRecharge = 0;
            while (staminaRecharge > 10) {
                staminaRecharge -= 10;
                if (stamina < maxStamina) stamina++;
            }
        }
        
        int dx = 0;
        int dy = 0;
        if (input.left.down) dx--;
        if (input.right.down) dx++;
        if (input.up.down) dy--;
        if (input.down.down) dy++;
        if (isSwimming() && tickTime % 60 == 0) {
            if (stamina > 0) {
                stamina--;
            } else {
                hurt(this, 1, dir);
            }
        }
        if (staminaRechargeDelay % 2 == 0) {
            move(dx, dy);
        }
        
        if (input.attack.clicked) {
            if (stamina > 0) {
                stamina--;
                staminaRecharge = 0;
                attack();
            }
        }
        if (input.menu.clicked) {
            if (!use())
                game.setScreen(new InventoryScreen(this));
        }
        if (input.crafting.clicked) {
            game.setScreen(new CraftingScreen(this, Crafting.freeRecipes));
        }
        if (input.drop.clicked) {
            if (activeItem != null) {
                drop(activeItem);
                activeItem = null;
            }
        }
        if (attackTime > 0) attackTime--;
        
        if (input.escape.clicked) {
            game.canResume = true;
            game.setScreen(new TitleScreen());
        }
    }
    
    private void attack() {
        attackDir = dir;
        attackItem = activeItem;
        boolean done = false;
        int yo = -4;
        if (activeItem != null) {
            attackTime = 10;
//            int interactRange = 8;
            //interact with entities
            System.out.println("Interact with entities...");
            if (dir == DIR_WEST && interact(x - xr - interactRange, y+yo, x - xr, y+yo)) done = true; 
            if (dir == DIR_EAST && interact(x + xr, y+yo, x + xr + interactRange, y+yo)) done = true; 
            if (dir == DIR_SOUTH && interact(x, y+yr+yo, x, y + yr + interactRange+yo)) done = true; 
            if (dir == DIR_NORTH && interact(x, y-yr-interactRange+yo, x, y - yr+yo)) done = true; 
            if(done) return;

            int xt = x >> 5;
            int yt = (y+yo) >> 5;

            if (dir == DIR_WEST) xt = (x - xr - interactRange) >> 5;
            if (dir == DIR_EAST) xt = (x + xr + interactRange) >> 5;
            if (dir == DIR_SOUTH) yt = (y + yr + interactRange+yo) >> 5;
            if (dir == DIR_NORTH) yt = (y - yr - interactRange+yo) >> 5;
            if (xt >= 0 && yt >= 0 && xt < field.width && yt < field.height){
                //interact item on tile (like placing furniture)
                System.out.println("Interact with active item " + activeItem.getName() + " on " + field.getTile(xt,yt).getClass().getSimpleName() + ".");
                if (activeItem.interactOn(field.getTile(xt, yt), field, xt, yt, this, dir)) {
                    done = true;
                } else {
                    System.out.println("Interact on field with active item " + activeItem.getName());
                    if(field.getTile(xt, yt).interact(field, xt, yt, this, activeItem, dir)) {
                        done = true;
                    }
                }
                if (activeItem.isDepleted()) {
                    activeItem = null;
                }
            }
        }
        if (done) return;
        //hurt entity or tile
        if (activeItem == null || activeItem.canAttack()) {
            attackTime = 5;
//            int attackRange = 8;
            System.out.println("Attack, noactive item // attackable");
//            if (dir == DIR_WEST) hurt(x - 32, y, x - 32 - r, y); 
//            if (dir == DIR_EAST) hurt(x + 32, y, x + 32 + r, y); 
//            if (dir == DIR_SOUTH) hurt(x, y, x, y + r); 
//            if (dir == DIR_NORTH) hurt(x, y, x, y - 32 - r); 
//            if (dir == DIR_WEST)  hurt(x - xr - 14, y, x - xr, y); 
//            if (dir == DIR_EAST)  hurt(x + xr, y, x + xr + 14, y); 
//            if (dir == DIR_SOUTH) hurt(x, y + yr, x, y + yr + 14); 
//            if (dir == DIR_NORTH) hurt(x, y - yr - 14, x, y - yr); 
            if (dir == DIR_WEST)  hurt(x - xr - attackRange, y+yo     , x - xr, y+yo); 
            if (dir == DIR_EAST)  hurt(x + xr, y+yo     , x + xr + attackRange, y+yo); 
            if (dir == DIR_SOUTH) hurt(x     , y + yr+yo, x     , y + yr + attackRange+yo); 
            if (dir == DIR_NORTH) hurt(x     , y - yr - attackRange+yo, x     , y - yr+yo); 
            
            int xt = x >> 5;
            int yt = (y+yo) >> 5;
            if (dir == DIR_WEST)  xt = (x - xr - attackRange) >> 5;
            if (dir == DIR_EAST)  xt = (x + xr + attackRange) >> 5;
            if (dir == DIR_NORTH) yt = (y - yr - attackRange+yo) >> 5;
            if (dir == DIR_SOUTH) yt = (y + yr + attackRange+yo) >> 5;
            if (xt >= 0 && yt >= 0 && xt < field.width && yt < field.height){
                System.out.println("Hurt tile");
                field.getTile(xt, yt).hurt(field, xt, yt, this, random.nextInt(3)+1, dir);
            }
        }
    }
    
    private void hurt(int x0, int y0, int x1, int y1) {
        List<Entity> entities = field.getEntities(x0, y0, x1, y1);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e != this) e.hurt(this, getAttackDamage(e), dir);
        }
    }
    private boolean interact(int x0, int y0, int x1, int y1) {
        List<Entity> entities = field.getEntities(x0, y0, x1, y1);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e == this) continue;
            if (e.interact(this, activeItem, dir)) return true;
        }
        return false;
    }
    
    private int getAttackDamage(Entity e) {
        int dmg = random.nextInt(3) + 1;
        return dmg;
    }
    
    private boolean use() {
//        int r = 12;
        int x0 = x;
        int y0 = y;
        int x1 = x;
        int y1 = y;
        if (dir == DIR_WEST) { x0 = x - xr - 14; x1 = x - xr; }
        if (dir == DIR_EAST) { x0 = x + xr; x1 = x + xr + 14; }
        if (dir == DIR_NORTH) { y0 = y - yr - 14; y1 = y - yr; }
        if (dir == DIR_SOUTH) { y0 = y + yr; y1 = y + yr + 14; }
        List<Entity> entities = field.getEntities(x0, y0, x1, y1);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
//            System.out.println("U: " + e.getClass().getSimpleName());
            if(e != this) if (e.use(this, dir)) return true;
        }
        return false;
    }
    
    @Override
    public void touchItem(ItemEntity itemEntity) {
        if (inventory.add(itemEntity.item))
            itemEntity.take(this);
    }
    
    public void changeLevel(int dir) {
        game.scheduleLevelChange(dir);
    }

    @Override
    protected void touchedBy(Entity e) {
        if (!(e instanceof Player)) {
            e.touchedBy(this);
        }
    }

    public void eat(int hungerRegain) {
        this.hunger += hungerRegain;
        field.add(new TextParticle("E"+Integer.toString(hungerRegain), x, y));
        if (hunger > maxHunger) hunger = maxHunger;
    }

    public void drop(Item item) {
        if (item != null) {
            inventory.items.remove(item);
            field.add(new ItemEntity(item, x + random.nextInt(10) + 3, y + random.nextInt(10) + 3));
        }
    }
    
}
