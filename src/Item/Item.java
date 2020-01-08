/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Entity.Plant.PlantTemplate;
import Res.Res;
import World.World;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Timot
 */
public class Item
{
    private String trueName;
    private String name;
    private Image texture;
    
    private boolean perishable;
    private int durability;
    private int maxDurability;
    private int stack;
    
    
    //a subItem is an item being contained by another Item
    //such as liquid within a waterskin
    //sword within a sheath
    //trinkets locked in a small chest
    private Item subItem;
    
    
    //a postConsume item will replace an consumable item once consumed
    //for example: bones will replace meat when meat is consumed
    //empty potion bottles will replace potions and as such
    private ArrayList<String> postConsume;
    
    //properties of an item
    //0 - perishable
    //1 - consumable
    //2 - liquid, drinkable, need container
    //3 - a liquid container
    //4 - stackable item
    //5 - readable item
    //6 - metal material
    //7 - generic metal products
    //
    //Equipment properties, stats of an equipment is represented by it's effects
    //20 - Equipable item
    //21 - Neck
    //22 - Head
    //23 - Back
    //24 - Main-Hand
    //25 - Body
    //26 - Off-Hand weapon/shield
    //27 - Ring
    //28 - Leg
    //29 - Accessory
    //30 - Cloak
    //31 - Shoes
    //32 - Belt
    //33 - 2-Hand
    
    
    
    private HashSet<Integer> properties;
    
    private ArrayList<Effect> effects;
    
    private ItemLibrary itemLibrary;
    
    private String desc,unidentified_desc;
    
    private String metal;
    
    private ArrayList<String> expire;
    
    //determines the ownership of an item,use to check if an item is stolen
    private String ownership;
    
    
    //constructor for seed items
    public Item(PlantTemplate pt,ItemLibrary itemLibrary)
    {
        this.trueName = pt.getName()+" Seed";
        this.name = pt.getName()+" Seed";
        this.properties = new HashSet<Integer>();
        
        //is a seed
        this.properties.add(201);
        //is stackable
        this.properties.add(4);
        
        this.effects = new ArrayList<Effect>();
        //gives the effect for planting a plant on a tile
        this.effects.add(new Effect(1,1,"plant&&"+pt.getName()));
        
        this.postConsume = new ArrayList<String>();
        this.itemLibrary = itemLibrary;
        this.stack = 1;
        this.durability = 1;
        this.maxDurability = 1;
        this.expire = null;
        this.desc = "A seed for "+pt.getName();
        this.texture = itemLibrary.getRes().getTextureByName("seed");
        this.ownership = "no_one";
        for(int i:pt.getGROUND())
        {
            properties.add(i);
        }
        
        
    }
    
    public Item(Item item)
    {
        this.trueName = item.getTrueName();
        this.name = item.getNameTrue();
        this.texture = item.getTexture();
        
        this.unidentified_desc = item.getUnidentified_desc();
        this.desc = item.getDescTrue();
        
        this.properties = new HashSet<Integer>(item.getProperties());
        this.effects = new ArrayList<Effect>(item.getEffects());
        this.postConsume = new ArrayList<String>(item.getPostConsume());
        this.itemLibrary = item.getItemLibrary();
        this.stack = item.getStack();
        this.durability = item.getDurability();
        this.maxDurability = item.getMaxDurability();
        this.expire = item.getExpire();
        this.ownership = item.getOwnership();
        
    }
    
    

