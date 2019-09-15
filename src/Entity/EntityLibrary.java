/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Res.Res;
import java.util.ArrayList;

/**
 *
 * @author Timot
 */
public class EntityLibrary
{
    private Res res;
    
    
    private ArrayList<Creature> creatures;
    
    public EntityLibrary(Res res)
    {
        this.res = res;
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
