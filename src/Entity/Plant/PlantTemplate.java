/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import Res.Res;
import java.util.ArrayList;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Timot
 */
public class PlantTemplate
{
    //growth is increased by 1 each turn when all the conditions of the plant is met
    //additional effect such as fertilizer can use to boost it's growth
    private final double MAXGROWTH;
    
    
    //name of the plant at each STAGEs
    private final ArrayList<String> NAMES;
    
    
    
    private final ArrayList<Harvest> HARVEST;
    
   
    
    private final SpriteSheet SPRITES;
    
    //stating the number of growth each STAGE need to advance to the next or final STAGE
    private final int[] STAGEGROWTHS;
    
    //whether a plant is solid at each stages
    private final boolean[] SOLIDS;

    
    //whether this plant requires SUNLIGHT to grow
    private final boolean SUNLIGHT;
    //whether this plant requires WATERing to grow
    private final boolean WATER;
    
    //types of GROUND this plant grows in
    private final int[] GROUND;
    
    public PlantTemplate(JSONObject jsonObj,Res res)
    {
        JSONArray STAGEArr = (JSONArray)jsonObj.get("stage");
        
        NAMES = new ArrayList<String>();
        HARVEST = new ArrayList<Harvest>();
        STAGEGROWTHS = new int[STAGEArr.size()];
        SOLIDS = new boolean[STAGEArr.size()];
        
        
        
        int max = 0;
        
        for(int i=0;i<STAGEArr.size();i++)
        {
            JSONObject STAGEObj = (JSONObject)STAGEArr.get(i);
            NAMES.add((String)STAGEObj.get("name"));
            if(i==0)
            {
                STAGEGROWTHS[i] = 0;
            }else
            {
                STAGEGROWTHS[i] = (int)(long)STAGEObj.get("growth")+STAGEGROWTHS[i-1];
            }
            max+=STAGEGROWTHS[i];
            JSONArray harvestArr = (JSONArray)STAGEObj.get("harvest");
            for(int j=0;j<harvestArr.size();j++)
            {
                JSONObject harvestObj = (JSONObject)harvestArr.get(j);
                HARVEST.add(new Harvest(harvestObj));
            }
            
            if((int)(long)STAGEObj.get("solid") == 0)
            {
                SOLIDS[i] = false;
            }else
            {
                SOLIDS[i] = true;
            }
            
            
        }
        
        MAXGROWTH = max;
        
        if(jsonObj.get("sunlight")!=null)
        {
            if((int)(long)jsonObj.get("sunlight") == 1)
            {
                SUNLIGHT = true;
            }else
            {
                SUNLIGHT = false;
            }
        }else
        {
            SUNLIGHT = false;
        }
        
        
        if(jsonObj.get("water")!=null)
        {
            if((int)(long)jsonObj.get("water") == 1)
            {
                WATER = true;
            }else
            {
                WATER = false;
            }
        }else
        {
            WATER = false;
        }
        
        JSONArray GROUNDArr = (JSONArray)jsonObj.get("ground");
        
        GROUND = new int[GROUNDArr.size()];
        for(int i=0;i<GROUNDArr.size();i++)
        {
            GROUND[i] = (int)(long)GROUNDArr.get(i);
        }
        
        SPRITES = res.getSpriteByName((String)jsonObj.get("texture"));
        
    }


    public String getName()
    {
        return NAMES.get(NAMES.size()-1);
    }

    public double getMaxGrowth()
    {
        return MAXGROWTH;
    }


    public ArrayList<String> getNames()
    {
        return NAMES;
    }


    public ArrayList<Harvest> getHarvest()
    {
        return HARVEST;
    }


    public SpriteSheet getSprites()
    {
        return SPRITES;
    }


    public int[] getStageGrowths()
    {
        return STAGEGROWTHS;
    }


    public boolean isSunlight()
    {
        return SUNLIGHT;
    }


    public boolean isWater()
    {
        return WATER;
    }



    public int[] getGROUND()
    {
        return GROUND;
    }

    public double getMAXGROWTH()
    {
        return MAXGROWTH;
    }

    public ArrayList<String> getNAMES()
    {
        return NAMES;
    }

    public ArrayList<Harvest> getHARVEST()
    {
        return HARVEST;
    }

    public SpriteSheet getSPRITES()
    {
        return SPRITES;
    }

    public int[] getSTAGEGROWTHS()
    {
        return STAGEGROWTHS;
    }

    public boolean[] getSOLIDS()
    {
        return SOLIDS;
    }

    public boolean isSUNLIGHT()
    {
        return SUNLIGHT;
    }

    public boolean isWATER()
    {
        return WATER;
    }

    
    
    
    
}
