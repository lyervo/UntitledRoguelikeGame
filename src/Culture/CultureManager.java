/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import java.util.HashMap;

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
    
    public CultureManager()
    {
        factions = new HashMap<String,Faction>();
        subFactions = new HashMap<String,SubFaction>();
        init();
    }
    
    public void init()
    {
        factions.put("Kingdom of Augonn", new Faction("Kingdom of Augonn"));
        subFactions.put("Honest man farm", new SubFaction("Honest man farm",factions.get("Kingdom of Augonn")));
        subFactions.put("House Hora", new SubFaction("House Hora",factions.get("Kingdom of Augonn")));
        
    }
    
    public void createSubFaction(String name,String factionName)
    {
        subFactions.put(name, new SubFaction(name,getFactionByName(factionName)));
    }
    
    public void createFaction(String name)
    {
        factions.put(name, new Faction(name));
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
