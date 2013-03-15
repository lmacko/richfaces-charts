import java.io.Serializable;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.event.DataClickListener;
import sk.lukasmacko.richfaces.chart.component.model.BarChartModel;
import sk.lukasmacko.richfaces.chart.component.model.LineChartModel;
import sk.lukasmacko.richfaces.chart.component.model.PieChartModel;

/**
 *
 * @author Macko
 */
@ManagedBean
@ViewScoped
public class MyBean {

   
    private LineChartModel first;
    private LineChartModel second;
    private PieChartModel pieData;
    private BarChartModel barData;
    private BarChartModel barData2;
    private String msg;
    private Random generator;
    
    private DataClickListener listner = new MyListner();

    public DataClickListener getListner() {
        return listner;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BarChartModel getBarData() {
        return barData;
    }
    public BarChartModel getBarData2() {
        return barData2;
    }

    public PieChartModel getPieData() {
        return pieData;
    }

    public LineChartModel getFirst() {
        return first;
    }

    public LineChartModel getSecond() {
        return second;
    }
    
    public void updateHandler(AjaxBehaviorEvent ev){
        setMsg("Updated from server.");
        System.out.print("Hey hou!");
    }
    
    public void generateRandom(){
        barData2 = new BarChartModel();
        barData2.add("June",generator.nextInt(100));
        barData2.add("July",generator.nextInt(100));
        barData2.add("August",generator.nextInt(100));
    }

    
    

    
    
    public MyBean() {
        msg ="Nothing";
        
        generator = new Random();
        
        first = new LineChartModel();
        first.add(1.0, 2.0);
        first.add(2.0, 2.0);
        first.add(3.0,4.0);
        first.add(5.0,1.0);
        
        second = new LineChartModel();
        second.add(1.0, 4.0);
        second.add(2.0, 3.0);
        second.add(3.0,3.0);
        second.add(5.0,1.5);
        
        pieData = new PieChartModel();
        pieData.add("June",40);
        pieData.add("July",50);
        pieData.add("August",60);
        
        
        barData = new BarChartModel();
        barData.add("June",40);
        barData.add("July",50);
        barData.add("August",60);
        
        barData2 = new BarChartModel();
        barData2.add("June",40);
        barData2.add("July",50);
        barData2.add("August",60);
        System.out.println("I'm alive");
        
    }
    public void handler(){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Additional listener","df"));
    }
    
    public void dataclick(DataClickEvent event){
        setMsg("An event occured! "+ event.toString());
    }
    
    public class MyListner implements DataClickListener{

        @Override
        public void processDataClick(DataClickEvent event) {
            System.out.println("Event");
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }


   
}
