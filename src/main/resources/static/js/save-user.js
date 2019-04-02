function saveUser() {
    var request = new XMLHttpRequest();
    var sexItem = document.getElementsByName("sex");
    var responseItem = document.getElementById("response");
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var city = document.getElementById("city").value;
    var pass = document.getElementById("pass").value;
    var sex;
    sexItem.forEach(function (element) {
        if(element.checked)
            sex = element.value;
    });
    var dateOfBirth = document.getElementById("dateOfBirth").value;
    var bio = document.getElementById("bio").value;
    var interests = document.getElementById("interests").value;
    var params = 'name=' + name + '&surname=' + surname + '&pass=' + pass
        + '&city=' + city + '&sex=' + sex + '&dateOfBirth='
        + dateOfBirth + '&bio=' + bio + '&interests=' + interests;
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            responseItem.innerText = this.responseText;
        }
    };
    request.open('POST', '/edit');
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(params);
}