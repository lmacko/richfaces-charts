/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.cdk.chartcomponent;

import org.richfaces.cdk.annotations.*;

/**
 * A trivial hello world component
 */

@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Cursor",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="cursor"))
abstract public class AbstractCursor extends javax.faces.component.UIComponentBase {
    
   @Attribute
   public abstract boolean getZoom();
   
   @Attribute
   public abstract String getConstraintZoom();
   
}

