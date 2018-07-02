
(function($) {
	/* acrolling header */
	var nav_container = $(".headerwrap");
	var nav = $(".site-header");
	var waypoint_offset = 60;

	var top_spacing = 0;
	nav_container.waypoint({
		handler: function(direction) {
			if (direction == 'down') {
				nav_container.css({ 'height':nav.outerHeight() });
				nav.stop().addClass("fixeddiv").css("top",-nav.outerHeight()).animate({"top":top_spacing});
			} else {
				nav_container.css({ 'height':'auto' });
				nav.stop().removeClass("fixeddiv").css("top",nav.outerHeight()).animate({"top":""});
			}
		},
		offset: function() {
			return -nav.outerHeight()-waypoint_offset;
		}
	});
})(jQuery);


$(document).ready(function(){
	/* customizing the drop down menu */
	jQuery('div.nav-container > ul > li > a').append( '<span class="colorbar"></span>' );
    jQuery('div.nav-container ul > li').hover(function() {
        jQuery(this).children('ul.children,ul.sub-menu').stop(true, true).slideDown("fast");
    }, function(){
        jQuery(this).children('ul.children,ul.sub-menu').slideUp("fast");
    });

    /* Go to top button */
    jQuery('body').append('<a href="#" class="go-top animated"><span><i class="fa fa-angle-up" style="width:48px;" aria-hidden="true"></i></span></a>');
    jQuery(window).scroll(function() {
        if (jQuery(this).scrollTop() > 200) {
            jQuery('.go-top').fadeIn(200).addClass( 'bounce' );
        } else {
            jQuery('.go-top').fadeOut("slow");
        }
   });

    // Animate the scroll to top
    jQuery('.go-top').click(function(event) {
        event.preventDefault();
        jQuery('html, body').animate({scrollTop: 0}, 1000);
    });

});


function addFeedback(){
    var name = $("#nameText").val();
    var email = $("#emailText").val();
    var telephone = $("#telephoneText").val();
    var message = $("#messageText").val();

    if(!name || !message){
        alert("please input name or message.")
        return;
    }
    $.post("/feedback/add",
        {"name":name, "email":email, "telePhone": telephone, "content": message}, function(json){
        if(json.code != 0){
            alert(json.message);
        }else{
            alert("You have successfully sent the message, and we will contact you soon.");
        }
    },"json");
}
