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
        type = "sk.lukasmacko.richfaces.chartcomponent.Xaxis",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="xaxis"))
abstract public class AbstractXaxis extends javax.faces.component.UIComponentBase implements AxisAttributes{

}
