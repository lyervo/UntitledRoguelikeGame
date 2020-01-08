/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

/**
 *
 * @author Timot
 */
public class Relationship
{
    private String subFaction;
    private int goodwill;
    public Relationship(String subFaction,int goodwill)
    {
        this.goodwill = goodwill;
        this.subFaction = subFaction;
    }

    public int getGoodwill()
    {
        return goodwill;
    }

    public void setGoodwill(int goodwill)
    {
        this.goodwill = goodwill;
    }

    public String getSubFaction()
    {
        return subFaction;
    }

    public void setSubFaction(String subFaction)
    {
        this.subFaction = subFaction;
    }
    
    
    
    
    
    
    
}
