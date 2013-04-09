
package sk.lukasmacko.richfaces.chart.component.model;

import org.richfaces.json.JSONString;

/**
 * 
 * @author Macko
 */
public class RawJSONString implements JSONString{
    
    private String string;
    
    public RawJSONString(String s){
        this.string=s;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
       
    @Override
    public String toJSONString() {
        return getString();
    }
    
}
