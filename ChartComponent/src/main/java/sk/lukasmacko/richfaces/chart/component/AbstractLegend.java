/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;

/**
 * A trivial hello world component
 */

@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Legend",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="legend"))
abstract public class AbstractLegend extends javax.faces.component.UIComponentBase {
     @Attribute
     public abstract String getPosition();
     
     @Attribute
     public abstract String getPlacement();
}
