package sk.lukasmacko.demo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Macko
 */
public class ChartSettings {
    List<String> markers;
    List<String> positionOpt;
    List<String> placementOpt;
    List<String> dirConstraintOpt;
    List<String> cursorOpt;
    
    String title= new String();
    
    //axis
    String xlabel= new String();
    String xformat = "";
    String yformat = "";
    String ylabel = new String();
    Integer xtickRotation=0;
    Integer ytickRotation=0;
    String xmin=null;
    String ymin=null;
    String xmax=null;
    String ymax=null;
    String xpad="1.2";
    String ypad="1.2";
    
    
    
    //legend
    String position = new String();
    String placement = new String();
    private boolean showLegend = true;
    
    //series
    String marker = new String();
    private String label = "";
    private String color = "";
    private boolean showMarker=true;
    private boolean draggable=false;
    private boolean trendline=false;
    private String draggableConstrain = new String();
    
    //cursor
    private boolean zoom=false;
    private String zoomConstraint;
    private boolean tooltip=false;
    private String cursor = new String();
    
   
    public ChartSettings(){
       markers = new ArrayList<String>(Arrays.asList("circle","square","diamond","filledCircle","filledDiamond","filledSquare"));
       positionOpt = new ArrayList<String>(Arrays.asList("top","bottom","left","right"));
       placementOpt = new ArrayList<String>(Arrays.asList("inside","outside"));
       cursorOpt = new ArrayList<String>(Arrays.asList("auto","crosshair","move","pointer"));
       dirConstraintOpt = new ArrayList<String>(Arrays.asList("x","y","none"));
    }
    
 
    
    

    public List<String> getMarkers() {
        return markers;
    }

    public String getTitle() {
        return title;
    }

    public String getXlabel() {
        return xlabel;
    }

    public String getYlabel() {
        return ylabel;
    }

    public String getMarker() {
        return marker;
    }

    public String getPosition() {
        return position;
    }

    public String getPlacement() {
        return placement;
    }

    public boolean getShowLegend(){
        return showLegend;
    }

    public boolean getDraggable(){
        return draggable;
    }
    
    public List<String> getPlacementOpt() {
        return placementOpt;
    }

    public List<String> getPositionOpt() {
        return positionOpt;
    }

    public String getZoomConstraint() {
        return zoomConstraint;
    }
    
    public boolean getZoom(){
        return zoom;
    }
    
    public boolean getTooltip(){
        return tooltip;
    }

    public String getCursor() {
        return cursor;
    }

    public List<String> getDirConstraintOpt() {
        return dirConstraintOpt;
    }

    public List<String> getCursorOpt() {
        return cursorOpt;
    }

    public String getColor() {
        return color;
    }

    public String getDraggableConstrain() {
        return draggableConstrain;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public boolean isShowMarker() {
        return showMarker;
    }

    public boolean isTrendline() {
        return trendline;
    }

    public String getXformat() {
        return xformat;
    }

    public String getYformat() {
        return yformat;
    }

    public void setYformat(String yformat) {
        this.yformat = yformat;
    }

    public Integer getXtickRotation() {
        return xtickRotation;
    }

    public void setXtickRotation(Integer xtickRotation) {
        this.xtickRotation = xtickRotation;
    }

    public Integer getYtickRotation() {
        return ytickRotation;
    }

    public void setYtickRotation(Integer ytickRotation) {
        this.ytickRotation = ytickRotation;
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    public String getYmin() {
        return ymin;
    }

    public void setYmin(String ymin) {
        this.ymin = ymin;
    }

    public String getXmax() {
        return xmax;
    }

    public void setXmax(String xmax) {
        if(xmax.equals("")){
            this.xmax = null;        
        }
        else{
            this.xmax = xmax;
        }
    }

    public String getYmax() {
        return ymax;
    }

    public void setYmax(String ymax) {
        this.ymax = ymax; 
    }

    public String getXpad() {
        return xpad;
    }

    public void setXpad(String xpad) {
        this.xpad = xpad;
    }

    public String getYpad() {
        return ypad;
    }

    public void setYpad(String ypad) {
        this.ypad = ypad;
    }
    
    
    
    
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setXlabel(String xlabel) {
        this.xlabel = xlabel;
    }

    public void setYlabel(String ylabel) {
        this.ylabel = ylabel;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }
    
    public void setDraggable(boolean b){
        this.draggable=b;
    }
    
    public void setZoom(boolean zoom){
        this.zoom=zoom;
    }
    
    public void setTooltip(boolean toolTip){
        this.tooltip=toolTip;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public void setZoomConstraint(String zoomConstraint) {
        this.zoomConstraint = zoomConstraint;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setShowMarker(boolean showMarker) {
        this.showMarker = showMarker;
    }

    public void setTrendline(boolean trendline) {
        this.trendline = trendline;
    }

    public void setDraggableConstrain(String draggableConstrain) {
        this.draggableConstrain = draggableConstrain;
    }

    public void setXformat(String xformat) {
        this.xformat = xformat;
    }
}
