/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.event.FacesListener;

/**
 *
 * @author Macko
 */
public interface DragStopListener extends FacesListener{
    
    public void processDragStop(DragStopEvent event);
    
}
