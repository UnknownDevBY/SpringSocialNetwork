function register2() {
    let request = new XMLHttpRequest();
    let sexItem = document.getElementsByName("sex");
    let sex;
    sexItem.forEach(function (element) {
        if(element.checked)
            sex = element.value;
    });
    if(sex == null)
        return;
    let dateOfBirth = document.getElementById("dateOfBirth").value.trim();
    let bio = document.getElementById("bio").value.trim();
    let city = document.getElementById("city").value.trim();
    let interests = document.getElementById("interests").value.trim();
    let params = 'city=' + city + '&sex=' + sex + '&dateOfBirth=' + dateOfBirth;
    if(bio !== '')
        params += '&bio=' + bio;
    if(interests !== '')
        params += '&interests=' + interests;
    request.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 200) {
                document.getElementById('alert').innerHTML =
                    `<div class="alert alert-success alert-dismissible fade show">
                                <button class="close" data-dismiss="alert">&times;</button>
                                <strong>Для подтверждения регистрации пройдите по ссылке, пришедшей на e-mail</strong>
                            </div>`;
                setTimeout(function() {
                    window.location.replace("/login");
                }, 5000)
            } else {
                document.getElementById('alert').innerHTML =
                    `<div class="alert alert-danger alert-dismissible fade show">
                                <button class="close" data-dismiss="alert">&times;</button>
                                <strong>Что-то не так, попробуйте еще раз</strong>
                            </div>`
            }
        }
    };
    request.open('POST', '/registration/2');
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(params);
}