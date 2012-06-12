/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.field.GameField;
import caregame.item.*;
import caregame.item.resource.Resource;
import caregame.screen.LevelTransitionScreen;
import caregame.screen.Screen;
import caregame.screen.TitleScreen;
import caregame.weather.Weather;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Markus
 */
public class Game extends Canvas implements Runnable {
    
    public static int TILE_SIZE = 32;
    
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final String TITLE = "Care Game";
    
    public static void main(String []args) {
        Game game = new Game();
        game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        game.setIgnoreRepaint(true);
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        game.requestFocus();
        game.start();
    }
    
    public boolean debug = false;
    
    private boolean running;
    private BufferStrategy bs;
    
    public GameField []fields;
    public GameField field;
//    public Inventory inventory;
    public Weather weather = Weather.sunny;
    public boolean canResume = false;
        
    private Screen screen;
    
    public Texturepack texturepack;
    
    private int ticks, fps;

    private boolean stopKeyRepeat = false;
    private int lastRepeatKey;
    
    public String worldName;
    
    public Player player;
    
    public InputHandler input = new InputHandler(this);
    
    private int currentLevel;
    private int pendingLevelChange;
    
    public void setTexturepack(Texturepack texturepack) {
        this.texturepack = texturepack;
        ImageCache.get().setTexturePackPath(texturepack.file.getAbsolutePath());
    }
    
    
    
    public void setScreen(Screen screen) {
        this.screen = screen;
        if (this.screen != null) {
            this.screen.init(this, input);
        }
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    /** only prepare variables to get filled */
    public void startLoadedGame(String worldName) {
        this.worldName = worldName;
//        field = new GameField();
//        cx = 0;
//        cy = 0;
        player = new Player(this, input);
//        selectedItem = null;
        weather = Weather.getRandomWeather();
        canResume = false;
    }
    
    public void startNewGame(String worldName) {
        this.worldName = worldName;
        currentLevel = 255;
        fields = new GameField[256];
        fields[255] = new GameField(255);
        field = fields[255];
//        cx = 0;
//        cy = 0;
//        selectedItem = null;
        weather = Weather.getRandomWeather();
        canResume = false;
        player = new Player(this, input);
        player.inventory.add(new ToolItem(ToolType.hoe, 0));
        player.inventory.add(new ToolItem(ToolType.shovel, 0));
        player.inventory.add(new ToolItem(ToolType.axe, 0));
        player.inventory.add(new ResourceItem(Resource.wood, 999));
        player.inventory.add(new ResourceItem(Resource.wheatSeeds, 999));
        player.inventory.add(new WaterCan(0));
        player.findStartPos(field);
        field.add(player);
        setScreen(null);
    }

    @Override
    public void run() {
        
        setScreen(new TitleScreen());
        
        init();
        double nsPerTick = 1000000000.0 / 60.0;
        double unprocessed = 0.0;
        long lastTime = System.nanoTime();
        long lastTime1 = System.currentTimeMillis();
        
        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (unprocessed >= 1.0) {
                tick();
                unprocessed -= 1.0;
                ticks++;
            }
            
            render();
            fps++;
            
            try {
                Thread.sleep(4);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (System.currentTimeMillis() - lastTime1 > 1000) {
                lastTime1 += 1000;
                System.out.println(ticks + " ticks, " + fps + " fps");
                ticks = 0;
                fps = 0;
            }
        }
        System.exit(0);
    }
    
    private void init() {
        createBufferStrategy(2);
        this.bs = getBufferStrategy();
        
        running = true;
    }
    
    private void tick() {
        if (!hasFocus()) {
            input.releaseAll();
        } else {
            input.tick();
            if(screen != null) {
                screen.tick();
            } else {
                if (player.removed) {
                    //DEAD SCREEN
                } else {
                    if (pendingLevelChange != 0) {
                        setScreen(new LevelTransitionScreen(pendingLevelChange));
                        pendingLevelChange = 0;
                    }
                }
                field.tick();
            }
        }
    }
    private List<Entity> rowSprites = new ArrayList<Entity>();
    private Comparator<Entity> spriteSorter = new Comparator<Entity>() {

        @Override
        public int compare(Entity e0, Entity e1) {
            if (e1.y < e0.y) return 1;
            if (e1.y > e0.y) return -1;
            return 0;
        }
        
    };
    private void render() {
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        int ww = getWidth();
        int hh = getHeight();
//        int xx = (getWidth() - ww) / 2;
//        int yy = (getHeight() - hh) / 2;
        
//        g.translate(xx, yy);
        if (screen == null || !screen.isPausingGame()) {
            {
                int xScroll = player.x - ww/2;
                int yScroll = player.y - hh/2;
                if (xScroll < 0) xScroll = 0;
                if (yScroll < 0) yScroll = 0;
                if (xScroll + ww >= field.width*32) xScroll = field.width * 32 - ww;
                if (yScroll + hh >= field.height*32) yScroll = field.height * 32 - hh;
                
                g.translate(-xScroll, -yScroll);
                int xt0 = xScroll >> 5;
                int yt0 = yScroll >> 5;
                int xt1 = (xScroll + ww + 64) >> 5;
                int yt1 = (yScroll + hh + 64) >> 5;
                for (int y = yt0; y < yt1; y++) {
                    for (int x = xt0; x < xt1; x++) {
                        field.getTile(x, y).render(g, field, x, y);
                    }
                }
                
                for (int y = yt0; y <= yt1; y++) {
                    for (int x = xt0; x <= xt1; x++) {
                        if (x < 0 || y < 0 || x >= field.width || y >= field.height) continue;
                        rowSprites.addAll(field.entitiesInTiles[x+y*field.width]);
                    }
                    if (rowSprites.size() > 0) {
                        Collections.sort(rowSprites, spriteSorter);
                        for (int i = 0; i < rowSprites.size(); i++) {
                            rowSprites.get(i).render(g);
                        }
                    }
                    rowSprites.clear();
                }
                
                g.translate(xScroll, yScroll);
                
            }
            renderGui(g);
        } 
        if (screen != null) {
            screen.render(g);
        }
//        ImageCache.get().get("icons.png").render8(g, 0 * 32 + 0, 0 * 32 + 0, 10 + 0 * 32);
//        ImageCache.get().get("icons.png").render8(g, 1 * 32 + 0, 0 * 32 + 0, 10 + 1 * 32);
        if (!hasFocus()) {
            renderFocusGrab(g);
        }
        
//        g.translate(-xx, -yy);
        
        g.dispose();
        bs.show();
    }
    
    private void renderGui(Graphics g) {
    }

    public void changeLevel(int dir) {
        field.remove(player);
        currentLevel += dir;
        field = fields[currentLevel];
        if (field == null) {
            fields[currentLevel] = new GameField(currentLevel);
            field = fields[currentLevel];
        }
        player.x = (player.x >> 5) * 32 + 16;
        player.y = (player.y >> 5) * 32 + 16;
        field.add(player);
    }

    public void stop() {
        running = false;
    }

    private void renderFocusGrab(Graphics g) {
        Screen.renderFrameText(g, getWidth() / 2 - 150, getHeight() / 2 - Screen.getFrameHeight(1), 300, Screen.ALIGN_CENTER, "CLICK TO FOCUS!");
    }

    public void stopKeyRepeat() {
        this.stopKeyRepeat = true;
    }

    public void scheduleLevelChange(int dir) {
        if (currentLevel + dir < 0) return;
        if (currentLevel + dir >= fields.length) return;
        pendingLevelChange = dir;
    }
    
    
    
}
