package sk.lukasmacko.richfaces.chart.component.event;

import javax.faces.event.FacesListener;

/**
 * Define listener for DataClickEvent.
 * @author Lukas Macko
 */
public interface DataClickListener extends FacesListener{
    
    public void processDataClick(DataClickEvent event);
    
}
