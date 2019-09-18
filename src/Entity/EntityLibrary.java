/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Res.Res;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Timot
 */
public class EntityLibrary
{
    private Res res;
    
    private ArrayList<FurnitureTemplate> furnitures;
    
    private ArrayList<Creature> creatures;
    
    public EntityLibrary(Res res)
    {
        this.res = res;
        this.furnitures = new ArrayList<FurnitureTemplate>();
        initFurniture();
    }
    
    public void initFurniture()
    {
        
        try {
            File file = new File("res/data/furniture.json");
            Scanner fileReader = new Scanner(file);
            
            String jsonString = "";
            
            while(fileReader.hasNext())
            {
                jsonString+=fileReader.nextLine();
            }
            
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(jsonString);
            JSONArray jsonArr = (JSONArray)obj;
            
            for(int i=0;i<jsonArr.size();i++)
            {
                furnitures.add(new FurnitureTemplate((JSONObject)jsonArr.get(i),res));
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EntityLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EntityLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public FurnitureTemplate getFurnitureTemplateByName(String name)
    {
        for(FurnitureTemplate ft:furnitures)
        {
            if(ft.getName().equals(name))
            {
                return ft;
            }
        }
        
        return null;
    }
    
    public FurnitureTemplate getFurnitureTemplateByStationType(int type)
    {
        for(FurnitureTemplate ft:furnitures)
        {
            if(ft.getStationType()==type)
            {
                return ft;
            }
        }
        return null;
    }
    
    public Creature getCreatureByName(String name)
    {
        for(Creature c:creatures)
        {
            if(c.getName().equals(name))
            {
                return c;
            }
        }
        
        return null;
    }
    
    
}
