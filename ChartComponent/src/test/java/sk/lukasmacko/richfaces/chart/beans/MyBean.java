package sk.lukasmacko.richfaces.chart.beans;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import sk.lukasmacko.richfaces.chart.component.model.NumberChartModel;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel.ChartType;

@ManagedBean
@ViewScoped
public class MyBean{
	private NumberChartModel model;
	
	@PostConstruct
	private void init(){
		model = new NumberChartModel(ChartType.line);
		model.add(5, 2);
		model.add(4, 2);
		model.add(3, 2);

	}
	
	public NumberChartModel getModel() {
		return model;
	}
}