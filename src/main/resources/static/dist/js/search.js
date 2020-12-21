let tasksDatatable = {};

function initSearchTasks(term) {

    tasksDatatable = $("#datatable_tasks").DataTable({
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
        "createdRow": function (row, data, dataIndex) {
            let info = "";
            // console.log("=========== data.content: " + JSON.stringify(data['content']));
            Object.keys(data['content']).forEach(function(key) {
                info += key + "\n";
                for (var i = 0; i < data['content'][key].length; i++) {
                    info += "    " + data['content'][key][i]['collection']+"\n";
                }
            });
            // console.log(info);
            $(row).attr('title', info);
        },
        "initComplete": function (settings, json) {
        },
        "search": {
            "search": term
        },
        "ajax": {
            "url": "/management/tasks/data",
            "type": "POST",
            "datatype": "json",
            "dataSrc": function (response) {
                // console.log("ajax load: "+JSON.stringify(response));
                $('#tasks_count').html(response.recordsActive);
                return response.data;
            },
        },
        "columns": [
            {
                "data": "source",
                "name": "source",
                "title": "Source",
                "orderable": true,
                "searchable": true,
            },
            {
                "data": "target",
                "name": "target",
                "title": "Target",
                "orderable": true,
                "searchable": true,
            },
            {
                "data": "owner",
                "name": "owner",
                "title": "Created",
                "width": "30%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    return printAccount(data);
                }
            },
            {
                "data": "progress",
                "name": "progress",
                "title": "Task Progress",
                "orderable": true,
                "searchable": false,
                "type": 'datetime',
                "def": function () {
                    return new Date();
                },
                "displayFormat": 'yyyy-MM-dd HH:mm:ss',
                "render": function (data, type, row) {
                    return '<div id=task_' + row["id"] + ' class="progress progress-sm">\n' +
                        '        <div id=progress_bar_' + row["id"] + ' class="' + getStatusClass(row['status']) + '" role="progressbar" aria-volumenow="' + data + '" ' +
                        'aria-volumemin="0" aria-volumemax="100" style="width: ' + data + '%">\n' +
                        '        </div>\n' +
                        '   </div>\n' +
                        '   <small>\n' +
                        '        <span id=progress_value_' + row["id"] + ' class="progress_value">' + data + '</span>% Complete\n' +
                        '   </small>';
                }
            },
            {
                "data": "status",
                "name": "status",
                "title": "Status",
                "orderable": true,
                "searchable": false,
                "render": function (data, type, row) {
                    var result = '<div id=task_status_' + row["id"] + ' class="status-block">\n' +
                        getStatusView(data);
                    if (row["endDate"] != null) {
                        // console.log(showDateDiff(row["startDate"], row["endDate"]));
                        result += '<br/><span class="badge badge-info">'+showDateDiff(row["startDate"], row["endDate"])+'</span>';
                    }
                    result += '</div>';
                    return result;
                }
            },
            {
                "data": "id",
                "name": "",
                "title": "",
                "width": "12%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    return '<a class="btn btn-primary btn-sm" href="/management/tasks?showTask='+ data +'">\n' +
                        '        <i class="fas fa-list"></i> View\n' +
                        '   </a></span>\n';
                }
            },
        ]
    });
    $('#datatable_tasks_filter').append('<button class="btn-default ml-2" onclick="tasksDatatable.ajax.reload(null, false);">Refresh</button>');
    $("#datatable_tasks_processing").css("visibility", "hidden");
}

function getStatusView(status) {
    if (status == 'New') {
        return '<span class="badge bg-gray-light">New</span>';
    } else if (status == 'Pending') {
        return '<span class="badge badge-warning">Pending</span>';
    } else if (status == 'InProgress') {
        return '<span class="badge badge-primary">In progress</span>';
    } else if (status == 'Failed') {
        return '<span class="badge badge-danger">Failed</span>';
    } else if (status == 'Completed') {
        return '<span class="badge badge-success">Completed</span>';
    } else if (status == 'Canceled') {
        return '<span class="badge badge-info">Canceled</span>';
    }
}

function getStatusClass(status) {
    if (status == 'New') {
        return 'progress-bar bg-gray-light';
    } else if (status == 'Pending') {
        return 'progress-bar bg-warning';
    } else if (status == 'InProgress') {
        return 'progress-bar progress-bar-striped progress-bar-animated';
    } else if (status == 'Failed') {
        return 'progress-bar bg-danger';
    } else if (status == 'Completed') {
        return 'progress-bar bg-success';
    } else if (status == 'Canceled') {
        return 'progress-bar bg-info';
    }
}

let usersDatatable = {};

function initSearchUsers(term) {

    usersDatatable = $("#datatable_users").DataTable({
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
        "search": {
            "search": term
        },
        "ajax": {
            "url": "/users/data",
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
                    result += row['id'] == 'unknown' ? 'javascript: void(0);' : '/users/profile/'+row['id'];
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
                "data": "bio",
                "name": "bio",
                "title": "Bio",
                "orderable": false,
                "searchable": true,
                "render": $.fn.dataTable.render.text()
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
                "data": "id",
                "name": "",
                "title": "",
                "width": "12%",
                "orderable": false,
                "searchable": false,
                "render": function (data, type, row) {
                    return '<a class="btn btn-primary btn-sm" href="/users/profile/'+ data +'">\n' +
                        '        <i class="fas fa-list"></i> View\n' +
                        '   </a></span>\n';
                }
            },
        ]
    });
    $('#datatable_users_filter').append('<button class="btn-default ml-2" onclick="usersDatatable.ajax.reload(null, false);">Refresh</button>')
}