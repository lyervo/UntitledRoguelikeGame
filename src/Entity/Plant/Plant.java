/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import Camera.Camera;
import Culture.SubFaction;
import Entity.Entity;
import Entity.Pawn;
import Item.Item;
import World.LocalMap;
import World.Tile;
import World.World;
import World.Zone;
import java.util.ArrayList;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import java.util.Random;

/**
 *
 * @author Timot
 */
public class Plant extends Entity
{
    
    private PlantTemplate plantTemplate;
    
    private int stage,currentGrowth;
    
    private boolean disturbed;
    
    private ArrayList<Harvest> harvests;
    
    private Tile tile;
    
    private String subFaction;
    
    public Plant(int id, int x, int y, PlantTemplate template,Tile tile)
    {
        super(id, x, y, template.getSprites(), false);
        this.plantTemplate = template;
        stage = 0;
        currentGrowth = 0;
        disturbed = false;
        this.harvests = new ArrayList<Harvest>(template.getHarvest());
        this.tile = tile;
    }
    
    
    public void harvest(Item tool,Pawn user,World world)
    {
        for(Integer i:harvests.get(stage).getTool())
        {
            if(tool.getProperties().contains(i))
            {
                harvests.get(stage).harvest((int)tool.getEffectByName("tool_efficiency").getValue());
                
                if(harvests.get(stage).finishedHarvest())
                {
                    
                    for(HarvestResult r :harvests.get(stage).getHarvestResult())
                    {
                        Item produce = world.getItemLibrary().getItemByName(r.getItemName());
                        Random rand = new Random();
                        int resultAmount = 0;
                        if(r.getMaxAmount() == r.getMinAmount())
                        {
                            resultAmount = r.getMinAmount();
                        }else
                        {
                            resultAmount = r.getMinAmount() + rand.nextInt(r.getMaxAmount()-r.getMinAmount()+1);
                        }
                        if(resultAmount>=1)
                        {
                            if(produce.isStackable())
                            {
                                produce.setStack(resultAmount);
                                if(user.isControl())
                                {
                                    world.getWm().getPlayerInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                                }else
                                {
                                    user.getInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                                }
                            }else
                            {
                                for(int a=0;a<resultAmount;a++)
                                {
                                    if(user.isControl())
                                    {
                                        world.getWm().getPlayerInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                                    }else
                                    {
                                        user.getInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                                    }
                                }
                            }
                        }
                        
                    }
                    
                    if(user.isControl())
                    {
                        world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                    }
                    
                    resetHarvest();
                    
                    if(harvests.get(stage).isStageRevert())
                    {
                        stage--;
                        int totalR = 0;
                        for(int t=0;t<stage-1;t++)
                        {
                            totalR += plantTemplate.getSTAGEGROWTHS()[t];
                        }
                        currentGrowth = totalR;
                        
                    }else if(harvests.get(stage).isKill())
                    {
                        System.out.println("call");
                        tile.clearPlant();
                    }
                }else
                {
                    disturbed = true;
                }
            }
        }
    }
    

    public int[] getCurrentTools()
    {
        return harvests.get(stage).getTool();
    }
    
    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        if(world.isMoved())
        {
            if(!disturbed)
            {
                if(currentGrowth<plantTemplate.getMaxGrowth())
                {
                    currentGrowth++;
                
                    for(int i=1;i<plantTemplate.getStageGrowths().length;i++)
                    {
                        if(currentGrowth>=plantTemplate.getStageGrowths()[i])
                        {
                            stage = i;
                            if(plantTemplate.getSOLIDS()[stage]&&distanceBetween(world.getWm().getPlayer())<6)
                            {
                                world.getWm().getPlayer().resetVisit(world);
                            }

                        }else
                        {
                            break;
                        }
                    }
                }
                for(Harvest h:harvests)
                {
                    h.depleteCurrent();
                }
            }else
            {
                
            }
            disturbed = false;
        }
    }
    
    public Harvest getCurrentHarvest()
    {
        return harvests.get(stage);
    }
    
    public void resetHarvest()
    {
        for(Harvest h:harvests)
        {
            h.setCurrent(0);
        }
    }
    
    public String getCurrentName()
    {
        return plantTemplate.getNAMES().get(stage);
    }
    
    public boolean isFinishedGrowing()
    {
        return stage == plantTemplate.getSTAGEGROWTHS().length-1;
    }
    
    @Override
    public void render(Camera cam,LocalMap map,boolean animate)
    {
        if(tile.isVisit())
        {
            if(sprites == null)
            {
                texture.draw(x*cam.getTile_size()+cam.getXofs(),y*cam.getTile_size()+cam.getYofs(),cam.getTile_size(),cam.getTile_size());
            }else
            {

                sprites.getSprite(stage,0).draw(x*cam.getTile_size()+cam.getXofs(),y*cam.getTile_size()+cam.getYofs(),cam.getTile_size(),cam.getTile_size());



            }
        }
    }
    
    public void setSubFaction(String subFaction)
    {
        this.subFaction = subFaction;
    }
    
    public boolean getCurrentSolid()
    {
        return plantTemplate.getSOLIDS()[stage];
    }

    @Override
    public boolean hasItem(String name)
    {
        return false;
    }
    
    public boolean isSolid()
    {
        return plantTemplate.getSOLIDS()[stage];
    }

    public PlantTemplate getPlantTemplate()
    {
        return plantTemplate;
    }

    public void setPlantTemplate(PlantTemplate plantTemplate)
    {
        this.plantTemplate = plantTemplate;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    public int getCurrentGrowth()
    {
        return currentGrowth;
    }

    public void setCurrentGrowth(int currentGrowth)
    {
        this.currentGrowth = currentGrowth;
    }

    public boolean isDisturbed()
    {
        return disturbed;
    }

    public void setDisturbed(boolean disturbed)
    {
        this.disturbed = disturbed;
    }

    public ArrayList<Harvest> getHarvests()
    {
        return harvests;
    }

    public void setHarvests(ArrayList<Harvest> harvests)
    {
        this.harvests = harvests;
    }

    public Tile getTile()
    {
        return tile;
    }

    public void setTile(Tile tile)
    {
        this.tile = tile;
    }

    public String getSubFaction()
    {
        return this.subFaction;
    }
    

    
    
}
