/*
 *
 * Images fullPlay(改进版)
 * Author: Join Fisher ＆ 臻
 * Version: 2.0 (25-09-2019)
 *
 */

(function($) {
    $.fn.fullImages = function(fisher) {
		
        var fisher = $.extend({
            ImgWidth: null,
            ImgHeight: null,
            autoplay: null,
            fadeTime: null
        },
        fisher)

        var count = $(this).find("img").length; 
        var nValue = 0;
        var oValue = 0;
        var _this = $(this);
       
	    _this.find("img:eq(0)").fadeIn("slow");

        var setIntervalImg = setInterval(function() {
			if(nValue!=-1){
				nValue++;
				if(nValue==3){		//此时为最后一张图片
					nValue=-1;
						$("#gotobutton").css("display","block");	
						$("#mainbg").css("display","block");		
				}
				_this.find(" img:eq(" + (nValue) + ")").fadeIn(fisher.fadeTime);
				_this.find(" img:eq(" + (oValue) + ")").fadeOut(fisher.fadeTime);
				oValue = nValue % count;

        }
		else clearInterval();
		},
        fisher.autoplay);

        resizeFun();
        $(window).resize(function(e) {
            resizeFun();
        });

        function resizeFun() {
		
            /*轮播图全屏*/
            var imgH = fisher.ImgHeight;
            var imgW = fisher.ImgWidth;
            var hValue = imgH / imgW;
            var wValue = imgW / imgH;

            if ($(window).width() / $(window).height() < wValue) {

                _this.find("img").css("width", $(window).height() * wValue);
                _this.find("img").css("margin-left", -(($(window).height() * wValue) - $(window).width()) / 2);
                _this.find("img").css("height", $(window).height());
				
				$("#mainbg").css("width",$(window).height() * wValue);
				$("#mainbg").css("margin-left", -(($(window).height() * wValue) - $(window).width()) / 2);
				$("#mainbg").css("height",$(window).height());
				
				$("#ButtonSize").css("width",($(window).height() * wValue)/6);
				$("#ButtonSize").css("height",($(window).height() * wValue)/6);

            } else {

                _this.find("img").css("width", $(window).width());
                _this.find("img").css("margin-left", 0);
                _this.find("img").css("height", $(window).width() * hValue);

				
				
            }

        }

    };

} (jQuery));