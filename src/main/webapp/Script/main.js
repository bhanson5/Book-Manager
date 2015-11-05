/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//$(document).click( ".open-editDialog" function () {
//     var Id = $(this).data('id');
//     alert(Id);
////     $("#editID").val( Id );
//});

$(".editDialog").click(function(){
        var Id = $(this).data('id');
        $("#editID").val( Id );
    });
