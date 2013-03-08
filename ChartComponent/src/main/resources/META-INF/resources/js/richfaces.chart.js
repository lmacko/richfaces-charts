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
            
            this.attachToDom(this.id);
                           
            this.element = jQuery(document.getElementById(this.id));
            
            var _this = this;
            //init chart
            this.element.jqplot(data, options);
                
            this.element.bind('jqplotDataClick', 
                function (ev, seriesIndex, pointIndex, data) {
                    _this.events.ondataclick.call(document.getElementById(_this.id));
                }
                );
            this.element.bind('jqplotDataHighlight', 
                function (ev, seriesIndex, pointIndex, data) {
                    _this.events.onmouseover.call(document.getElementById(_this.id));
                }
                );
                    
                    
                   
        },
        
        destroy: function(){
            $super.destroy.call(this);
   
        }
        
    });
    var $super = rf.ui.Chart.$super;
})(jQuery,RichFaces);


