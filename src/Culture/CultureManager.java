/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import Item.ItemColour;
import Item.ItemLibrary;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
public class CultureManager
{
    
    //the culture manager will be incharge of the cultural and political system of the game world
    //such as
    //name, art and legends generation
    //kingdoms and other factions
    //adding some history behind the game scene
    
    private HashMap<String,Faction> factions;
    
    private HashMap<String,SubFaction> subFactions;
    
    private HashMap<String,TradingBehavior> tradingBehaviors;
    
    public CultureManager()
    {
        factions = new HashMap<String,Faction>();
        subFactions = new HashMap<String,SubFaction>();
        tradingBehaviors = new HashMap<String,TradingBehavior>();
        init();
    }
    
    public void init()
    {
        factions.put("Kingdom of Augonn", new Faction("Kingdom of Augonn"));
        subFactions.put("player",new SubFaction("player",null,"player"));
        subFactions.put("Honest man farm", new SubFaction("Honest man farm",factions.get("Kingdom of Augonn"),"farm"));
        subFactions.put("House Hora", new SubFaction("Hora",factions.get("Kingdom of Augonn"),"family"));
        subFactions.put("Augonnian Army", new SubFaction("Augonnian Army",factions.get("Kingdom of Augonn"),"army"));
        initTradingBehavior();
        
        
    }
    
    public void initTradingBehavior()
    {
        System.out.println("initialize trading behavior");
        try {
            
            File jsonFile = new File("res/data/entity/trading_behavior.json");
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
                String jobName = (String)jsonObj.get("job");
                tradingBehaviors.put(jobName,new TradingBehavior(jsonObj));
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException e)
        {
            Logger.getLogger(ItemLibrary.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    public void createSubFaction(String name,String factionName,String type)
    {
        subFactions.put(name, new SubFaction(name,getFactionByName(factionName),type));
    }
    
    public void createFaction(String name)
    {
        factions.put(name, new Faction(name));
    }
    
    public TradingBehavior getTradingBehaviorByJob(String job)
    {
        return tradingBehaviors.get(job);
    }
    
    public SubFaction getSubFactionByName(String name)
    {
        return subFactions.get(name);
    }
    
    public Faction getFactionByName(String name)
    {
        return factions.get(name);
    }

    public HashMap<String, Faction> getFactions()
    {
        return factions;
    }

    public void setFactions(HashMap<String, Faction> factions)
    {
        this.factions = factions;
    }

    public HashMap<String, SubFaction> getSubFactions()
    {
        return subFactions;
    }

    public void setSubFactions(HashMap<String, SubFaction> subFactions)
    {
        this.subFactions = subFactions;
    }
    
    
    
    
    
    
}
