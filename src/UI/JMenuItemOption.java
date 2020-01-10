/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JMenuItem;

/**
 *
 * @author Timot
 */
public class JMenuItemOption extends JMenuItem
{
    private String info,command;
    
    private int id,index;
    
    private Object target;
    
    public JMenuItemOption(String label,String info,String command,int id,int index,Object target)
    {
        super(label);
        this.info = info;
        this.command = command;
        this.id = id;
        this.target = target;
        this.index = index;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Object getTarget()
    {
        return target;
    }

    public void setTarget(Object target)
    {
        this.target = target;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
    
    
    
    
}
