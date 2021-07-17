function postComment() {
    var $input =$("#userComment");
    var comment = $input.val();

    $.ajax({
        url: "http://localhost:8090/springPrj/add",
        data: {"comment": comment},
        success: function (result){
            var html=$("<li>").text(comment);
            html.prependTo('#comments');
        }
    })
}


