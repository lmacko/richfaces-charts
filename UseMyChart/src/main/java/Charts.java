import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel;
import sk.lukasmacko.richfaces.chart.component.model.DateChartModel;
import sk.lukasmacko.richfaces.chart.component.model.NumberChartModel;
import sk.lukasmacko.richfaces.chart.component.model.StringChartModel;

/**
 *
 * @author Macko
 */
@ManagedBean
@ViewScoped
public class Charts {
    
    private StringChartModel pieModel;
    private StringChartModel barModel;
    private NumberChartModel lineModel;
    private DateChartModel  dateModel;
    private List<Point<Number>> points;
    private String msg;
    

    private List<Point<String>> data;

    @PostConstruct
    public void initData(){
        data = new ArrayList<Point<String>>();
        data.add(new Point<String>("Departement X", 100));
        data.add(new Point<String>("Departement Y", 200));
        data.add(new Point<String>("Departement Z", 300));
    }
    
    public List<Point<String>> getData() {
        return data;
    }
    
    /**
     * Creates a new instance of Charts
     */
    public Charts() {
        msg= new String();
        
        points = new ArrayList<Point<Number>>();
        points.add(new Point<Number>(1,1));
        points.add(new Point<Number>(5, 5));
        points.add(new Point<Number>(9, 4));
        
        barModel = new StringChartModel(ChartDataModel.ChartType.bar);
        barModel.add("June", 100);
        barModel.add("July", 80);
        barModel.add("August", 120);
        
        
        
        pieModel = new StringChartModel(ChartDataModel.ChartType.pie);
        pieModel.add("June", 100);
        pieModel.add("July", 80);
        pieModel.add("August", 120);
        
        lineModel = new NumberChartModel(ChartDataModel.ChartType.line);
        lineModel.add(4, 5);
        lineModel.add(6, 6);
        lineModel.add(7, 8);
        
        dateModel = new DateChartModel(ChartDataModel.ChartType.line);
        dateModel.add(new GregorianCalendar(2013, 5, 1).getTime(), 400);
        dateModel.add(new GregorianCalendar(2013, 5, 5).getTime(), 350);
        dateModel.add(new GregorianCalendar(2013, 5, 6).getTime(), 390);
        dateModel.add(new GregorianCalendar(2013, 5, 8).getTime(), 420);
    }

    public DateChartModel getDateModel() {
        return dateModel;
    }

    
    public StringChartModel getPieModel() {
        return pieModel;
    }

    public NumberChartModel getLineModel() {
        return lineModel;
    }

    public List<Point<Number>> getPoints() {
        return points;
    }

    public String getMsg() {
        return msg;
    }
    
    
    

    public StringChartModel getBarModel() {
        return barModel;
    }
    
    public void listner(DataClickEvent event){
        msg = event.toString();
    }
    
    
   public class Point<T>{
        private T x;
        private Number y;
        
        public Point(T x, Number y){
            this.x =x;
            this.y =y;
        }

        public T getX() {
            return x;
        }

        public Number getY() {
            return y;
        }
    }
    
}
