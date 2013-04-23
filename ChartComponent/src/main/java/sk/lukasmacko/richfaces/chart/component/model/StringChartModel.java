package sk.lukasmacko.richfaces.chart.component.model;

/**
 * Concrete class derived from ChartDataModel. Independent
 * variable is instance of String.
 * @author Macko
 */
public class StringChartModel extends ChartDataModel<String> {
    
    public StringChartModel(ChartDataModel.ChartType type){
        super(type);
    }

    @Override
    public Class getKeyType() {
        return String.class;
    }
    
}