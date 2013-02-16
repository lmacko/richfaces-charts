(function($,rf){
    rf.ui = rf.ui || {};
    
    var defaultOptions = {};
    
    rf.ui.Chart =  rf.BaseComponent.extendClass({
        name:"Chart",
        init:function(componentId,options,data){
            if(!document.getElementById(componentId)){
                throw "Element with id '"+componentId+"' not found.";
            }
            var barChart=false;
            for (var i = 0; i < options.series.length; i++) {
                if(options.series[i].renderer=="bar"){
                    barChart=true;
                    options.series[i].renderer= $.jqplot.BarRenderer;
                }
                else if(options.series[i].renderer=="pie"){
                    options.series[i].renderer= $.jqplot.PieRenderer;
                }
            }
            if(barChart){
                options.axes.xaxis.renderer=$.jqplot.CategoryAxisRenderer;
            }
            
            this.options = options;
            
            
            $super.constructor.call(this,componentId);
            
            this.attachToDom(this.id);
            var _this = this;
            jQuery(function(){
               
                jQuery(document.getElementById(_this.id)).jqplot(data, options); 
            });
        },
        //public API
        destroy: function(){
            $super.destroy.call(this);
   
        }
    });
    var $super = rf.ui.Chart.$super;
})(jQuery,RichFaces);


