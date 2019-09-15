/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Timot
 */
public class Meter
{
    private double max,value,depleteRate;
    private String name;
    
    public Meter(String name, double max, double value, double depleteRate) {
        this.name = name;
        this.max = max;
        this.value = value;
        this.depleteRate = depleteRate;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getValue() {
        return value;
    }
    
    public void deplete()
    {
        this.value += depleteRate;
        if(value>max)
        {
            value = max;
        }else if(value<0)
        {
            value = 0;
        }
    }

    public void addValue(double value)
    {
        this.value += value;
        if(this.value>max)
        {
            this.value = max;
        }else if(this.value<0)
        {
            this.value = 0;
        }
        
    }
    
    public void setValue(double value) {
        this.value = value;
        if(this.value>max)
        {
            this.value = max;
        }else if(value<0)
        {
            this.value = 0;
        }
    }

    public double getDepleteRate() {
        return depleteRate;
    }

    public void setDepleteRate(double depleteRate) {
        this.depleteRate = depleteRate;
    }
    
    
    
    
}
