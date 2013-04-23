package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;

/**
 * 
 * @author Lukas Macko
 */
@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Cursor",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="cursor"))
abstract public class AbstractCursor extends javax.faces.component.UIComponentBase {
    
   /**
    * Attribute enables to zoom chart.
    */ 
   @Attribute(defaultValue="false")
   public abstract boolean isZoomEn();
   
   /**
    * Constraint zoom allowed values are 
    * <b>x</b>,<b>y</b>,<b>none</b>.
    */ 
   @Attribute(defaultValue="ConstraintZoomType.none")
   public abstract ConstraintZoomType getConstraintZoom();
   
   /**
    * Cursor CSS property
    */
   @Attribute(defaultValue="pointer")
   public abstract String getCursorStyle();
   
   /**
    * Attribute whether to show tooltip or not
    */
   @Attribute(defaultValue="false")
   public abstract boolean isTooltipVisible();
   
   public enum ConstraintZoomType{
       x,y,none
   }
}

