function update(action, type, id) {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            window.location.reload();
        }
    };
    request.open('GET', '/' + action + '/' + type + '/' + id, true);
    request.send();
}