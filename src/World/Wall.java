/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class Wall
{
    
    private boolean solid;
    protected int x,y;
    protected Image texture;
    protected int type;
    
    public Wall(int x, int y, int type, Image image)
    {
        this.x = x;
        this.y = y;
        this.texture = image;
        this.type = type;
        this.solid = true;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
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

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
}
