package sk.lukasmacko.richfaces.chart.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;



@ManagedBean
@ViewScoped
public class MyBean{
	List<Point> points;
	
	@PostConstruct
	public void init(){
		points = new ArrayList<Point>();
		points.add(new Point(1,2));
		points.add(new Point(2,2));
		
	}
	
	public List<Point> getPoints() {
		return points;
	}
 
	class Point{
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