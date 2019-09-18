/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.Image;

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
    private boolean lifeSpan;
    
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
    
    
    
    private ArrayList<Integer> properties;
    
    private ArrayList<Effect> effects;
    
    private ItemLibrary itemLibrary;
    
    private String desc,unidentified_desc;
    
    public Item(Item item)
    {
        this.trueName = item.getTrueName();
        this.name = item.getNameTrue();
        this.texture = item.getTexture();
        
        this.unidentified_desc = item.getUnidentified_desc();
        this.desc = item.getDescTrue();
        
        this.properties = new ArrayList<Integer>(item.getProperties());
        this.effects = new ArrayList<Effect>(item.getEffects());
        this.postConsume = new ArrayList<String>(item.getPostConsume());
        this.itemLibrary = item.getItemLibrary();
        this.stack = item.getStack();
    }

    public Item(JSONObject json,ItemLibrary itemLibrary)
    {
        this.itemLibrary = itemLibrary;
        properties = new ArrayList<Integer>();
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

    public boolean isLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(boolean lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Integer> properties) {
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

    public Integer getType()
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
    
    
    
    
    
    
}
