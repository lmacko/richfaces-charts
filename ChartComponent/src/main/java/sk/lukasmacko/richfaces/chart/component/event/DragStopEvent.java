/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 *
 * @author Macko
 */
public class DragStopEvent extends FacesEvent{
    private int seriesIndex;
    private int pointIndex;
    private String x;
    private Number y;
    
    
    public DragStopEvent(UIComponent component,int seriesIndex,int pointIndex,String x, Number y){
        super(component);
        this.seriesIndex = seriesIndex;
        this.pointIndex = pointIndex;
        this.x = x;
        this.y = y;
    }

    public int getSeriesIndex() {
        return seriesIndex;
    }

    public int getPointIndex() {
        return pointIndex;
    }

    public String getX() {
        return x;
    }

    public Number getY() {
        return y;
    }

    @Override
    public boolean isAppropriateListener(FacesListener fl) {
        return fl instanceof DragStopListener;
    }

    @Override
    public void processListener(FacesListener fl) {
        ((DragStopListener) fl).processDragStop(this);
    }

    @Override
    public String toString() {
        return "Point with index " +getPointIndex()+
               "within series "+getSeriesIndex()+" was dragged to new position.\n"+
               "Point coordinates ["+getX()+","+getY()+"]";
    }
    
    
}
