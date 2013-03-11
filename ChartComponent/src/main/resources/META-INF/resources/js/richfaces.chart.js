(function($,rf){
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
                    
                    
            } */         
        },
        
        
            
        destroy: function(){
            $super.destroy.call(this);
   
        }
        
    });
    var $super = rf.ui.Chart.$super;
})(jQuery,RichFaces);