    public Item(JSONObject json,ItemLibrary itemLibrary)
    {
        this.ownership = "no_one";
        this.itemLibrary = itemLibrary;
        properties = new HashSet<Integer>();
        effects = new ArrayList<Effect>();
        postConsume = new ArrayList<String>();
        stack = Integer.parseInt((String)json.get("stack"));
        this.trueName = (String)json.get("trueName");
        JSONArray propertiesArr = (JSONArray)json.get("properties");
        for(int i=0;i<propertiesArr.size();i++)
        {
            long temp = (long)propertiesArr.get(i);
            int j = (int)temp;
            properties.add(j);
        }
        JSONArray effectArr = (JSONArray)json.get("effects");
    
        for(int i=0;i<effectArr.size();i++)
        {
          
            JSONObject effectObj = (JSONObject)effectArr.get(i);
            effects.add(new Effect(Double.parseDouble((String)effectObj.get("value")),Integer.parseInt((String)effectObj.get("duration")),(String)effectObj.get("type")));
            
        }
        
        JSONArray postArr = (JSONArray)json.get("consumed");
        if(postArr!=null)
        {
            for(int i=0;i<postArr.size();i++)
            {
                JSONObject postObj = (JSONObject)postArr.get(i);
                postConsume.add((String)postObj.get("name"));
            }
        }
        this.stack = 1;
        
        this.desc = (String)json.get("desc");
        if(json.get("unidentified_desc")!=null)
        {
            this.unidentified_desc = (String)json.get("unidentified_desc");
        }
        
        long longDura = (long)json.get("durability");
        int duraInt = (int)longDura;
        this.durability = duraInt;
        this.maxDurability = duraInt;
        
        expire = new ArrayList<String>();
        
        if(json.get("expire")!=null)
        {
            JSONArray expArr = (JSONArray)json.get("expire");
            
                for(int i=0;i<expArr.size();i++)
                {
                    JSONObject expObj = (JSONObject)expArr.get(i);
                    expire.add((String)expObj.get("name"));
                }
            
        }
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        if(world.isMoved())
        {
            if(properties.contains(0))
            {
                durability--;
            }
        }
    }
    
    
    
    public void decreaseStack()
    {
        stack--;
    }
    
    public String getName()
    {
        if(name==null)
        {
            return trueName;
        }
        
        if(itemLibrary.checkIdentified(trueName))
        {

            return trueName;
        }else
        {

            return name;
        }
    }
    
    public void render(Graphics g,int x,int y,int w,int h,Color color)
    {
        texture.draw(x,y,w,h,color);
        if(properties.contains(0))
        {
            Color c = Color.decode("#00a300");
            c.a = 0.5f;
            g.setColor(c);
            g.fillRect(x, y+(h-(h*(getDurabilityPercentage()))), w/6, h*(getDurabilityPercentage()));
        }
    }
    
    public void render(Graphics g,int x,int y,int w,int h)
    {
        texture.draw(x,y,w,h);
        if(properties.contains(0))
        {
            Color c = Color.decode("#00a300");
            c.a = 0.5f;
            g.setColor(c);
            g.fillRect(x, y+(h-(h*(getDurabilityPercentage()))), w/6, h*(getDurabilityPercentage()));
        }
    }
    
