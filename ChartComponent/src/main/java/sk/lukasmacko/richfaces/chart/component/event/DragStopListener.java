package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.event.FacesListener;

/**
 *
 * @author Macko
 */
public interface DragStopListener extends FacesListener{
    
    public void processDragStop(DragStopEvent event);
    
}
