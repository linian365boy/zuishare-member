$(document).ready(function(){
    $("div.itemContainer .item").mouseover(function(){
        $(this).addClass("itemHover")
    });

    $("div.itemContainer .item").mouseout(function(){
        $(this).removeClass("itemHover")
    });


});