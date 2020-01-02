/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Comparator;

/**
 *
 * @author Timot
 */
public class TaskComparator implements Comparator
{

    @Override
    public int compare(Object o1, Object o2)
    {
        return ((Task)o2).getPriority()-((Task)o1).getPriority();
    }
    
}
