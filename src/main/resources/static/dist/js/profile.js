$.fn.dataTable.ext.errMode = 'none';

let bankAccountsDatatable = {};

function initBankAccounts() {
    bankAccountsDatatable = $("#datatable_bank_accounts").DataTable({
        "paging": true,
        "processing": true,
        "serverSide": true,
        "searching": false,
        "scrollCollapse": true,
        "info": false,
        "order": [],
        "pagingType": "numbers",
        "pageLength": 6,
        "lengthChange": false,
        "bFilter": false,
        "ordering": false,
        "bAutoWidth": false,
        // 'stripeClasses':['stripe1','stripe2'],
        "initComplete": function (settings, json) {
            $("#datatable_bank_accounts").hide();
            $("#datatable_bank_accounts thead").hide();
            $("#datatable_bank_accounts_filter label").hide();
        },
        "ajax": {
            "url": "/bank_accounts/data",
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                $("#bank_accounts").html('');
                return response.data;
            }
        },
        "drawCallback": function(  ) {
        },
        "columns": [
            {
                "data": "uuid",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    let result = '<div class="col-12 col-sm-6 col-md-4 d-flex align-items-stretch">\n' +
                        '                                        <div class="card bg-light w-100">\n' +
                        '                                            <div class="card-header text-muted border-bottom-0">\n' +
                        '                                                '+row['number'] +
                        '                                            </div>\n' +
                        '                                            <div class="card-body pt-0">\n' +
                        '                                                <div class="row">\n' +
                        '                                                    <!-- Heading -->\n' +
                        '                                                    <div class="item-heading clearfix">\n' +
                        '                                                        <!-- Heading -->\n' +
                        '                                                        <h3>Amount: '+row['amount']+'</h3>\n' +
                        '                                                        <h3>'+row['currency']+'</h3>\n' +
                        '                                                    </div>\n' +
                        '                                                </div>\n' +
                        '                                            </div>\n' +
                        '                                            <span class="card-footer">\n' +
                        '                                                <span>\n' +
                        '                                                    <a href="#" class="btn btn-sm btn-primary">\n' +
                        '                                                        <i class="fas fa-list"></i> Transactions\n' +
                        '                                                    </a>\n' +
                        '                                                </span>\n';
                    if (!isAdmin) {
                        result += '                                      <span>\n' +
                            '                                                    <a href="#" class="btn btn-sm btn-primary">\n' +
                            '                                                        <i class="fas fa-hand-holding-usd"></i> Make transaction\n' +
                            '                                                    </a>\n' +
                            '                                                </span>\n';
                    }
                    result +='                                       </div>\n' +
                        '                                        </div>\n' +
                        '                                    </div>';
                    $("#bank_accounts").append(result);
                }
            },
        ]
    });
}


$("#bank_account-modal .confirm").prop('disabled', true);

$(document).ready(function (){
    initAmount();

    var url = new URL(window.location.href);
    var bankAccountUuid = url.searchParams.get("showBankAccount");
    if (bankAccountUuid == true) {
        showDetails();
    }
});

function showDetails() {

    $('#process-modal').modal({
        show: true
    });
}

$(document).on('change', '#currency', function() {
    initAmount();
});

$('#bank_account-form').on('keyup change paste', 'input, select', function(){
    validateData();
});

function initAmount() {
    var currency = $("#currency").val();
    if (currency == "USD") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '$ ','min':0,'max':1000000");
    } else if (currency == "EURO") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '€ ','min':0,'max':1000000");
    } else if (currency == "GBP") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '£ ','min':0,'max':1000000");
    }
    $("#amount").val(0);
    $('[data-mask]').inputmask();
}

function validateData() {
    if($("#number").val().replace(/\D/g,'').length == 16 &&
        parseInt($("#amount").val().replace(/\D/g,'')) > 0 ) {
        $("#bank_account-modal .confirm").prop('disabled', false);
    } else {
        $("#bank_account-modal .confirm").prop('disabled', true);
    }
}


$(document).on("click", "#add_bank_account", function(e) {
    $('#bank_account-modal').modal({
        show: true
    });
});

$(document).on("click", "#bank_account-modal .confirm", function(e) {
    var form = $("#bank_account-form");
    $.ajax({
        url: "/bank_accounts/add",
        type: 'POST',
        data: form.serialize(),
        success: function (data) {
            $("#bank_account-modal").modal('hide');
            $('#bank_account-form').trigger("reset");

            bankAccountsDatatable.ajax.reload(null, false);
        },
        error: function (xhr, status, err) {
            var errorMsg = "Server returns error."
            try {
                errorMsg = JSON.parse(xhr.responseText).message;
            } catch (e) { }
            showError('Show details failed.', "Session expired", errorMsg);
        }
    });
});