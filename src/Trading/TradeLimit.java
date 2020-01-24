/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import Culture.TradingCondition;

/**
 *
 * @author Timot
 */
public class TradeLimit
{
    private int type,min,max,count;
    
    public TradeLimit(TradingCondition tc)
    {
        this.type = tc.getType();
        this.min = tc.getMin();
        this.max = tc.getMax();
        this.count = 0;
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
    
    public void addCount(int count)
    {
        this.count += count;
    }
    
    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

 
    
    
    
    
}
