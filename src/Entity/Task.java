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
public class Task
{
    private int x,y,id,index;
    private String type;
    
    private Entity target;
    private String info;
    private int priority;
    
    //type
    //nothing
    //grab_item
    //follow_target
    //craft
    //attack
    
    
    public Task(int x, int y, int id, int index,String type) 
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.index = index;
        this.type = type;
    }

    public void clearTask()
    {
        this.x = 0;
        this.y = 0;
        this.id = 0;
        this.index = 0;
        this.target = null;
        this.type = "nothing";
    }
    
    public void setCordsToTarget()
    {
        x = target.getX();
        y = target.getY();
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
    
}
