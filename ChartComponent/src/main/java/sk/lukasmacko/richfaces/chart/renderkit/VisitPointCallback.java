package sk.lukasmacko.richfaces.chart.renderkit;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import sk.lukasmacko.richfaces.chart.component.AbstractPoint;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel.ChartType;
import sk.lukasmacko.richfaces.chart.component.model.DateChartModel;
import sk.lukasmacko.richfaces.chart.component.model.NumberChartModel;
import sk.lukasmacko.richfaces.chart.component.model.StringChartModel;

/**
 * 
 * @author Lukas Macko
 */
public class VisitPointCallback implements VisitCallback {

    private ChartDataModel model = null;
    private ChartDataModel.ChartType chartType;
    
    public VisitPointCallback(ChartDataModel.ChartType chartType) {
        this.chartType = chartType;
    }

    /**
     * The method collects data from nested point tags. 
     * @param context
     * @param target
     * @return 
     */
    @Override
    public VisitResult visit(VisitContext context, UIComponent target) {

        if (target instanceof AbstractPoint) {

            AbstractPoint p = (AbstractPoint) target;

            Object x = p.getX();

            //the first point determine type of dat model 
            if (model == null) {
                if (x instanceof Number) {
                    model = new NumberChartModel(chartType);
                } else if (x instanceof String) {
                    model = new StringChartModel(chartType);
                } else if (x instanceof Date) {
                    model = new DateChartModel(chartType);
                } else {
                    throw new IllegalArgumentException("Not supported type");
                }
            }

            
            if (x instanceof Number) {
                double numberX;
                numberX = ((Number) x).doubleValue();
                model.add(numberX, p.getY());
            } else if (x instanceof String) {
                model.add(((String)x), p.getY());
            } else if (x instanceof Date) {
                model.add((Date)x, p.getY());
            } else {
                throw new IllegalArgumentException("Not supported type" + x.getClass());
            }



        }
        return VisitResult.ACCEPT;
    }

    public ChartDataModel getModel() {
        return this.model;
    }

    public ChartType getChartType() {
        return chartType;
    }

    
}
