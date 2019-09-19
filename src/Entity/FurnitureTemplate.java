/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import Res.Res;
import org.json.simple.JSONArray;

/**
 *
 * @author Timot
 */
public class FurnitureTemplate
{
    private ArrayList<Integer> properties;
    private String name;
    private String desc;
    private SpriteSheet spriteSheet;
    private Image texture;
    
    private boolean autoAnimate;
    
    
    public FurnitureTemplate(JSONObject jsonObj,Res res)
    {
        this.name = (String)jsonObj.get("name");
        this.desc = (String)jsonObj.get("description");
        if(Integer.parseInt((String)jsonObj.get("use_spritesheet"))==1)
        {
            spriteSheet = res.getSpriteByName((String)jsonObj.get("texture"));
            
            if(Integer.parseInt((String)jsonObj.get("auto_animate"))==0)
            {
                autoAnimate = false;
            }else
            {
                autoAnimate = true;
            }
            
        }else
        {
            this.texture = res.getTextureByName((String)jsonObj.get("texture"));
        }
        
        properties = new ArrayList<Integer>();
        
        
        JSONArray proArr = (JSONArray)jsonObj.get("properties");
        for(int i=0;i<proArr.size();i++)
        {
            properties.add(Integer.parseInt((String)proArr.get(i)));
        }
        this.texture = res.getTextureByName((String)jsonObj.get("texture"));
        
    }
    
    public int getStationType()
    {
        for(int i=0;i<properties.size();i++)
        {
            if(properties.get(i)>20&&properties.get(i)<50)
            {
                return properties.get(i);
            }
        }
        return -1;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Integer> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isAutoAnimate() {
        return autoAnimate;
    }

    public void setAutoAnimate(boolean autoAnimate) {
        this.autoAnimate = autoAnimate;
    }
    
    
    
    
    
}
