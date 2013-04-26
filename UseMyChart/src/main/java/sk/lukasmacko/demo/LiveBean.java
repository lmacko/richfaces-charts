package sk.lukasmacko.demo;

import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel;
import sk.lukasmacko.richfaces.chart.component.model.DateChartModel;
import sk.lukasmacko.richfaces.chart.component.model.StringChartModel;

/**
 * @author Macko
 */
@ManagedBean
@SessionScoped
public class LiveBean {

    private DateChartModel dateModel;
    private StringChartModel barModel;
    private StringChartModel pieModel;
    private ChartSettings lineChart;
    private ChartSettings barChart;
    private ChartSettings pieChart;
    
    
    @PostConstruct
    public void init(){
      
       dateModel = new DateChartModel(ChartDataModel.ChartType.line);
       dateModel.add(new GregorianCalendar(2013, 5, 1).getTime(), 400);
       dateModel.add(new GregorianCalendar(2013, 5, 5).getTime(), 350);
       dateModel.add(new GregorianCalendar(2013, 5, 6).getTime(), 390);
       dateModel.add(new GregorianCalendar(2013, 5, 8).getTime(), 420);
       
       
        barModel = new StringChartModel(ChartDataModel.ChartType.bar);
        barModel.add("June", 200);
        barModel.add("July", 300);
        barModel.add("August", 400);
        
        pieModel = new StringChartModel(ChartDataModel.ChartType.pie);
        pieModel.add("June", 200);
        pieModel.add("July", 300);
        pieModel.add("August", 400);
                
       lineChart = new ChartSettings();
       lineChart.setXformat("%m/%d/%Y");
       lineChart.setYformat("$%d");
       lineChart.setXtickRotation(-30);
       
       barChart = new ChartSettings();
       pieChart = new ChartSettings();
    }

    public ChartSettings getLineChart() {
        return lineChart;
    }

    public ChartSettings getBarChart() {
        return barChart;
    }

    public ChartSettings getPieChart() {
        return pieChart;
    }
    
    
  

    public DateChartModel getDateModel() {
        return dateModel;
    }

    public StringChartModel getBarModel() {
        return barModel;
    }

    public StringChartModel getPieModel() {
        return pieModel;
    }
    
}
