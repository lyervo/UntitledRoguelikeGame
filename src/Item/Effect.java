/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

/**
 *
 * @author Timot
 */
public class Effect
{
    private double value;
    private int duration;
    private String type;
    
    
    //types of effects
    //weapon_
    //pawn_
    //
    
    public Effect(double value,int duration,String type)
    {
        this.value = value;
        this.duration = duration;
        this.type = type;
    }

    public Effect(Effect effect)
    {
        this.value = effect.getValue();
        this.duration = effect.getDuration();
        this.type = effect.getType();
    }
    
    public void multiplyValue(double value)
    {
        this.value *= value;
    }
    
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void depleteDuration()
    {
        duration--;
    }
    
    
    
}
