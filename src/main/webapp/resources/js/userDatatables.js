var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function changeEnabled(checkbox) {
    var enabled = checkbox[0].checked;
    var userId = checkbox[0].closest('tr').id;
    var log = "User with id = " + userId + (enabled ? "is enabled!" : "is disabled!");
    console.log(log);
    var form = $("#checkbox");
    $.ajax({
        type: "POST",
        url: ajaxUrl + userId,
        data: "state=" + enabled
    }).done(function () {
            successNoty(log);
            checkbox.closest("tr").attr("data-userEnabled", enabled);
        }
    ).fail(function () {
        failNoty("Change status request to " +
            (enabled ? "is enabled " : "is disabled ") +
            "for user with id = " + userId + " failed!");
        checkbox[0].checked = !enabled;
    });
}