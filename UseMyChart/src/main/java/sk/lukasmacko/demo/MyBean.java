package sk.lukasmacko.demo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.event.DragStopEvent;
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
public class MyBean {

 
    private String msg;
    private String dragLog;
    private Random generator;
    private List<Point<Number>> points;
    private List<Point<Date>> datePoints;

    public List<Point<Date>> getDatePoints() {
        return datePoints;
    }

    public List<Point<String>> getStringPoints() {
        return stringPoints;
    }
    private List<Point<String>> stringPoints;
    private NumberChartModel lineChartModel;
    private NumberChartModel barChartModel3;
    private StringChartModel barChartModel;
    private StringChartModel barChartModel2;
    private StringChartModel pieChartModel;
    
    private DateChartModel dateModel;
    private DateChartModel dateModel2;

    public DateChartModel getDateModel() {
        return dateModel;
    }

    public StringChartModel getPieChartModel() {
        return pieChartModel;
    }

   

    public List<Point<Number>> getPoints() {
        return points;
    }

    public String getMsg() {
        return msg;
    }

    public String getDragLog() {
        return dragLog;
    }

    public StringChartModel getBarChartModel() {
        return barChartModel;
    }

    public StringChartModel getBarChartModel2() {
        return barChartModel2;
    }

    public NumberChartModel getBarChartModel3() {
        return barChartModel3;
    }
    
    public void setDragLog(String log) {
        this.dragLog = log;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    public void updateHandler(AjaxBehaviorEvent ev) {
        setMsg("Updated from server.");
        System.out.print("Hey hou!");
    }

    public DateChartModel getDateModel2() {
        return dateModel2;
    }



    public MyBean() {
        msg = "Nothing";
        dragLog = "Nothing dragged yet.";


        lineChartModel = new NumberChartModel(ChartDataModel.ChartType.line);
        lineChartModel.add(2.0, 6.0);
        lineChartModel.add(3.0, 5.0);
        lineChartModel.add(4.0, 6.0);
        
        barChartModel3 = new NumberChartModel(ChartDataModel.ChartType.bar);
        barChartModel3.add(2.0, 6.0);
        barChartModel3.add(3.0, 5.0);
        barChartModel3.add(4.0, 6.0);
        
        barChartModel = new StringChartModel(ChartDataModel.ChartType.bar);
        barChartModel.add("aha", 5.0);
        barChartModel.add("mmm", 5.0);
        barChartModel.add("aaa", 5.0);
        
        barChartModel2 = new StringChartModel(ChartDataModel.ChartType.bar);
        barChartModel2.add("aha", 5.0);
        barChartModel2.add("aaa", 5.0);
        
        pieChartModel = new StringChartModel(ChartDataModel.ChartType.pie);
        pieChartModel.add("aha", 5.0);
        pieChartModel.add("aaa", 5.0);
        
        points = new ArrayList<Point<Number>>();
        points.add(new Point(4.0, 5.0));
        points.add(new Point(3.0, 5.0));
        points.add(new Point(1.0, 5.0));
        
        datePoints = new ArrayList<Point<Date>>();
        datePoints.add(new Point<Date>(new Date(),5));
        datePoints.add(new Point<Date>(new GregorianCalendar(2013, 4, 1).getTime(),5));
        datePoints.add(new Point<Date>(new GregorianCalendar(2013, 5, 1).getTime(),6));
        
        stringPoints = new ArrayList<Point<String>>();
        stringPoints.add(new Point<String>("hey", 5));
        stringPoints.add(new Point<String>("hou", 5));
        stringPoints.add(new Point<String>("lets", 5));
        
        dateModel = new DateChartModel(ChartDataModel.ChartType.line);
        dateModel.add(new Date(), 4);
        
       

    }

    public NumberChartModel getLineChartModel() {
        return lineChartModel;
    }

    public void handler() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Additional listener", "df"));
        setMsg("handler");
    }

    public void dataclick(DataClickEvent event) {
        setMsg("An event occured! " + event.toString());
    }

    public void dragStopHandler(DragStopEvent event) {
        setDragLog(this.dragLog + '\n' + event.toString());
    }

    public class Point<T> {

        private T x;
        private Number y;

        public Point(T x, Number y) {
            this.x = x;
            this.y = y;
        }

        public T getX() {
            return x;
        }

        public Number getY() {
            return y;
        }
    }

   
}
