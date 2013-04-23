package sk.lukasmacko.richfaces.chart.component.model;

/**
 * Concrete class derived from ChartDataModel. Independent
 * variable is instance of Number.
 * @author Macko
 */
public class NumberChartModel extends ChartDataModel<Number> {
    
    public NumberChartModel(ChartDataModel.ChartType type){
        super(type);
    }

    @Override
    public Class getKeyType() {
        return Number.class;
    }
    
}
