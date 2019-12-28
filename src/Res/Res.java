/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Res;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;


/**
 *
 * @author Timot
 */
public class Res
{
    
    
    
    public Image human1,human2;
    public Image basicTile,basicWall;

    public Image basicItem,basicItem2;
    
    //Inventory UI
    public Image inventory_icon;
    public Image inventory_bg_1,inventory_bg_2,crafting_bg_1,equipment_bg,inventory_bg_bar;
    public Image inventory_scroll_up,inventory_scroll_down;
    public Image quick_item_bg;
    //crafting UI
    public Image crafting_icon,crafting_clear_all,crafting_craft,crafting_filter_by_learnt,crafting_filter_by_learnt_and_craftable;
    
    public Image furniture_inventory,furniture_inventory_fuelable;
    
    public Image meat_raw_1;
    
    
    public Image minimize,maximize;
    
    public Image up_indicator,down_indicator;
    
    public Image close_icon,pin_icon,unpin_icon;
    
    public TrueTypeFont pixelCowboy,disposableDroidBB,disposableDroidBB20f,disposableDroidBB40f;
    
    public GameContainer container;
    
    public Image sharp_object;
    
    
    public Image empty_potion,wood,leaves,wooden_shaft,wood_shavings,book_1;
    
    
    public Image wooden_sword,iron_sword,zweihander,wooden_shield,dagger;
    
    public Image metal_bar,metal_bar_2;
    
    
    public Image table;
    public SpriteSheet camp_fire;
    
    
    public Image tree_1;
    
    public Image metal_bar_template;
    public Image potion_template,sword_template,dagger_template;
    
    
    private HashMap<String,Image> images;
    private ArrayList<Pair<String,SpriteSheet>> sprites;
    
    
    public Sound potion_pop;
    
    
    
    
    
