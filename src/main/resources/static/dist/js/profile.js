$('#add_bank_account-button').prop('disabled', true);

$(document).ready(function (){
    initAmount();
});

$(document).on('change', '#currency', function() {
    initAmount();
});

// $('#bank_account-form.form-control').each(function() {
//     console.log("validation....")
//     validateData();
// });

$('#bank_account-form').on('keyup change paste', 'input, select', function(){
    console.log('Form changed!');
    validateData();
});

function initAmount() {
    var currency = $("#currency").val();
    if (currency == "USD") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '$ '");
    } else if (currency == "EURO") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '€ '");
    } else if (currency == "GBP") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '£ '");
    }
    $("#amount").val(0);
    $('[data-mask]').inputmask();
}

function validateData() {
    console.log($("#number").val().replace(/\D/g,''));
    console.log(parseInt($("#amount").val().replace(/\D/g,'')));
    if($("#number").val().replace(/\D/g,'').length == 16 &&
        parseInt($("#amount").val().replace(/\D/g,'')) > 0 ) {
        $('#add_bank_account-button').prop('disabled', false);
    } else {
        $('#add_bank_account-button').prop('disabled', true);
    }
}


$(document).on("click", "#add_bank_account", function(e) {
    $('#bank_account-modal').modal({
        show: true
    });
});
