var api = "webapi/profile";
$.get(api, function (student, status) {
    if (status == "success") {
        var student_data_body = "";
        for (var i = 0; i < student.length; i++) {
            var domainName = student[i].domain.discipline + " " + student[i].domain.branch;
            var photograph = '<img src="' + student[i].photograph + '" height="80" alt="' + student[i].rollNumber + '">'
            student_data_body += '<tr>'
               // + '<td>' + (i + 1) + '</td>'
                + '<td>' + student[i].rollNumber + '</td>'
                + '<td>' + student[i].firstName + '</td>'
                + '<td>' + student[i].middleName + '</td>'
                + '<td>' + student[i].lastName + '</td>'
                + '<td>' + student[i].emailId + '</td>'
                + '<td>' + domainName + '</td>'
                + '<td align="center">' + photograph + '</td>'
                + '<td> <button class="btn btn-primary btn-block " type="submit" onclick=edit('+student[i].id+')>Edit</button> </td>'
                + '</tr>';
        }
        $('#student_data tbody').html(student_data_body);
    }
    $('#student_data').DataTable();
});

function edit(Id) {
    sessionStorage.setItem("ID",Id);
    window.location.assign("/edit_student_war/index.html");
}

