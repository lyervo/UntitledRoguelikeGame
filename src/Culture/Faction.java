/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Timot
 */
public class Faction
{
    //faction example:
    //Main faction: Kingdom of Elinar
    //Sub-faction: Family of Chalice , the Alchemy Shop
    //A pawn can only have 1 main-faction: a citizenship of a country or 
    //being in an influential organization such as a bandit band, dark cult etc
    //
    //however, the pawn will be able to have multiple subfaction, such as their family,
    //the business they are running or being an apprentice in, or the school they are 
    //attending or the knight order
    
    //base on the faction of the pawn and player, interactions will be different
    //such as you must be a family member to enter a house of a family
    //enter a shop or farmland only if you work or owns it
    //have access to the school, guild, order's service if you are part of it
    //and if you are well known to be affliated with a faction, hostile factions
    //will attack you on sight and friendly factions will like you more
    
    private String factionName;
    
    private HashMap<String,Integer> relationship;
    
    public Faction(String factionName)
    {
        this.factionName = factionName;
    }

    public String getFactionName()
    {
        return factionName;
    }

    public void setFactionName(String factionName)
    {
        this.factionName = factionName;
    }

    public HashMap<String, Integer> getRelationship()
    {
        return relationship;
    }

    public void setRelationship(HashMap<String, Integer> relationship)
    {
        this.relationship = relationship;
    }
    
    
    
    
}
