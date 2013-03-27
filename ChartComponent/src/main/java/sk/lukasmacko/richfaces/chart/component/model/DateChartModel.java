package sk.lukasmacko.richfaces.chart.component.model;


import java.util.Date;

/**
 *
 * @author Macko
 */
public class DateChartModel extends ChartDataModel<Date> {
   
    
    public DateChartModel(ChartDataModel.ChartType type){
        super(type);
        if(type==ChartDataModel.ChartType.line){
            strategy = new LineDateStrategy();
        }
       
    }

    @Override
    public Class getKeyType() {
        return Date.class;
    }

   
    
    
}