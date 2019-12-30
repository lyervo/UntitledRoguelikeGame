/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import org.json.simple.JSONObject;

/**
 *
 * @author Timot
 */
public class HarvestResult
{
    private int minAmount,maxAmount;
    private String itemName;
    
    public HarvestResult(JSONObject jsonObj)
    {
        minAmount = (int)(long)jsonObj.get("minAmount");
        if(jsonObj.get("maxAmount")!=null)
        {
            maxAmount = (int)(long)jsonObj.get("maxAmount");
            if(maxAmount<minAmount)
            {
                System.out.println("Invalid maxAmount value for plant");
                System.exit(-1);
            }
        }else
        {
            maxAmount = minAmount;
        }
        itemName = (String)jsonObj.get("itemName");
    }

    public int getMinAmount()
    {
        return minAmount;
    }

    public void setMinAmount(int minAmount)
    {
        this.minAmount = minAmount;
    }

    public int getMaxAmount()
    {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount)
    {
        this.maxAmount = maxAmount;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    
    
}
