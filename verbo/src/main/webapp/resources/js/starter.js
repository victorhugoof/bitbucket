$(document).on("pfAjaxComplete", function () {
                    var $messages = $("div[id$='msgs']");

                    if ($messages.length) {
                        var wordCount = $messages.text().split(/\W/).length;
                        var readingTimeMillis = 2500 + (wordCount * 200);

                        setTimeout(function () {
                            $messages.slideUp();
                        }, readingTimeMillis);
                    }

});

$(document).ready(function () {
                    var $messages = $("div[id$='msgs']");

                    if ($messages.length) {
                        var wordCount = $messages.text().split(/\W/).length;
                        var readingTimeMillis = 2500 + (wordCount * 200);

                        setTimeout(function () {
                            $messages.slideUp();
                        }, readingTimeMillis);
                    }
                    
                    enterTab();

});
	

function enterTab(){
	$('body').on('keydown', 'input, select, textarea', function(e) {
	    var self = $(this)
	      , form = self.parents('form:eq(0)')
	      , focusable
	      , next
	      ;
	    if (e.keyCode == 13) {
	        focusable = form.find('input,a,button,select,textarea').filter(':visible');
	        next = focusable.eq(focusable.index(this)+1);
	        if (next.length) {
	            next.focus();
	        } else {
	            return false;
	        }
	        return false;
	    }
	});
}

function soNumber(){
	$(document).ready(function() {
	    $(".usuarioLogin").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl/cmd+A
	            (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
	             // Allow: Ctrl/cmd+C
	            (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
	             // Allow: Ctrl/cmd+X
	            (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
	             // Allow: home, end, left, right
	            (e.keyCode >= 35 && e.keyCode <= 39)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	});
	
}


