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
public class IngredientRequirement
{
    private String item;
    private int consume;
    private int genericType;
    
    
    public IngredientRequirement(String item,int consume)
    {
        this.item = item;
        this.consume = consume;
        this.genericType = 0;
    }
    
    public IngredientRequirement(String item,int consume,int genericType)
    {
        this.item = item;
        this.consume = consume;
        this.genericType = genericType;
    }

    public String getItem()
    {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public int getGenericType() {
        return genericType;
    }

    public void setGenericType(int genericType) {
        this.genericType = genericType;
    }
    
    
    
    
}
