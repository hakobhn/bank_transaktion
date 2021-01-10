let transactionsDatatable = {};
let fromTransactionsDatatable = {};
let toTransactionsDatatable = {};
let bankAccountTransactionsDatatable = {};

function initTransactions() {

    transactionsDatatable = $("#datatable_transactions").DataTable({
        "paging": true,
        "processing": true,
        "serverSide": true,
        "searching": true,
        "scrollCollapse": true,
        "info": false,
        "order": [],
        "pageLength": 25,
        "ordering": true,
        "bAutoWidth": false,
        // 'stripeClasses':['stripe1','stripe2'],
        "initComplete": function (settings, json) {
        },
        "ajax": {
            "url": "/transactions/data",
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                return response.data;
            }
        },
        "columnDefs": [{"targets": 1,"className": 'truncate'}],
        "columns": [
            {
                "data": "from",
                "name": "from",
                "title": "From bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "to",
                "name": "to",
                "title": "To bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "fromAmount",
                "name": "fromAmount",
                "title": "From amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['from']['currency']+'</span';
                }
            },
            {
                "data": "toAmount",
                "name": "toAmount",
                "title": "To amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['to']['currency']+'</span';
                }
            },
            {
                "data": "type",
                "name": "type",
                "title": "Type",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "status",
                "name": "status",
                "title": "Status",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "createdDate",
                "name": "createdDate",
                "title": "CreatedAt",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return moment(data).format("DD/MM/YYYY");
                }
            },
            {
                "data": "uuid",
                "name": "",
                "title": "",
                "width": "13%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    let result = '';
                    if (row['status']=='PENDING') {
                        let result = '';
                        result = '<div class="container">\n' +
                            '       <div class="row">' +
                            '             <div class="col-12 col-md-6">' +
                            '                 <a alt="Accept transaction" href="javascript:void(0);" data-uuid="'+row["uuid"]+'" class="accept_transaction btn btn-sm btn-success">' +
                            '                     <i class="fas fa-check">Accept</i> ' +
                            '                 </a>' +
                            '             </div>'+
                            '             <div class="col-12 col-md-6">' +
                        '                     <a alt="Reject transaction" href="javascript:void(0);" data-uuid="'+row["uuid"]+'" class="reject_transaction btn btn-sm btn-danger">' +
                        '                         <i class="fas fa-ban">Reject</i>' +
                        '                     </a>' +
                        '                 </div>'+
                        '            </div>' +
                        '    </div>';
                        return result;
                    }
                    return result;
                }
            },
        ]
    });
    $('#datatable_transactions_filter').append('<button class="btn-default ml-2" onclick="transactionsDatatable.ajax.reload(null, false);">Refresh</button>')
}

