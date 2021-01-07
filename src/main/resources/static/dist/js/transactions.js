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
                "data": "amount",
                "name": "amount",
                "title": "Amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
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
                "data": "amount",
                "name": "amount",
                "title": "Amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
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
                            '                 <a alt="Delete" class="btn btn-danger btn-sm" href="#" onclick="cancelTransaction(\'' + data + '\')">' +
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
                "data": "amount",
                "name": "amount",
                "title": "Amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
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
                            '                 <a alt="Delete" class="btn btn-danger btn-sm" href="#" onclick="cancelTransaction(\'' + data + '\')">' +
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
                "data": "amount",
                "name": "amount",
                "title": "Amount",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    return data;
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
                            '                 <a alt="Delete" class="btn btn-danger btn-sm" href="#" onclick="cancelTransaction(\'' + data + '\')">' +
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
    $('#datatable_bank_account_transactions').append('<button class="btn-default ml-2" onclick="bankAccountTransactionsDatatable.ajax.reload(null, false);">Refresh</button>')
}

$(document).on("click", "#cancel_confirmation .confirm", function(e) {
    let accountId = $('#cancel_confirmation_id').val();
    $.ajax({
        url: "/transactions/cancel?id="+transactionId,
        type: "DELETE",
        success: function (data) {
            accountsDatatable.ajax.reload(null, false);
            $("#cancel_confirmation").modal('hide');
            $('#cancel_confirmation_id').val('');
        },
        error: function (xhr, status, err) {
            showError('Delete account failed.', "Error received", JSON.parse(xhr.responseText).message);
        }
    });
});

function cancelTransaction(transactionId) {
    console.log("Cancel transaction id: " + transactionId);
    $('#delete_account_id').val(transactionId);
    $("#cancel_confirmation .close").on("click", function(e) {
        $("#cancel_confirmation").modal('hide');
    });
    $('#cancel_confirmation').modal('show');
}
