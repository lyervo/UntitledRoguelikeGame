/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import World.Zone;
import java.util.ArrayList;

/**
 *
 * @author Timot
 */
public class SubFaction
{
    private String subFactionName;
    
    private Faction faction;
    
    private ArrayList<Zone> zones;
    
    public SubFaction(String subFactionName,Faction faction)
    {
        this.subFactionName = subFactionName;
        this.faction = faction;
        zones = new ArrayList<Zone>();
    }

    public String getSubFactionName()
    {
        return subFactionName;
    }

    public void setSubFactionName(String subFactionName)
    {
        this.subFactionName = subFactionName;
    }

    public Faction getFaction()
    {
        return faction;
    }

    public void setFaction(Faction faction)
    {
        this.faction = faction;
    }

    public ArrayList<Zone> getZones()
    {
        return zones;
    }

    public void setZones(ArrayList<Zone> zones)
    {
        this.zones = zones;
    }
    
    
    
}
