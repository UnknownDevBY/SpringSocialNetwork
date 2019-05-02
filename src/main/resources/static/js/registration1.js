function register1() {
    let request = new XMLHttpRequest();
    let name = document.getElementById("name").value;
    let surname = document.getElementById("surname").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let params = 'name=' + name + '&surname=' + surname + '&email='
        + email + '&password=' + password;
    request.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 200) {
                    window.location.href = '/registration/2'
            } else {
                document.getElementById('alert').innerHTML =
                    `<div class="alert alert-danger alert-dismissible fade show">
                                <button class="close" data-dismiss="alert">&times;</button>
                                <strong>Пользователь с таким e-mail уже существует</strong>
                      </div>`
            }
        }
    };
    request.open('POST', '/registration/1');
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(params);
}