function initOwnerTransactions() {

    fromTransactionsDatatable = $("#datatable_from_transactions").DataTable({
        "paging": true,
        "processing": true,
        "serverSide": true,
        "searching": true,
        "scrollCollapse": true,
        "info": false,
        "order": [],
        "pageLength": 25,
        "ordering": true,
        "bAutoWidth": false,
        // 'stripeClasses':['stripe1','stripe2'],
        "initComplete": function (settings, json) {
        },
        "ajax": {
            "url": "/transactions/data",
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                return response.data;
            }
        },
        "columnDefs": [{"targets": 1,"className": 'truncate'}],
        "columns": [
            {
                "data": "from",
                "name": "from",
                "title": "From bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "to",
                "name": "to",
                "title": "To bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "fromAmount",
                "name": "fromAmount",
                "title": "From amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['from']['currency']+'</span';
                }
            },
            {
                "data": "toAmount",
                "name": "toAmount",
                "title": "To amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['to']['currency']+'</span';
                }
            },
            {
                "data": "type",
                "name": "type",
                "title": "Type",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "status",
                "name": "status",
                "title": "Status",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "createdDate",
                "name": "createdDate",
                "title": "CreatedAt",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return moment(data).format("DD/MM/YYYY");
                }
            },
            {
                "data": "uuid",
                "name": "",
                "title": "",
                "width": "13%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    let result = '';
                    if (row['status']=='PENDING') {
                        result = '<div class="container">\n' +
                            '       <div class="row">' +
                            '             <div class="col-12">' +
                            '                 <a href="javascript:void(0);" data-uuid="'+row["uuid"]+'" class="cancel_transaction btn btn-danger btn-sm" >' +
                            '                     <i class="fas fa-trash">Cancel</i>' +
                            '                 </a>\n';
                        '             </div>' +
                        '        </div>' +
                        '    </div>';
                    }
                    return result;
                }
            },
        ]
    });
    $('#datatable_from_transactionss_filter').append('<button class="btn-default ml-2" onclick="fromTransactionsDatatable.ajax.reload(null, false);">Refresh</button>')

    toTransactionsDatatable = $("#datatable_to_transactions").DataTable({
        "paging": true,
        "processing": true,
        "serverSide": true,
        "searching": true,
        "scrollCollapse": true,
        "info": false,
        "order": [],
        "pageLength": 25,
        "ordering": true,
        "bAutoWidth": false,
        // 'stripeClasses':['stripe1','stripe2'],
        "initComplete": function (settings, json) {
        },
        "ajax": {
            "url": "/transactions/data",
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                return response.data;
            }
        },
        "columnDefs": [{"targets": 1,"className": 'truncate'}],
        "columns": [
            {
                "data": "from",
                "name": "from",
                "title": "From bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "to",
                "name": "to",
                "title": "To bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "fromAmount",
                "name": "fromAmount",
                "title": "From amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                        return data + '<span>'+row['from']['currency']+'</span';
                }
            },
            {
                "data": "toAmount",
                "name": "toAmount",
                "title": "To amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['to']['currency']+'</span';
                }
            },
            {
                "data": "type",
                "name": "type",
                "title": "Type",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "status",
                "name": "status",
                "title": "Status",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "createdDate",
                "name": "createdDate",
                "title": "CreatedAt",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return moment(data).format("DD/MM/YYYY");
                }
            },
            {
                "data": "uuid",
                "name": "",
                "title": "",
                "width": "13%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    let result = '';
                    if (row['status']=='PENDING') {
                        result = '<div class="container">\n' +
                            '       <div class="row">' +
                            '             <div class="col-12">' +
                            '                 <a href="javascript:void(0);" data-uuid="'+row["uuid"]+'" class="cancel_transaction btn btn-danger btn-sm" >' +
                            '                     <i class="fas fa-trash">Cancel</i>' +
                            '                 </a>\n';
                        '             </div>' +
                        '        </div>' +
                        '    </div>';
                    }
                    return result;
                }
            },
        ]
    });
    $('#datatable_to_transactionss_filter').append('<button class="btn-default ml-2" onclick="toTransactionsDatatable.ajax.reload(null, false);">Refresh</button>')
}

function initBankAccountTransactions(bankAccountUuid) {

    bankAccountTransactionsDatatable = $("#datatable_bank_account_transactions").DataTable({
        "paging": true,
        "processing": true,
        "serverSide": true,
        "searching": true,
        "scrollCollapse": true,
        "info": false,
        "order": [],
        "pageLength": 25,
        "ordering": true,
        "bAutoWidth": false,
        // 'stripeClasses':['stripe1','stripe2'],
        "initComplete": function (settings, json) {
        },
        "ajax": {
            "url": "/transactions/data_bank_account/"+bankAccountUuid,
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                return response.data;
            }
        },
        "columnDefs": [{"targets": 1, "className": 'truncate'}],
        "columns": [
            {
                "data": "from",
                "name": "from",
                "title": "From bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "to",
                "name": "to",
                "title": "To bank account",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data['number'];
                }
            },
            {
                "data": "fromAmount",
                "name": "fromAmount",
                "title": "From amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['from']['currency']+'</span';
                }
            },
            {
                "data": "toAmount",
                "name": "toAmount",
                "title": "To amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data + '<span>'+row['to']['currency']+'</span';
                }
            },
            {
                "data": "type",
                "name": "type",
                "title": "Type",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "status",
                "name": "status",
                "title": "Status",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "createdDate",
                "name": "createdDate",
                "title": "CreatedAt",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return moment(data).format("DD/MM/YYYY");
                }
            },
            {
                "data": "uuid",
                "name": "",
                "title": "",
                "width": "13%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    let result = '';
                    if (row['status'] == 'PENDING') {
                        result = '<div class="container">\n' +
                            '       <div class="row">' +
                            '             <div class="col-12">' +
                            '                 <a href="javascript:void(0);" data-uuid="'+row["uuid"]+'" class="cancel_transaction btn btn-danger btn-sm" >' +
                            '                     <i class="fas fa-trash">Cancel</i>' +
                            '                 </a>\n';
                        '             </div>' +
                        '        </div>' +
                        '    </div>';
                    }
                    return result;
                }
            },
        ]
    });
    $('#datatable_bank_account_transactions_filter').append('<button class="btn-default ml-2" onclick="bankAccountTransactionsDatatable.ajax.reload(null, false);">Refresh</button>')
}

