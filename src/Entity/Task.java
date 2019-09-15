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
    private int x,y,id,index,type;
    //type
    //0 = wander
    //1 = follow
    //2 = get item
    
    
    
    public Task(int x, int y, int id, int index, int type) 
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.index = index;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
}
