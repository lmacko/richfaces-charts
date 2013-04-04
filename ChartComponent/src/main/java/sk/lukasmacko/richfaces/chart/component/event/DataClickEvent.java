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
    private String x;
    private Number y;
    
    public DataClickEvent(UIComponent component,int seriesIndex,int pointIndex, String x, Number y){
        super(component);
        this.seriesIndex=seriesIndex;
        this.pointIndex=pointIndex;
        this.x=x;
        this.y=y;
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
    public String toString() {
        return "Point with index " +getPointIndex()+
                "within series "+getSeriesIndex()+" was clicked.\n"+
                "Point coordinates ["+getX()+","+getY()+"]";
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