$(document).on("click", ".cancel_transaction", function(e) {
    let transactionUuid = $(this).attr("data-uuid");
    cancelTransaction(transactionUuid);
});

function cancelTransaction(transactionUuid) {
    console.log("Cancel transaction uuid: " + transactionUuid);
    $('#cancel_confirmation_uuid').val(transactionUuid);
    $("#cancel_confirmation .close").on("click", function(e) {
        $("#cancel_confirmation").modal('hide');
        $('#cancel_confirmation_uuid').val('');
    });
    $('#cancel_confirmation').modal('show');
}

$(document).on("click", "#cancel_confirmation .confirm", function(e) {
    let transactionUuid = $('#cancel_confirmation_uuid').val();
    console.log("Cancel transaction uuid: " + transactionUuid);
    let processJson = JSON.stringify({'uuid':transactionUuid, 'status':'CANCELED'});

    console.log(processJson);

    $.ajax({
        url: "/transactions/cancel",
        type: "DELETE",
        data: processJson,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            fromTransactionsDatatable.ajax.reload(null, false);
            toTransactionsDatatable.ajax.reload(null, false);
            $("#cancel_confirmation").modal('hide');
            $('#cancel_confirmation_uuid').val('');
        },
        error: function (xhr, status, err) {
            alertError(xhr);
        }
    });
});



$(document).on("click", ".accept_transaction", function(e) {
    let transactionUuid = $(this).attr("data-uuid");
    acceptTransaction(transactionUuid);
});

function acceptTransaction(transactionUuid) {
    console.log("Accept transaction uuid: " + transactionUuid);
    $('#accept_transaction_confirmation_id').val(transactionUuid);
    $("#accept_transaction_confirmation .close").on("click", function(e) {
        $("#accept_transaction_confirmation").modal('hide');
        $('#accept_transaction_confirmation_id').val('');
    });
    $('#accept_transaction_confirmation').modal('show');
}

$(document).on("click", "#accept_transaction_confirmation .confirm", function(e) {
    let transactionUuid = $('#accept_transaction_confirmation_id').val();
    let processJson = JSON.stringify({'uuid':transactionUuid, 'status':'ACCEPTED'});

    $.ajax({
        url: "/transactions/processing",
        type: "PUT",
        data: processJson,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(JSON.stringify(data));
            transactionsDatatable.ajax.reload(null, false);
            $("#accept_transaction_confirmation").modal('hide');
            $('#accept_transaction_confirmation_id').val('');
        },
        error: function (xhr, status, err) {
            alertError(xhr);
        }
    });
});

$(document).on("click", ".reject_transaction", function(e) {
    let transactionUuid = $(this).attr("data-uuid");
    rejectTransaction(transactionUuid);
});

function rejectTransaction(transactionUuid) {
    console.log("Reject transaction uuid: " + transactionUuid);
    $('#reject_transaction_confirmation_id').val(transactionUuid);
    $("#reject_transaction_confirmation .close").on("click", function(e) {
        $("#reject_transaction_confirmation").modal('hide');
        $('#reject_transaction_confirmation_id').val('');
    });
    $('#reject_transaction_confirmation').modal('show');
}

$(document).on("click", "#reject_transaction_confirmation .confirm", function(e) {
    let transactionUuid = $('#reject_transaction_confirmation_id').val();
    console.log("Reject uuid: " + transactionUuid);
    let processJson = JSON.stringify({'uuid':transactionUuid, 'status':'REJECTED'});

    $.ajax({
        url: "/transactions/processing",
        type: "PUT",
        data: processJson,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(JSON.stringify(data));
            transactionsDatatable.ajax.reload(null, false);
            $("#reject_transaction_confirmation").modal('hide');
            $('#reject_transaction_confirmation_id').val('');
        },
        error: function (xhr, status, err) {
            alertError(xhr);
        }
    });
});