    public float getDurabilityPercentage()
    {
      
        return (float)durability/maxDurability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = new Image(texture.getTexture());
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public HashSet<Integer> getProperties() {
        return properties;
    }

    public void setProperties(HashSet<Integer> properties) {
        this.properties = properties;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public Item getSubItem() {
        return subItem;
    }

    public void setSubItem(Item subItem) {
        this.subItem = subItem;
    }

    public ArrayList<String> getPostConsume() {
        return postConsume;
    }

    public void setPostConsume(ArrayList<String> postConsume) {
        this.postConsume = postConsume;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    public ItemLibrary getItemLibrary() {
        return itemLibrary;
    }

    public void setItemLibrary(ItemLibrary itemLibrary) {
        this.itemLibrary = itemLibrary;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public boolean isStackable() {
        return properties.contains(4);
    }

    
    
    public void addStack(int stack)
    {
        this.stack += stack;
    }
    
    public String getNameTrue()
    {
        return name;
    }

    public Integer getEquipmentType()
    {
        for(Integer i:properties)
        {
            if(i>=21&&i<=33)
            {
                return i;
            }
        }
        return -1;
    }
    
    public Integer getGenericType()
    {
        for(Integer i:properties)
        {
            if(i>=50&&i<=100)
            {
                return i;
            }
        }
        return -1;
    }
    
    public String getDesc() {
        if(unidentified_desc==null)
        {
            return desc;
        }
        
        if(itemLibrary.checkIdentified(trueName))
        {

            return desc;
        }else
        {

            return unidentified_desc;
        }
    }
    
    public Image paintItem(Image image1,ItemColour colour)
    {
        try {
            Image image = new Image(image1.getTexture());
            Graphics g = image.getGraphics();
            
            Color c1 = Color.decode(colour.getFirst());
            Color c2 = Color.decode(colour.getSecond());
            Color c3 = Color.decode(colour.getThird());
            Color c4 = Color.decode(colour.getFourth());
            
            Color cc1 = Color.decode("#ffff01");
            Color cc2 = Color.decode("#ffff02");
            Color cc3 = Color.decode("#ffff03");
            Color cc4 = Color.decode("#ffff04");
            
            for(int i=0;i<image.getHeight();i++)
            {
                for(int j=0;j<image.getWidth();j++)
                {
                    if(image.getColor(j, i).equals(cc1))
                    {
                        g.setColor(c1);
                        g.fillRect(j, i, 1, 1);
                    }else if(image.getColor(j, i).equals(cc2))
                    {
                        g.setColor(c2);
                        g.fillRect(j, i, 1, 1);
                    }else if(image.getColor(j, i).equals(cc3))
                    {
                        g.setColor(c3);
                        g.fillRect(j, i, 1, 1);
                    }else if(image.getColor(j, i).equals(cc4))
                    {
                        g.setColor(c4);
                        g.fillRect(j, i, 1, 1);
                    }
                }
            }
            

            g.flush();
            return image;
        } catch (SlickException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public void setMetalMaterial(ItemLibrary itemLibrary,Material material,Image template,String template_name)
    {
       
        trueName = trueName.replace("<metal>", material.getName());
        
        desc = desc.replaceAll("<metal>", material.getName());
        
        for(int i=0;i<material.getProperties().size();i++)
        {
            if(!properties.contains(material.getProperties().get(i))&&material.getProperties().get(i)!=52)
            {
                properties.add(material.getProperties().get(i));
            }
        }
        
        for(int i=0;i<effects.size();i++)
        {
            for(int j=0;j<material.getMultipliers().size();j++)
            {
                if(effects.get(i).getType().startsWith(material.getMultipliers().get(j).getType()))
                {
                    effects.get(i).multiplyValue(material.getMultipliers().get(j).getValue());
                }
            }
        }
        
        
        if(itemLibrary.getRes().getTextureByName(trueName+material.getItemColor().getName()+"_"+template_name)!=null)
        {
           
            //grab painted template texture if already exists in res class
            this.texture = itemLibrary.getRes().getTextureByName(trueName+material.getItemColor().getName()+"_"+template_name);
        }else
        {
            //paint a new template texture if not exist in res class
            this.texture = paintItem(template,material.getItemColor());
            itemLibrary.getRes().getImages().put(trueName+material.getItemColor().getName()+"_"+template_name,this.texture);
        }
            
        
        
        
    }
    
    public int getItemType()
    {
        for(Integer i:properties)
        {
            if(i>=50&&i<=60)
            {
                return i;
            }
        }
        return -1;
    }
    

    
    
    public String getUnidentifiedName()
    {
        return name;
    }
    
    public boolean isMetalMaterial()
    {
        return properties.contains(52);
    }
    
    public String getDescTrue()
    {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnidentified_desc() {
        return unidentified_desc;
    }

    public void setUnidentified_desc(String unidentified_desc) {
        this.unidentified_desc = unidentified_desc;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }
    
    public Effect getEffectByName(String name)
    {
        for(int i=0;i<effects.size();i++)
        {
            if(effects.get(i).getType().equals(name))
            {
                return effects.get(i);
            }
        }
        return null;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public ArrayList<String> getExpire() {
        return expire;
    }

    public void setExpire(ArrayList<String> expire) {
        this.expire = expire;
    }

    public String getOwnership()
    {
        return ownership;
    }

    public void setOwnership(String ownership)
    {
        this.ownership = ownership;
        if(!ownership.equals("player")&&!ownership.equals("no_one"))
        {
            this.desc += " Belongs to "+ownership;
        }
        
    }
    
    
    
    
}