    public Res(GameContainer container) throws IOException, SlickException
    {
        sprites = new ArrayList<Pair<String,SpriteSheet>>();
        
//        potion_pop = new Sound("res/audio/potion_pop.ogg");
        
        images = new HashMap<String,Image>();
        this.container = container;
        SpriteSheet ss = new SpriteSheet("res/texture/entities/player0.png",32,32);
        
        SpriteSheet tree_s = new SpriteSheet("res/texture/entities/trees_1.png",32,32);
        
        

        human1 = ss.getSubImage(0, 0, 32, 32);
        human2 = ss.getSubImage(32, 32, 32, 32);
        basicTile = new Image("res/texture/map/tile/basic_tile.png");
        basicWall = new Image("res/texture/map/tile/basic_wall.png");

        inventory_icon = new Image("res/texture/ui/icon/inventory_icon.png");
        inventory_bg_1 = new Image("res/texture/ui/background/inventory_bg_1_small.png");
        inventory_bg_2 = new Image("res/texture/ui/background/inventory_bg_2.png");
        inventory_bg_bar = new Image("res/texture/ui/background/inventory_bar.png");
        equipment_bg = new Image("res/texture/ui/background/equipment_bg.png");
        
        crafting_icon = new Image("res/texture/ui/icon/crafting_icon.png");
        crafting_bg_1 = new Image("res/texture/ui/background/crafting_bg_1.png");
        
        crafting_clear_all = new Image("res/texture/ui/icon/crafting_clear_all.png");
        crafting_craft = new Image("res/texture/ui/icon/crafting_craft.png");
        crafting_filter_by_learnt = new Image("res/texture/ui/icon/crafting_filter_by_learnt.png");
        crafting_filter_by_learnt_and_craftable = new Image("res/texture/ui/icon/crafting_filter_by_learnt_and_craftable.png");
        
        maximize = new Image("res/texture/ui/icon/maximize.png");
        minimize = new Image("res/texture/ui/icon/minimize.png");
        
        
        furniture_inventory = new Image("res/texture/ui/background/furniture_inventory.png");
        furniture_inventory_fuelable = new Image("res/texture/ui/background/furniture_inventory_fuelable.png");
        
        
        close_icon = new Image("res/texture/ui/icon/close_icon.png");
        pin_icon = new Image("res/texture/ui/icon/pin_button.png");
        unpin_icon = new Image("res/texture/ui/icon/unpin_button.png");
        
        quick_item_bg = new Image("res/texture/ui/inventory/quick_item_bar.png");
        
        up_indicator = new Image("res/texture/ui/option_tab/up_indicator.png");
        down_indicator = new Image("res/texture/ui/option_tab/down_indicator.png");
        
        inventory_scroll_up = new Image("res/texture/ui/inventory/scroll_up_button.png");
        inventory_scroll_down = new Image("res/texture/ui/inventory/scroll_down_button.png");
        SpriteSheet item_sheet = new SpriteSheet("res/texture/items/Potion.png",32,32);
        
        basicItem = item_sheet.getSprite(0, 0);
        
        metal_bar_template = new Image("res/texture/items/metal_bar_template.png");
        potion_template = new Image("res/texture/items/potion_template.png");
        sword_template = new Image("res/texture/items/sword_template.png");
        dagger_template = new Image("res/texture/items/dagger_template.png");
        
        
        empty_potion = new Image("res/texture/items/empty_potion_bottle.png");
        wood = new Image("res/texture/items/wood.png");
        leaves = new Image("res/texture/items/leaves.png");
        wooden_shaft = new Image("res/texture/items/wooden_shaft.png");
        
        metal_bar = new Image("res/texture/items/metal_bar.png");
        metal_bar_2 = new Image("res/texture/items/metal_bar_2.png");
        
        dagger = new Image("res/texture/items/dagger.png");
        wooden_sword = new Image("res/texture/items/wooden_sword.png");
        iron_sword = new Image("res/texture/items/iron_sword.png");
        zweihander = new Image("res/texture/items/zweihander.png");
        wooden_shield = new Image("res/texture/items/wooden_shield.png");
        wood_shavings = new Image("res/texture/items/wood_shavings.png");
        
        tree_1 = new Image("res/texture/entities/tree_1.png");
        
        meat_raw_1 = new Image("res/texture/items/meat_raw_1.png");
        sharp_object = new Image("res/texture/items/sharp_object.png");
        
        table = new Image("res/texture/furniture/table.png");
        
        camp_fire = new SpriteSheet("res/texture/furniture/camp_fire.png",32,32);
        
        SpriteSheet books = new SpriteSheet("res/texture/items/Book.png",32,32);
        
        book_1 = new Image("res/texture/items/book_1.png");
        
        images.put("raw_meat_1",meat_raw_1);
        images.put("dagger",dagger);
        images.put("dagger_template",dagger_template);
        images.put("sword_template",sword_template);
        images.put("book_1",book_1);
        images.put("empty_bottle",empty_potion);
        images.put("wooden_sword",wooden_sword);
        images.put("iron_sword",iron_sword);
        images.put("zweihander",zweihander);
        images.put("wooden_shield",wooden_shield);
        images.put("tree_1",tree_1);
        images.put("wood",wood);
        images.put("leaves",leaves);
        images.put("sharp_object",sharp_object);
        images.put("wooden_shaft",wooden_shaft);
        images.put("wood_shavings",wood_shavings);
        images.put("table",table);
        images.put("metal_bar",metal_bar);
        images.put("metal_bar_2",metal_bar_2);
        images.put("axe_template",sword_template);
        
        sprites.add(new Pair("camp_fire",camp_fire));
        sprites.add((new Pair("tree_1",tree_s)));
        
        Graphics g = basicItem.getGraphics();
        g.setColor(Color.green);
        g.fillRect(0, 0, 20, 20);
        g.flush();
        basicItem2 = item_sheet.getSprite(0, 1);
        
        try {
		pixelCowboy = loadFont("res/font/PixelCowboy/Pixel_Cowboy.otf",24f);
                disposableDroidBB = loadFont("res/font/DisposableDroidBB/DisposableDroidBB.ttf",30f);
                disposableDroidBB20f = loadFont("res/font/DisposableDroidBB/DisposableDroidBB.ttf",20f);
                disposableDroidBB40f = loadFont("res/font/DisposableDroidBB/DisposableDroidBB.ttf",40f);
	} catch (Exception e) {
		e.printStackTrace();
	}	
        
    }
    
    public SpriteSheet getSpriteByName(String name)
    {
        for(Pair<String,SpriteSheet> p:sprites)
        {
            if(p.getKey().equals(name))
            {
                return p.getValue();
            }
        }
        
        return null;
    }
    
    public Image getTextureByName(String name)
    {
        
        
        return images.get(name);
    }
    
    public TrueTypeFont loadFont(String path,float size) throws FontFormatException, IOException
    {
        InputStream inputStream	= ResourceLoader.getResourceAsStream(path);
        Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        awtFont2 = awtFont2.deriveFont(size); // set font size
        TrueTypeFont font = new TrueTypeFont(awtFont2, false);
        return font;
    }

    public HashMap<String,Image> getImages() {
        return images;
    }

    public void setImages(HashMap<String,Image> images) {
        this.images = images;
    }

    public ArrayList<Pair<String, SpriteSheet>> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Pair<String, SpriteSheet>> sprites) {
        this.sprites = sprites;
    }
    
    
    
}
