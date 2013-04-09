package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.event.FacesListener;

/**
 *
 * @author Macko
 */
public interface DataClickListener extends FacesListener{
    
    public void processDataClick(DataClickEvent event);
    
}
