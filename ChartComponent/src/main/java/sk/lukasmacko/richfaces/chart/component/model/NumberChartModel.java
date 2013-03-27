package sk.lukasmacko.richfaces.chart.component.model;

/**
 *
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
