package sk.lukasmacko.richfaces.chart.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import sk.lukasmacko.richfaces.chart.component.model.NumberChartModel;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel.ChartType;



@Named
@ViewScoped
public class MyBean{
	List<Point> points;
	NumberChartModel model;
	
	public NumberChartModel getModel() {
		return model;
	}
	
	@PostConstruct
	public void init(){
		points = new ArrayList<Point>();
		points.add(new Point(1,2));
		points.add(new Point(2,2));
		
		model = new NumberChartModel(ChartType.line);
		model.add(1, 2);
		model.add(2, 2);
	}
	
	public List<Point> getPoints() {
		return points;
	}
 
	public class Point{
		private Number x;
		private Number y;
		
		public Point(Number x,Number y){
			this.x=x;
			this.y=y;
		}
		
		public Number getX() {
			return x;
		}
		public Number getY() {
			return y;
		}
	}
}