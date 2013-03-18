/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import sk.lukasmacko.richfaces.chart.component.AbstractPoint;
import sk.lukasmacko.richfaces.chart.component.model.LineChartModel;

/**
 *
 * @author Macko
 */
public class VisitPointCallback implements VisitCallback{

    private LineChartModel model= null;
    
    @Override
    public VisitResult visit(VisitContext context, UIComponent target) {
       
        if(model==null){
            model = new LineChartModel();
        }
 
        if(target instanceof AbstractPoint){
           AbstractPoint p = (AbstractPoint) target;
          
           model.add(p.getX(), p.getY().doubleValue());
        
        }
        return VisitResult.ACCEPT;
    }
    
    public LineChartModel getModel(){
        return this.model;
    }
}

