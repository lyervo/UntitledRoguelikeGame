/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Res.Res;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Timot
 */
public class ItemLibrary
{
    private String seed;
    
  
    private HashMap<String,Item> items;
    
    private Res res;
    
    private HashMap<String,Boolean> identify;
    
    private boolean[] learntRecipe;
    
    private ArrayList<Recipe> recipes;
    
    private ArrayList<ItemType> types;
    
    private ArrayList<Material> materials;
    
    private ArrayList<ItemColour> colours;
    
    private ArrayList<Item> itemPreload;
    
    public ItemLibrary(String seed,Res res)
    {
        this.res = res;
        this.seed = seed;
        this.items = new HashMap<String,Item>();
        this.recipes = new ArrayList<Recipe>();
        this.types = new ArrayList<ItemType>();
        this.materials = new ArrayList<Material>();
        this.colours = new ArrayList<ItemColour>();
        this.identify = new HashMap<String,Boolean>();
        this.itemPreload = new ArrayList<Item>();
        
        initItemColour();
        initPotions();
        initMaterials();
        
        initItems();
        initRecipes();
        initItemType();
        
    }
    
    public void initItemColour()
    {
        try {
            
            File jsonFile = new File("res/data/colours.json");
            Scanner jsonReader;
            jsonReader = new Scanner(jsonFile);
            String jsonString = "";
            while(jsonReader.hasNext())
            {
                jsonString += jsonReader.nextLine();
            }
            JSONParser jsonParser = new JSONParser();
          
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            for(int i=0;i<jsonArr.size();i++)
            {
                JSONObject jsonObj = (JSONObject)jsonArr.get(i);
                colours.add(new ItemColour(jsonObj));
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException e)
        {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    public void initMaterials()
    {
        try {
            
            File jsonFile = new File("res/data/materials.json");
            Scanner jsonReader;
            jsonReader = new Scanner(jsonFile);
            String jsonString = "";
            while(jsonReader.hasNext())
            {
                jsonString += jsonReader.nextLine();
            }
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            
            Collections.shuffle(colours);
            
            
            for(int i=0;i<jsonArr.size();i++)
            {
                JSONObject jsonObj = (JSONObject)jsonArr.get(i);
               
                materials.add(new Material(jsonObj,colours.get(i)));
                Image materialTexture = paintPotion(res.metal_bar_template,colours.get(i));
                
                getItemByTrueName(materials.get(i).getName()+" Bar").setTexture(materialTexture);
                
                
                
                
                
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException e)
        {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void initItemType()
    {
        try {
            
            File jsonFile = new File("res/data/item_type.json");
            Scanner jsonReader;
            jsonReader = new Scanner(jsonFile);
            String jsonString = "";
            while(jsonReader.hasNext())
            {
                jsonString += jsonReader.nextLine();
            }
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            for(int i=0;i<jsonArr.size();i++)
            {
                JSONObject jsonObj = (JSONObject)jsonArr.get(i);
                types.add(new ItemType(jsonObj,res));
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException e)
        {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Image getRecipeTemplateTextureByName(String recipeName)
    {
        for(Recipe r:recipes)
        {
            if(r.getName().equals(recipeName))
            {
                return r.getTemplate_texture();
            }
        }
        return null;
    }
    
    public void initRecipes()
    {
       
        
        
        try {
            
            File jsonFile = new File("res/data/recipe.json");
            Scanner jsonReader;
            jsonReader = new Scanner(jsonFile);
            String jsonString = "";
            while(jsonReader.hasNext())
            {
                jsonString += jsonReader.nextLine();
            }
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            for(int i=0;i<jsonArr.size();i++)
            {
                JSONObject jsonObj = (JSONObject)jsonArr.get(i);
                recipes.add(new Recipe(jsonObj,this,i));
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException e)
        {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, e);
        }
        
        learntRecipe = new boolean[recipes.size()];
        Arrays.fill(learntRecipe, false);
            
    }
    
    
    public void initPotions()
    {
        ArrayList<Item> potionPreload = new ArrayList<Item>();
        
        try {
            
            File jsonFile = new File("res/data/item.json");
            Scanner jsonReader = new Scanner(jsonFile);
            String jsonString = "";
            while(jsonReader.hasNext())
            {
                jsonString += jsonReader.nextLine();
            }
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            for(int i=0;i<jsonArr.size();i++)
            {
                JSONObject jsonObj = (JSONObject)jsonArr.get(i);
                if(jsonObj.get("category").equals("potion"))
                {
                    potionPreload.add(new Item(jsonObj,this));
                    if(jsonObj.get("texture")!=null)
                    {
                        potionPreload.get(potionPreload.size()-1).setTexture(res.getTextureByName((String)jsonObj.get("texture")));
                    }
                }else if(jsonObj.get("category").equals("metal_material"))
                {
                    Item m = new Item(jsonObj,this);
                    items.put(m.getTrueName(), m);
                }else
                {
                    Item newItem = new Item(jsonObj,this);
                    
                    if(jsonObj.get("texture")!=null)
                    {
                        newItem.setTexture(res.getTextureByName((String)jsonObj.get("texture")));
                    }
                    
                    itemPreload.add(newItem);
                }
                
                
                
            }
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        

            Collections.shuffle(colours);
            
            
            
            
            
            
            
            for(int i=0;i<potionPreload.size();i++)
            {
                
                Image temp = new Image(res.potion_template.getTexture());
                
                temp = paintPotion(temp,colours.get(i));
                
                if(potionPreload.get(i).getTexture()==null)
                {
                    potionPreload.get(i).setTexture(temp);
                    potionPreload.get(i).setName(colours.get(i).getName()+" Potion");
                    if(potionPreload.get(i).getUnidentified_desc()!=null)
                    {
                        String new_desc = potionPreload.get(i).getUnidentified_desc().replace("<color>", colours.get(i).getName());
                        potionPreload.get(i).setUnidentified_desc(new_desc);
                    }
                }
                
                items.put(potionPreload.get(i).getTrueName(), potionPreload.get(i));
                identify.put(potionPreload.get(i).getTrueName(), Boolean.FALSE);
          
                
                
            }
            
            
        
        
    }
    
    public void initItems()
    {
        for (int i = 0; i < itemPreload.size(); i++)
        {
            if (itemPreload.get(i).getTrueName().startsWith("<metal>"))
            {
                items.put(itemPreload.get(i).getTrueName(), itemPreload.get(i));

                for (Material m : materials)
                {
                    Item newItem = new Item(itemPreload.get(i));
                    String[] splitter = newItem.getTrueName().split(" ");
                    String templateName = "";
                    for(int z =1;z<splitter.length;z++)
                    {
                        templateName+=splitter[z];
                    }
                    newItem.setMetalMaterial(this, m,res.getTextureByName((templateName+"_template").toLowerCase()) , newItem.getTrueName());
                    items.put(newItem.getTrueName(), newItem);
                }
            } else
            {
                items.put(itemPreload.get(i).getTrueName(), itemPreload.get(i));
            }
        }
        itemPreload.clear();
    }
    
    public void learnRecipeByName(String name)
    {
        for(int i=0;i<recipes.size();i++)
        {
            if(recipes.get(i).getRecipeName().equals(name))
            {
                learntRecipe[i] = true;
            }
        }
    }
    
    public Image paintPotion(Image img,ItemColour colour)
    {
        try {
            Image image = new Image(img.getTexture());
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
    
    
    //this will get the item by it's type name instead of it's randomly generated name
    public Item getItemByTrueName(String trueName)
    {
        
        return items.get(trueName); 
    }
    
    public Item getItemByName(String name)
    {
        Item a = items.get(name);
        if(a == null)
        {
            for(Map.Entry e:items.entrySet())
            {
                String unidentifiedName = ((Item)e.getValue()).getUnidentifiedName();
                if(unidentifiedName!=null)
                {
                    if(unidentifiedName.equals(name))
                    {
                        return ((Item)e.getValue());
                    }
                }
            }
        }else
        {
            return a;
        }
        return null;
    }
    
    public Material getMaterialByName(String materialName)
    {
        for(Material m:materials)
        {
            if(m.getName().equals(materialName))
            {
                return m;
            }
        }
        return null;
    }
    
    public boolean checkIdentified(String trueName)
    {
        if(identify.get(trueName)!=null)
        {
            return identify.get(trueName);
        }
        //if item is not on the identify list, return as true;
        return true;
    }
    
    public void identify(String trueName)
    {
        
        identify.put(trueName, Boolean.TRUE);
    }
    
    public Recipe getRecipeById(int id)
    {
        for(Recipe r:recipes)
        {
            if(r.getId() == id)
            {
                return r;
            }
        }
        return null;
    }
    
    public Recipe getRecipeByName(String recipeName)
    {
        for(Recipe r:recipes)
        {
            if(r.getName().equals(recipeName))
            {
                return r;
            }
        }
        return null;
    }
    
    public ItemType getItemTypeByName(String name)
    {
        for(ItemType i:types)
        {
            if(i.getName().equals(name))
            {
                return i;
            }
        }
        return null;
    }
    
    public ItemType getItemTypeByType(int type)
    {
        for(ItemType i:types)
        {
            if(i.getType()==type)
            {
                return i;
            }
        }
        return null;
    }
    

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<ItemType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<ItemType> types) {
        this.types = types;
    }

    public boolean[] getLearntRecipe() {
        return learntRecipe;
    }

    public void setLearntRecipe(boolean[] learntRecipe) {
        this.learntRecipe = learntRecipe;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public HashMap<String, Boolean> getIdentify() {
        return identify;
    }

    public void setIdentify(HashMap<String, Boolean> identify) {
        this.identify = identify;
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Material> materials) {
        this.materials = materials;
    }

    public ArrayList<ItemColour> getColours() {
        return colours;
    }

    public void setColours(ArrayList<ItemColour> colours) {
        this.colours = colours;
    }
    
    
    
    
}
