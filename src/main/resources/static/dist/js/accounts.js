let accountsDatatable = {};

function initAccounts() {

    accountsDatatable = $("#datatable_accounts").DataTable({
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
            "url": "/accounts/data",
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                return response.data;
            }
        },
        "columnDefs": [{"targets": 1,"className": 'truncate'}],
        "columns": [
            {
                "data": "email",
                "name": "email",
                "title": "Main data",
                "width": "30%",
                "orderable": true,
                "searchable": true,
                "render": function (data, type, row) {
                    let result = '<div class="user-block">\n' +
                        '      <img class="img-circle img-bordered-lg elevation-3" src="' + row['avatar'] + '" alt="' + row['email'] + '">\n' +
                        '      <span class="username">\n' +
                        '          <a href="';
                    result += row['id'] == 'unknown' ? 'javascript: void(0);' : '/accounts/profile/'+row['uuid'];
                    result += '">'+ cleanText(row['firstName'] + ' ' + row['lastName']) + '</a>\n' +
                        '      </span>\n' +
                        '      <span class="description font-weight-bold">' + row['email'] + '</span>\n' +
                        '      <span class="roles">\n' +
                        '          <span class="description">' + row['roles'] + '</span>\n' +
                        '      </span>\n';
                    if (row['lastLoginDate'] != null) {
                        result += '<span class="description">Last visit  ' + row['lastLoginDate'] + '</span>';
                    } else {
                        result += '<span class="description">Not logged in yet</span>';
                    }
                    result += '   </div>';
                    return result;
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
                        result = '<div class="container">\n' +
                            '       <div class="row">' +
                            '             <div class="col-12 col-md-3">' +
                            '                 <a alt="Manage bank accounts" href="/accounts/profile/'+ row["uuid"]+'" class="btn btn-sm bg-teal">' +
                            '                     <i class="fas fa-money-check"></i>' +
                            '                 </a>' +
                            '             </div>'+
                            '             <div class="col-12 col-md-3">' +
                            '                 <a alt="Edit" class="btn btn-primary btn-sm" href="/accounts/edit/'+ row["uuid"]+'">' +
                            '                     <i class="fas fa-edit"></i>' +
                            '                 </a>\n' +
                            '             </div>'+
                            '             <div class="col-12 col-md-3">' +
                            '                 <a alt="Delete" class="btn btn-danger btn-sm" href="#" onclick="deleteAccount(\'' + data + '\')">' +
                            '                     <i class="fas fa-trash"></i>' +
                            '                 </a>\n';
                            '             </div>'+
                            '        </div>' +
                            '    </div>';
                    return result;
                }
            },
        ]
    });
    $('#datatable_accounts_filter').append('<button class="btn-default ml-2" onclick="accountsDatatable.ajax.reload(null, false);">Refresh</button>')
}

$(document).on("click", "#delete_confirmation .confirm", function(e) {
    let accountId = $('#delete_account_id').val();
    console.log("Delete accountId: " + accountId);
    $.ajax({
        url: "/accounts/delete?uuid="+accountId,
        type: "DELETE",
        success: function (data) {
            accountsDatatable.ajax.reload(null, false);
            $("#delete_confirmation").modal('hide');
            $('#delete_account_id').val('');
        },
        error: function (xhr, status, err) {
            alertError(xhr);
        }
    });
});

function deleteAccount(accountId) {
    // console.log("Delete account id: " + accountId);
    $('#delete_account_id').val(accountId);
    $("#delete_confirmation .close").on("click", function(e) {
        $("#delete_confirmation").modal('hide');
    });
    $('#delete_confirmation').modal('show');
}
