/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Timot
 */
public class Material 
{
    private String name;
    private ArrayList<Effect> multipliers;
    private ArrayList<Integer> properties;
    private ItemColour itemColor;
    
    public Material(JSONObject jsonObj,ItemColour itemColor)
    {
        this.itemColor = itemColor;
        this.name = (String)jsonObj.get("name");
        
        this.multipliers = new ArrayList<Effect>();
        this.properties = new ArrayList<Integer>();
        
        
        JSONArray mulArr = (JSONArray)jsonObj.get("effect");
        
        for(int i=0;i<mulArr.size();i++)
        {
            JSONObject mulObj = (JSONObject)mulArr.get(i);
            multipliers.add(new Effect(Double.parseDouble((String)mulObj.get("value")),Integer.parseInt((String)mulObj.get("duration")),(String)mulObj.get("type")));
        }
        
        JSONArray proArr = (JSONArray)jsonObj.get("properties");
        for(int i=0;i<proArr.size();i++)
        {
            properties.add(Integer.parseInt((String)proArr.get(i)));
        }
        
        
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Effect> getMultipliers() {
        return multipliers;
    }

    public void setMultipliers(ArrayList<Effect> multipliers) {
        this.multipliers = multipliers;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Integer> properties) {
        this.properties = properties;
    }

    public ItemColour getItemColor() {
        return itemColor;
    }

    public void setItemColor(ItemColour itemColor) {
        this.itemColor = itemColor;
    }
    
    
    
    
    
}
