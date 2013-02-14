import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sk.lukasmacko.richfaces.chart.component.model.PieChartModel;
import sk.lukasmacko.richfaces.chart.component.model.LineChartModel;

/**
 *
 * @author Macko
 */
@ManagedBean
@RequestScoped
public class MyBean {

   
    private LineChartModel first;
    private LineChartModel second;
    private PieChartModel barData;

    public PieChartModel getBarData() {
        return barData;
    }

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
        
        barData = new PieChartModel();
        barData.add("June",40);
        barData.add("July",50);
        barData.add("August",60);
        
        
        
        
    }
}
