/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Item.Effect;
import World.LocalMap;
import World.World;

/**
 *
 * @author Timot
 */
public class Status
{
    private Effect effect;
    
    public Status(Effect effect)
    {
        this.effect = new Effect(effect);
    }
    
    public void tick(World world,LocalMap lm,Pawn pawn)
    {
        if(world.isMoved()&&effect.getDuration()>=1)
        {
            switch(effect.getType())
            {
                case "heal":
                    System.out.println("heal");
                    pawn.getHp().addValue(effect.getValue());
                    effect.depleteDuration();
                    break;
                case "poison":
                    if(effect.getDuration()>0)
                    {
                        pawn.getHp().addValue(-effect.getValue());
                        effect.depleteDuration();
                    }
                    break;
                case "cure_poison":
                    for(Status s:pawn.getStatus())
                    {
                        if(s.getEffect().getType().equals("poison"))
                        {
                            s.getEffect().setDuration(0);
                        }
                    }
                    effect.depleteDuration();
                    break;
            }
            
        }
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    
    
}
