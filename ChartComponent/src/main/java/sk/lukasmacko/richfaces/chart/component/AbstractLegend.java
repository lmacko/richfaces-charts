package sk.lukasmacko.richfaces.chart.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.richfaces.cdk.annotations.*;


/**
 * The &lt;lm:legend&lt; tag is child tag of &lt;lm:chart&gt;. It customizes
 * legend look.
 * @author Lukas Macko
 */
@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Legend",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="legend"))
abstract public class AbstractLegend extends javax.faces.component.UIComponentBase {
     
     /**
      * Legend position in parent chart div.
      * Attribute accepts following values 
      * <b>top</b>,<b>left</b>,<b>right,bottom</b>
      *  
      */    
     @Attribute(defaultValue="PositionType.right",suggestedValue="PositionType.left,PositionType.top,PositionType.bottom")
     public abstract PositionType getPosition();
     
     /**
      * Legend placement <b>inside</b> or <b>outside</b>
      * of chart grid.
      */
     @Attribute(defaultValue = "PlacementType.inside")
     public abstract PlacementType getPlacement();
     
     /**
      * Show/hide legend 
      */
     @Attribute(defaultValue="true")
     public abstract boolean isVisible();
     
     public static enum PlacementType {
        inside,
        outside
     }
     public static enum PositionType{
         top,left,right,bottom
     }
     
     /**
      * Map from chart attributes to javascript options
      */
     public static Map<PositionType,String> positionMap;
     static {
        Map <PositionType,String>tmp = new HashMap<PositionType,String>();
        tmp.put(PositionType.top, "n");
        tmp.put(PositionType.left, "w");
        tmp.put(PositionType.bottom, "s");
        tmp.put(PositionType.right, "e");
        positionMap=Collections.unmodifiableMap(tmp);
    }
}
