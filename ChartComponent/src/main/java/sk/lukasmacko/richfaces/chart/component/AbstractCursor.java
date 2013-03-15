package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;


@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Cursor",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="cursor"))
abstract public class AbstractCursor extends javax.faces.component.UIComponentBase {
    
   @Attribute(defaultValue="false")
   public abstract boolean isZoomEn();
   
   @Attribute(defaultValue="ConstraintZoomType.none")
   public abstract ConstraintZoomType getConstraintZoom();
   
   
   public enum ConstraintZoomType{
       x,y,none
   }
}

