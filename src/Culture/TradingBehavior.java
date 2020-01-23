/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Timot
 */
public class TradingBehavior
{
    private String jobName;
    
    private ArrayList<TradingCondition> conditions;
    
    public TradingBehavior(JSONObject jsonObj)
    {
        conditions = new ArrayList<TradingCondition>();
        
        this.jobName = (String)jsonObj.get("job");
        
        JSONArray conditionArr = (JSONArray)jsonObj.get("rule");
        for(int i=0;i<conditionArr.size();i++)
        {
            conditions.add(new TradingCondition((JSONObject)conditionArr.get(i)));
        }
        
    }

    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public ArrayList<TradingCondition> getConditions()
    {
        return conditions;
    }

    public void setConditions(ArrayList<TradingCondition> conditions)
    {
        this.conditions = conditions;
    }
    
    
    
}
