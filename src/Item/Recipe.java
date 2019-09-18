/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import java.util.ArrayList;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class Recipe
{
    private String name;
    private int amount;
    private ArrayList<Pair<String,Integer>> ingredients;
    private ArrayList<Integer> stations;
    private ArrayList<Pair<String,Integer>> requirements;
    private boolean learnt;
    private String type;
    private Image texture;
    private int id;
    
    private ArrayList<Pair<String,Integer>> byProducts;
    
    public Recipe(JSONObject jsonObj,ItemLibrary itemLibrary,int id)
    {
        this.id = id;
        ingredients = new ArrayList<Pair<String,Integer>>();
        stations = new ArrayList<Integer>();
        requirements = new ArrayList<Pair<String,Integer>>();
        
        name = (String)jsonObj.get("name");
        
        amount = Integer.parseInt((String)jsonObj.get("amount"));

        JSONArray ingArr = (JSONArray)jsonObj.get("ingredients");
        if(ingArr!=null)
        {
            for(int i=0;i<ingArr.size();i++)
            {
                JSONObject ingObj = (JSONObject)ingArr.get(i);
              
                ingredients.add(new Pair((String)ingObj.get("name"),Integer.parseInt((String)ingObj.get("consumed"))));
            }
        }
        
        JSONArray staArr = (JSONArray)jsonObj.get("station");
        if(staArr!=null)
        {
            for(int i=0;i<staArr.size();i++)
            {
                JSONObject staObj = (JSONObject)staArr.get(i);
                stations.add(Integer.parseInt((String)staObj.get("type")));
            }
        }
        
        JSONArray reqArr = (JSONArray)jsonObj.get("requirements");
        if(reqArr!=null)
        {
            for(int i=0;i<reqArr.size();i++)
            {
                JSONObject reqObj = (JSONObject)reqArr.get(i);
                requirements.add(new Pair((String)reqObj.get("type"),Integer.parseInt((String)reqObj.get("level"))));
            }
        }
        
        byProducts = new ArrayList<Pair<String,Integer>>();
        JSONArray proArr = (JSONArray)jsonObj.get("by_product");
        if(proArr!=null)
        {
            for(int i=0;i<proArr.size();i++)
            {
                JSONObject proObj = (JSONObject)proArr.get(i);
                byProducts.add(new Pair((String)proObj.get("name"),Integer.parseInt((String)proObj.get("amount"))));
            }
        }

        
       
        
        texture = itemLibrary.getItemByTrueName(name).getTexture();
        
        learnt = true;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<Pair<String, Integer>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Pair<String, Integer>> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Integer> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Integer> stations) {
        this.stations = stations;
    }

    public ArrayList<Pair<String, Integer>> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<Pair<String, Integer>> requirements) {
        this.requirements = requirements;
    }

    public boolean isLearnt() {
        return learnt;
    }

    public void setLearnt(boolean learnt) {
        this.learnt = learnt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Pair<String, Integer>> getByProducts() {
        return byProducts;
    }

    public void setByProducts(ArrayList<Pair<String, Integer>> byProducts) {
        this.byProducts = byProducts;
    }
    
    
    
    
}
