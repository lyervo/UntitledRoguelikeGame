/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Res.Res;
import org.json.simple.JSONObject;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class ItemType
{
    private String name,desc;
    private int type;
    private Image texture;
    
    public ItemType(String name,String desc,int type,Image texture)
    {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.texture = texture;
    }
    
    public ItemType(JSONObject jsonObj,Res res)
    {
        this.name = (String)jsonObj.get("name");
        this.desc = (String)jsonObj.get("desc");
        this.type = Integer.parseInt((String)jsonObj.get("type"));
        this.texture = res.getTextureByName((String)jsonObj.get("texture"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }
    
    
    
    
}
