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
public class DataClickEvent extends FacesEvent{
    private int seriesIndex;
    private int pointIndex;
    private Number x;
    private Object y;
    
    public DataClickEvent(UIComponent component){
        super(component);
    }

    public int getSeriesIndex() {
        return seriesIndex;
    }

    public int getPointIndex() {
        return pointIndex;
    }

    public Number getX() {
        return x;
    }

    public Object getY() {
        return y;
    }
    
    @Override
    public boolean isAppropriateListener(FacesListener listener) {
        return listener instanceof DataClickListener;
    }

    @Override
    public void processListener(FacesListener listener) {
        ((DataClickListener) listener).processDataClick(this);
    }
    
}
