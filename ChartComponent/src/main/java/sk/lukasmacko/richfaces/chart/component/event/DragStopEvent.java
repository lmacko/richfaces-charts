package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 * The class represents dragstop event fired
 * by the chart component, when user stops point dragging.
 * @author Lukas Macko
 */
public class DragStopEvent extends FacesEvent{
    /**
     * Index into chart series . The first
     * series has index 0.
     */
    private int seriesIndex;
    
    /**
     * An Index into list of points inside series.
     * The index points to an altered point.
     * The first point has index 0.
     */
    private int pointIndex;
    
    /**
     * The new value independent variable of the dragged point.
     * x-coordinate 
     */
    private String x;
    
    /**
     * Dependent variable - altered value.
     * y-coordinate
     */
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
