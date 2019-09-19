/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import org.json.simple.JSONObject;

/**
 *
 * @author Timot
 */
public class ItemColour
{
    private String name,first,second,third,fourth;
    
    public ItemColour(JSONObject jsonObj)
    {
        this.name = (String)jsonObj.get("name");
        this.first = (String)jsonObj.get("1");
        this.second = (String)jsonObj.get("2");
        this.third = (String)jsonObj.get("3");
        this.fourth = (String)jsonObj.get("4");
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }
    
    
    
    
}
