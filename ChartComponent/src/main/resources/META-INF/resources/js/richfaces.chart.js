 (function ($, rf) {
   
    rf.ui = rf.ui || {};
    // Default options definition if needed for the component
    var defaultOptions = {
        legend : { show:true}
       /* seriesColors: [
   

        ]*/
    };
    
 
    // Create constructor and register our component class
    rf.ui.Chart = function (componentId, options, data, eventHandlers) {
        if(!document.getElementById(componentId)){
            throw "Element with id '"+componentId+"' not found.";
        }
        
        var mergedOptions = $.extend({}, defaultOptions, options);
        
        this.init(eventHandlers,mergedOptions,data);
        
        // call constructor of parent class
        
        
        $super.constructor.call(this, componentId, mergedOptions);   
                               
        this.element = jQuery(document.getElementById(this.id));
  
        //init chart
        
        this.element.jqplot(data, mergedOptions);
        
    };
 
    // Extending component class
    rf.ui.Base.extend(rf.ui.Chart);
 
    // define super class reference - reference to the parent prototype
    var $super = rf.ui.Chart.$super;
 
    // Add new properties and methods via extending Chart.prototype
    $.extend(rf.ui.Chart.prototype, {
        // class name
        name:"Chart",
        
        init: function(eventHandlers,options,data){
            this.eventHandlers = eventHandlers;
            this.options = options;
            this.data = data;
        },
        
        //jsf event -> javascript event 
        __eventMap : {
            'ondataclick':'jqplotDataClick',
            'onhighlight':'jqplotDataHighlight',
            'onunhighlight':'jqplotDataUnhighlight',
            'ondragstart':'jqplotDragStart',
            'ondragstop':'jqplotDragStop',
            'onpointmove':'jqplotSeriesPointChange'
       },
        
        __bindEventHandlers:function(){
            for (e in this.__eventMap){
                if(this.eventHandlers[e]){
                    jQuery(document.getElementById(this.id)).bind(this.__eventMap[e],this.__gethandlerfunction(this.eventHandlers,e,this.id,this.options));
                }
            } 
        },
        
        __gethandlerfunction : function(obj,eventName,id,options){
            if(eventName =='onunhighlight'){
                return function(ev){
                    obj[eventName].call(document.getElementById(id),ev);
                }
            }
            else if(eventName =='ondragstop'){
                //!!Edit jqplot.dragable.js line 222
                return function(ev,seriesIndex,pointIndex,dataPos){
                    ev.data = {
                        'seriesIndex':seriesIndex,
                        'x': dataPos.xaxis,
                        'y': dataPos.yaxis,
                        'pointIndex':pointIndex
                            
                    };
                    //server-side
                    obj.ajaxFunction(ev,"dragStop",seriesIndex,pointIndex,dataPos.xaxis,dataPos.yaxis);
                    //client-side
                    obj[eventName].call(document.getElementById(id),ev);
                }
            }
            else{
                return function(ev,seriesIndex,pointIndex,data){
                    ev.data = {
                        'seriesIndex':seriesIndex,
                        'pointIndex' :pointIndex,
                        'y':data[1],
                        'x':data[0]
                    };
                    
                    if(options.chartType=="bar"){//bar chart label are stored in options
                       ev.data.x = options.axes.xaxis.ticks[data[0]-1];    
                    }
                    //server-side
                    if(eventName=="ondataclick"){
                        obj.ajaxFunction(ev,"dataClick",seriesIndex,pointIndex,data[1],data[0]);
                    }
                    //client-side
                    obj[eventName].call(document.getElementById(id),ev);
                }
            }
        },
        
        // destructor definition
        destroy: function () {
            // define destructor if additional cleaning is needed but
            // in most cases its not nessesary.
            // call parent’s destructor
            $super.destroy.call(this);
        }
    });
})(jQuery, RichFaces);

