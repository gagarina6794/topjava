var ajaxUrl = "ajax/meals/";
var datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Update",
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

function clearFilter(){
    $("#filterForm").find(":input").val("");
    updateTable();
}

function updateTable() {
    $.post(
        ajaxUrl + "filter",
        $("#filterForm").serialize(),
        function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}