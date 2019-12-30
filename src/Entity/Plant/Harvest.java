/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Timot
 */
public class Harvest
{
    //the amount of worked and the maximum amount of work before a harvest of
    //a plant is finished
    //current will be depleted if entity did not attempt to harvest it the next turn
    private double current,max;
    
    //does the plant dies and removed from the map after harvest
    private boolean kill;
    
    //does the harvest action decrement the stage of the plant
    //this option creates reharvestable plants like fruit tree
    private boolean stageRevert;
    
    //the minimum stage required for the harvest
    private int stage;
    
    //the tools that can be use for the harvesting action
    private int[] tool;
    
    //the resulting items
    private ArrayList<HarvestResult> harvestResult;
    
    
    public Harvest(JSONObject jsonObj)
    {
        JSONArray toolArr = (JSONArray)jsonObj.get("tool");
        tool = new int[toolArr.size()];
        
        for(int i=0;i<toolArr.size();i++)
        {
            tool[i] = (int)(long)toolArr.get(i);
        }
        
        int postHarvest = (int)(long)jsonObj.get("postHarvest");
        if(postHarvest == -1)
        {
            kill = true;
            stageRevert = false;
        }else if(postHarvest == 1)
        {
            kill = false;
            stageRevert = true;
        }else
        {
            kill = false;
            stageRevert = false;
        }
        
        current = 0;
        max = (double)(int)(long)jsonObj.get("harvestMeter");
        
        harvestResult = new ArrayList<HarvestResult>();
        
        JSONArray productArr = (JSONArray)jsonObj.get("products");
        for(int i=0;i<productArr.size();i++)
        {
            JSONObject productObj = (JSONObject)productArr.get(i);
            harvestResult.add(new HarvestResult(productObj));
        }
        
        
    }

    public void depleteCurrent()
    {
        if(current>=1)
        {
            current--;
        }
    }
    
    public void harvest(int progress)
    {
        current += progress;
    }
    
    public boolean finishedHarvest()
    {
        return current>=max;
    }
    
    public double getCurrent()
    {
        return current;
    }

    public void setCurrent(double current)
    {
        this.current = current;
    }

    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public boolean isKill()
    {
        return kill;
    }

    public void setKill(boolean kill)
    {
        this.kill = kill;
    }

    public boolean isStageRevert()
    {
        return stageRevert;
    }

    public void setStageRevert(boolean stageRevert)
    {
        this.stageRevert = stageRevert;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    public int[] getTool()
    {
        return tool;
    }

    public void setTool(int[] tool)
    {
        this.tool = tool;
    }

    public ArrayList<HarvestResult> getHarvestResult()
    {
        return harvestResult;
    }

    public void setHarvestResult(ArrayList<HarvestResult> harvestResult)
    {
        this.harvestResult = harvestResult;
    }
    
    
    
    
}
