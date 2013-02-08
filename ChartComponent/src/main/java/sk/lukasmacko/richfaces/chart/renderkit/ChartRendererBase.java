/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.renderkit;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;
import org.richfaces.renderkit.RendererBase;
import sk.lukasmacko.richfaces.chart.component.AbstractChart;
import sk.lukasmacko.richfaces.chart.component.AbstractCursor;
import sk.lukasmacko.richfaces.chart.component.AbstractLegend;
import sk.lukasmacko.richfaces.chart.component.AbstractSeries;
import sk.lukasmacko.richfaces.chart.component.AbstractXaxis;
import sk.lukasmacko.richfaces.chart.component.AbstractYaxis;

/**
 *
 * @author Macko
 */
public abstract class ChartRendererBase extends RendererBase{
   
   private JSONObject options; 
   
  
    
   public static JSONObject addAttribute(JSONObject obj, String key,Object value){
        try {
            obj.put(key, value);
        } catch (JSONException ex) {
            Logger.getLogger(ChartRendererBase.class.getName()).log(Level.SEVERE, null, ex);
        }
       return obj;
   }
   
   protected void getOptions(FacesContext context, UIComponent component) throws IOException{
       ResponseWriter writer = context.getResponseWriter();
       AbstractChart chart = (AbstractChart) component;
     
        options = new JSONObject();
       //chart options
       addAttribute(options,"title", component.getAttributes().get("title"));
            
      
       
       List<UIComponent> children  = chart.getChildren();
      
       for (UIComponent ch : children){
           if(ch instanceof AbstractLegend){
              processLegend(ch); 
           }
           else if(ch instanceof AbstractSeries){
               
           }
           else if(ch instanceof AbstractCursor){
               
           }
           else if(ch instanceof AbstractXaxis){
               
           }
           else if(ch instanceof AbstractYaxis){
               
           }
       }
       writer.write(options.toString());
       
   }
   
   protected void processLegend(UIComponent legend){
       JSONObject legendOpt = new JSONObject();
    
       addAttribute(legendOpt, "show", true);
       addAttribute(options, "legend", legendOpt); 
   }

   
   
}
