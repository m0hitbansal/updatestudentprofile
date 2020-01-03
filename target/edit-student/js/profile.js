var profile = "webapi/profile/"+sessionStorage.getItem("ID");
var StudentMeta;
var all_domains;

$.get(profile, function (student, status) {
    var api = "webapi/domain";
    $.get(api, function (domain, status) {
        if (status == "success") {
            all_domains = domain;
            var domainOptions = "";
            for (var i = 1; i <= domain.length; i++) {
                if(i===StudentMeta.domain.id){
                    domainOptions += '<option value="' + domain[i-1].id + '" selected>' + domain[i-1].discipline + ' ' + domain[i-1].branch + '</option>';
                }
                else{
                    domainOptions += '<option value="' + domain[i-1].id + '">' + domain[i-1].discipline + ' ' + domain[i-1].branch + '</option>';
                }
            }
            $("#domainId").append(domainOptions);
        }
    });
    StudentMeta = student;
    ID = student.id;
    if(status == "success") {
        document.getElementById("firstName").value = student.firstName;
        document.getElementById("middleName").value = student.middleName;
        document.getElementById("lastName").value = student.lastName;
        document.getElementById("emailId").value = student.emailId;
        document.getElementById("rollNumber").value = student.rollNumber;
        document.getElementById("domainId").value = student.domain.id;
        if(student.photograph)
            document.getElementById('preview').src=student.photograph;
        else
            document.getElementById('preview').src="images/icons/placeholder.png"
    }
    else{
        window.alert("Invalid ID");
        location.reload(true);
    }
});


function previewFile() {
    var preview = document.getElementById('preview');
    var fileName = $(".custom-file-label");
    var file = document.querySelector('input[type=file]').files[0]; //sames as here
    var reader = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
        fileName.html(file.name);
    }

    if (file) {
        reader.readAsDataURL(file); //reads the data as a URL
    } else {
        preview.src = "images/icon/placeholder.png";
        fileName.html("Photograph");
    }
}

function update(){
    var name = document.getElementById("firstName");
    var middle=document.getElementById("middleName");
    var last=document.getElementById("lastName");
    var roll =document.getElementById("rollNumber");
    var email =document.getElementById("emailId");
   //regular expression
    var alphaExp = /^[a-zA-Z]+$/;
    var numExp=/^[0-9]+$/;
    var emailExp =/^[a-zA-Z0-9]+\.[a-zA-Z0-9]+@iiitb.org$/;
    //current year fetch
    var d = new Date();
    var n=d.getFullYear();
    //check first name, lastname, middlename check
    if(!name.value.match(alphaExp)){
        window.alert('First Name cannot be numeric');
        location.reload(true);
        return ;
    }
    if(!(middle.value.match(alphaExp)||middle.value.length==0)){
        window.alert('middle Name cannot be numeric');
        location.reload(true);
        return ;
    }
    if(!(last.value.match(alphaExp)||last.value.length==0)){
        window.alert('last Name cannot be numeric');
        location.reload(true);
        return;
    }
    //check email format
    if(!email.value.match(emailExp)){
        window.alert('Email cannot be without @iiitb.org');
        location.reload(true);
        return ;
    }
    //check roll number format
    if(roll.value.length!=9&&roll.value.length!=10){
        window.alert('Roll number is not in format');
        location.reload(true);
        return ;
    }
    if(roll.value.length==9){
        var dom = roll.value.slice(0,2);
        var year= roll.value.slice(2,6);
        var digit= roll.value.slice(6,9);
        if(dom!="MT"||dom!="MS"){
            window.alert('Roll number is not in format');
            location.reload(true);
            return ;
        }
        if(!(year.match(numExp)|| year<"2000"||year>=n.toString())) {
            window.alert('Roll number is not in format');
            location.reload(true);
            return;
        }
        if(!digit.match(numExp)){
            window.alert('Roll number is not in format');
            location.reload(true);
            return;
        }
    }
    if(roll.value.length==10){
        var dom = roll.value.slice(0,3);
        var year= roll.value.slice(3,7);
        var digit= roll.value.slice(7,10);
        if(dom!="IMT"){
            window.alert('Roll number is not in format');
            location.reload(true);
            return ;
        }
        if(!(year.match(numExp)&& year>"2000"&&year<=n.toString())) {
            window.alert('Roll number is not in format');
            location.reload(true);
            return;
        }
        if(!digit.match(numExp)){
            window.alert('Roll number is not in format');
            location.reload(true);
            return;
        }
    }
    var form = $('#admission-form')[0];
    var data = new FormData(form);
    $.ajax({
        type: "POST",
        url:'webapi/profile/update/'+ID,
        enctype:'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        async: true,
        timeout: 60000,
        success:function(student,status){
            alert("Information updated successfully!\n Image updation may take some time");
            window.location.assign("/edit_student_war/studentlist.html");
        },
        error:function (xhr,exception) {
            if( xhr.status === 0)
                alert('Error : ' + xhr.status + 'You are not connected.');
            else if( xhr.status === 201)
                alert('Error : ' + xhr.status + '\nServer error.');
            else if( xhr.status === 404)
                alert('Error : ' + xhr.status + '\nPage note found');
            else if(xhr.status === 403)
                alert("Error : " + xhr.status + "\nStudent with same Roll Number already exists, change and submit!");
            else if( xhr.status === 402)
                alert('Error : ' + xhr.status + '\nPlease select a domain');
            else if( xhr.status === 405)
                alert('Error : ' + xhr.status + '\nFirst Name cannot be blank');
            else if( xhr.status === 406)
                alert('Error : ' + xhr.status + '\nEmail Id cannot be blank');
            else if( xhr.status === 407)
                alert('Error : ' + xhr.status + '\nRoll Number cannot be blank');
            else if( xhr.status === 500)
                alert('Internal Server Error [500].');
            else if (exception === 'parse Error')
                alert('Error : ' + xhr.status + '\nImpossible to parse result.');
            else if (exception === 'Timeout')
                alert('Error : ' + xhr.status + '\nRequest timeout.');
            else
                alert('Error .\n' + xhr.responseText);
            location.reload(true);
        }
    });
}