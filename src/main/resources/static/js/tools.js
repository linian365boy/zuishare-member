
$(document).ready(function(){
    $("div.itemContainer .item").mouseover(function(){
        $(this).addClass("itemHover")
    });

    $("div.itemContainer .item").mouseout(function(){
        $(this).removeClass("itemHover")
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