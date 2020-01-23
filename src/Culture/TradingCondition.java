/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import org.json.simple.JSONObject;

/**
 *
 * @author Timot
 */
public class TradingCondition
{
    private int type,min,max;
    private double priceMultiplier;
    
    private boolean buying,selling;
    
    
    public TradingCondition(JSONObject jsonObj)
    {
        this.type = (int)(long)jsonObj.get("type");
        
        String buy = (String)jsonObj.get("buying");
        if(buy.equals("yes"))
        {
            buying = true;
        }else
        {
            buying = false;
        }
        
        String sell = (String)jsonObj.get("selling");
        if(sell.equals("yes"))
        {
            selling = true;
        }else
        {
            selling = false;
        }
        
        this.priceMultiplier = (double)jsonObj.get("price_multiplier");
        
        this.min = (int)(long)jsonObj.get("minimum_stock");
        this.max = (int)(long)jsonObj.get("maximum_stock");
        
        
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getMin()
    {
        return min;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public double getPriceMultiplier()
    {
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier)
    {
        this.priceMultiplier = priceMultiplier;
    }

    public boolean isBuying()
    {
        return buying;
    }

    public void setBuying(boolean buying)
    {
        this.buying = buying;
    }

    public boolean isSelling()
    {
        return selling;
    }

    public void setSelling(boolean selling)
    {
        this.selling = selling;
    }
    
    
    
}
