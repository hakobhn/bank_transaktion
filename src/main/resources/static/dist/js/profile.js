$.fn.dataTable.ext.errMode = 'none';

let bankAccountsDatatable = {};

function initBankAccounts(ownerUuid) {
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
            "url": "/bank_accounts/data_owner/"+ownerUuid,
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
                        '                                                <h3 class="card-title font-weight-bold"><i class="fas fa-key"></i> '+row["number"]+'</h3>\n';
                    if (isAdmin) {
                        result += '<div class="card-tools">\n' +
                            '                  <button type="button" class="btn btn-sm btn-danger mt-0" title="Remove bank account" onclick="deleteBankAccount(\''+row["uuid"]+'\')">' +
                            '                      <i class="fas fa-trash"></i>\n' +
                            '                  </button>\n' +
                            '                </div>';
                    }
                    result +='                                       </div>\n'+
                        '                                            <div class="card-body pt-0">\n' +
                        '                                                <div class="row">\n' +
                        '                                                    <!-- Heading -->\n' +
                        '                                                    <div class="item-heading clearfix">\n' +
                        '                                                        <!-- Heading -->\n' +
                        '                                                        <h3>Amount: '+row['amount']+'</h3>\n' +
                        '                                                        <h3>Available: '+row['availableAmount']+'</h3>\n' +
                        '                                                        <h3>'+row['currency']+'</h3>\n' +
                        '                                                    </div>\n' +
                        '                                                </div>\n' +
                        '                                            </div>\n' +
                        '                                            <span class="card-footer">\n' +
                        '                                                <span>\n' +
                        '                                                    <a href="/transactions/list_bank_account/'+row['uuid']+'" class="btn btn-sm btn-primary">\n' +
                        '                                                        <i class="fas fa-list"></i> Transactions\n' +
                        '                                                    </a>\n' +
                        '                                                </span>\n';
                    if (!isAdmin) {
                        result += '                                      <span>\n' +
                            '                                                 <a href="javascript:void(0);" id="'+row['number']+'" data-currency="'+row['currency']+'" class="make_transaction btn btn-sm btn-primary">\n' +
                            '                                                      <i class="fas fa-hand-holding-usd"></i> Make transaction\n' +
                            '                                                 </a>\n' +
                            '                                            </span>\n';
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

function deleteBankAccount(uuid) {
    $('#delete_bank_account_uuid').val(uuid);
    $("#delete_confirmation .close").on("click", function(e) {
        $("#delete_confirmation").modal('hide');
    });
    $('#delete_confirmation').modal('show');
}

$(document).on("click", "#delete_confirmation .confirm", function(e) {
    let uuid = $('#delete_bank_account_uuid').val();
    $.ajax({
        url: "/bank_accounts/delete?uuid="+uuid,
        type: "DELETE",
        success: function (data) {
            bankAccountsDatatable.ajax.reload(null, false);
            $("#delete_confirmation").modal('hide');
            $('#delete_account_id').val('');
        },
        error: function (xhr, status, err) {
            alertError(xhr);
        }
    });
});


$("#bank_account-modal .confirm").prop('disabled', true);

$(document).on('change', '#bank_account_currency', function() {
    initBankAccountAmount();
});

$('#bank_account-form').on('keyup change paste', 'input, select', function(){
    validateBankAccount();
});

function initBankAccountAmount() {
    var currency = $("#bank_account_currency").val();
    if (currency == "USD") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '$ ','min':0,'max':1000000");
    } else if (currency == "EUR") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '€ ','min':0,'max':1000000");
    } else if (currency == "GBP") {
        $("#amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '£ ','min':0,'max':1000000");
    }
    $("#bank_account_amount").val(0);
    $('[data-mask]').inputmask();
}

function validateBankAccount() {
    if($("#bank_account_number").val().replace(/\D/g,'').length == 16 &&
        parseInt($("#bank_account_amount").val().replace(/\D/g,'')) > 0 ) {
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
            alertError(xhr);
        }
    });
});







$("#make_transaction-modal .confirm").prop('disabled', true);

$('#make_transaction-form').on('keyup change paste', 'input, select', function(){
    validateTransaction();
});

function initTransactionAmount(currency) {
    if (currency == "USD") {
        $("#transaction_amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '$ ','min':0,'max':1000000");
    } else if (currency == "EUR") {
        $("#transaction_amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '€ ','min':0,'max':1000000");
    } else if (currency == "GBP") {
        $("#transaction_amount").attr('data-inputmask', "'alias': 'currency', 'rightAlign': false, 'prefix': '£ ','min':0,'max':1000000");
    }
    $("#transaction_amount").val(0);
    $('[data-mask]').inputmask();
}

function validateTransaction() {
    if($("#transaction_to_number").val().replace(/\D/g,'').length == 16 &&
        parseInt($("#transaction_amount").val().replace(/\D/g,'')) > 0 ) {
        $("#make_transaction-modal .confirm").prop('disabled', false);
    } else {
        $("#make_transaction-modal .confirm").prop('disabled', true);
    }
}

$(document).on("click", ".make_transaction", function(e) {
    // console.log("FromNumber: "+$(this).attr("id"));
    $("#make_transaction_from_number").val($(this).attr("id"));

    initTransactionAmount($(this).attr("data_currency"));

    $('#make_transaction-modal').modal({
        show: true
    });
});

$(document).on("click", "#make_transaction-modal .confirm", function(e) {
    var form = $("#make_transaction-form");
    console.log(JSON.stringify(form.serialize()));
    $.ajax({
        url: "/transactions/add",
        type: 'POST',
        data: form.serialize(),
        success: function (data) {
            $("#make_transaction-modal").modal('hide');
            $('#make_transaction-form').trigger("reset");

            bankAccountsDatatable.ajax.reload(null, false);
        },
        error: function (xhr, status, err) {
            alertError(xhr);
        }
    });
});