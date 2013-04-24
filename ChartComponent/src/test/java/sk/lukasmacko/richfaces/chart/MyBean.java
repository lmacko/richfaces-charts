package sk.lukasmacko.richfaces.chart;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.model.NumberChartModel;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel.ChartType;
import sk.lukasmacko.richfaces.chart.component.model.StringChartModel;



@ManagedBean
@ViewScoped
public class MyBean{
	List<Point<String>> points;
	StringChartModel model;
	NumberChartModel lineModel;
	StringChartModel barModel;
	String msg;
	
	public StringChartModel getModel() {
		return model;
	}
	
	public String getMsg() {
		return msg;
	}
	
	@PostConstruct
	public void init(){
		msg=new String("-");
		points = new ArrayList<Point<String>>();
		points.add(new Point<String>("hey",5));
		points.add(new Point<String>("hou",5));
		points.add(new Point<String>("lets",5));
		
		
		model = new StringChartModel(ChartType.pie);
		model.add("a", 5);
		model.add("b", 5);
		model.add("c", 5);
		
		lineModel = new NumberChartModel(ChartType.line);
		lineModel.add(4, 5);
		lineModel.add(6, 6);
		lineModel.add(7, 8);
		
		barModel = new StringChartModel(ChartType.bar);
		barModel.add("June", 100);
		barModel.add("July", 80);
		barModel.add("August", 120);
	}
	
	public void dataClickHandler(DataClickEvent e){
		msg=""+e.getPointIndex();
	}
	public void listner(DataClickEvent event){
        msg = event.toString();
    }
	
	public List<Point<String>> getPoints() {
		return points;
	}
 
	public StringChartModel getBarModel() {
		return barModel;
	}
	public NumberChartModel getLineModel() {
		return lineModel;
	}
	
	public class Point<T>{
		private T x;
		private Number y;
		
		public Point(T x,Number y){
			this.x=x;
			this.y=y;
		}
		
		public T getX() {
			return x;
		}
		public Number getY() {
			return y;
		}
	}
}