package sk.lukasmacko.richfaces.chart.component.model;


import java.util.Date;

/**
 * Concrete class derived from ChartDataModel. Independent
 * variable is instance of Date.
 * @author Macko
 */
public class DateChartModel extends ChartDataModel<Date> {
   
    
    public DateChartModel(ChartDataModel.ChartType type){
        super(type);
        if(type==ChartDataModel.ChartType.line){
            strategy = new LineDateStrategy();
        }
        else{
            throw new IllegalArgumentException("DateChartModel accepts only line chart type."); 
        }
       
    }

    @Override
    public Class getKeyType() {
        return Date.class;
    }

}