$(document).ready(function(){
    $("div.itemContainer .item").mouseover(function(){
        $(this).addClass("itemHover itemHoverIndex1")
    });

    $("div.itemContainer .item").mouseout(function(){
        $(this).removeClass("itemHover itemHoverIndex1")
    });


});