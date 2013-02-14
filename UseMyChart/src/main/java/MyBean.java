/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sk.lukasmacko.richfaces.chart.component.model.LineChartModel;

/**
 *
 * @author Macko
 */
@ManagedBean
@RequestScoped
public class MyBean {

  

    /**
     * Creates a new instance of MyBean
     */
    
    
    private LineChartModel first;
    private LineChartModel second;

    public LineChartModel getFirst() {
        return first;
    }

    public LineChartModel getSecond() {
        return second;
    }

    

    
    
    public MyBean() {
        first = new LineChartModel();
        first.add(1.0, 2.0);
        first.add(2.0, 2.0);
        first.add(3.0,4.0);
        first.add(5.0,1.0);
        
        second = new LineChartModel();
        second.add(1.0, 4.0);
        second.add(2.0, 3.0);
        second.add(3.0,3.0);
        second.add(5.0,1.5);
        
    }
}
