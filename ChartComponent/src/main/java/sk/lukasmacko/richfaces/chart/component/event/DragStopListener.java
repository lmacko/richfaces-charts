package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.event.FacesListener;

/**
 * Interface define listener for DragStopEvent.
 * @author Lukas Macko
 */
public interface DragStopListener extends FacesListener{
    
    public void processDragStop(DragStopEvent event);
    
}
