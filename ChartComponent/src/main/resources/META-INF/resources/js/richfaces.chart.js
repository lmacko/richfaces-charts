(function ($, rf) {
    // Create (for example) ui container for our component class
    rf.ui = rf.ui || {};
    // Default options definition if needed for the component
    var defaultOptions = {};
    
 
    // Create constructor and register our component class
    rf.ui.Chart = function (componentId, options,data,eventHandlers) {
        if(!document.getElementById(componentId)){
            throw "Element with id '"+componentId+"' not found.";
        }
        
        this.init(eventHandlers,options,data);
        
        // call constructor of parent class
        $super.constructor.call(this, componentId, options, defaultOptions);   
                               
        this.element = jQuery(document.getElementById(this.id));
  
        //init chart
        this.element.jqplot(data, options);
        
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
                    jQuery(document.getElementById(this.id)).bind(this.__eventMap[e],this.__gethandlerfunction(this.eventHandlers,e,this.id));
                }
            } 
        },
        
        __gethandlerfunction : function(obj,eventName,id){
            if(eventName =='onunhighlight'){
                return function(ev){
                    obj[eventName].call(document.getElementById(id),ev);
                }
            }
            else{
                return function(ev,seriesIndex,pointIndex,data){
                    ev.data = {
                        'seriesIndex':seriesIndex,
                        'pointIndex' :pointIndex,
                        'x':data[0],
                        'y':data[1]
                    };
                    
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




/*(function($,rf){
    rf.ui = rf.ui || {};
    
    rf.ui.Chart =  rf.BaseComponent.extendClass({
        name:"Chart",
        
        init:function(componentId,options,data,events){
            
            if(!document.getElementById(componentId)){
                throw "Element with id '"+componentId+"' not found.";
            }
            
            this.options = options;
            this.events = events;
           
            $super.constructor.call(this,componentId);
            
            this.__element = this.attachToDom(this.id);
                           
            this.element = jQuery(document.getElementById(this.id));
            
            var _this = this;
            //init chart
            this.element.jqplot(data, options);
            
            
            //Client-side event binding
            
            //jsf event -> javascript event 
            var eventMap = {
                'ondataclick':'jqplotDataClick',
                'onhighlight':'jqplotDataHighlight',
                'onunhighlight':'jqplotDataUnhighlight',
                'ondragstart':'jqplotDragStart',
                'ondragstop':'jqplotDragStop',
                'onpointmove':'jqplotSeriesPointChange'
                
            }
           
            var __gethandlerfunction = function(obj,eventName,id){
                return function(ev,seriesIndex,pointIndex,data){
                    ev.data = {
                        'seriesIndex':seriesIndex,
                        'pointIndex' :pointIndex,
                        'x':data[0],
                        'y':data[1]
                    };
                    
                    obj[eventName].call(document.getElementById(id),ev);
                }
            };
             
            for (e in eventMap){
                if(this.events[e]){
                    this.element.bind(eventMap[e],__gethandlerfunction(this.events,e,this.id));
                }
            } 
             
        /*if(this.events.ondataclick){
                this.element.bind('jqplotDataClick', 
                    __gethandlerfunction(this.events,'ondataclick',this.id));
            }
            
            if(this.events.onmouseover){
                this.element.bind('jqplotDataHighlight', 
                    function (ev, seriesIndex, pointIndex, data) {
                        ev.data = {
                            'seriesIndex':seriesIndex,
                            'pointIndex' :pointIndex,
                            'x':data[0],
                            'y':data[1]
                        };
                    
                        _this.events.onmouseover.call(document.getElementById(_this.id),ev);
                    }
                    );
                    
                    
            }         
        },
        
        
            
        destroy: function(){
            $super.destroy.call(this);
   
        }
        
    });
    var $super = rf.ui.Chart.$super;
})(jQuery,RichFaces);


*/