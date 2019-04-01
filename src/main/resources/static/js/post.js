function newPost(ref, id) {
    var request = new XMLHttpRequest();
    var textareaContent = document.querySelector('textarea[name=content]');
    var params = 'content=' + textareaContent.value;
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            window.location.reload();
        }
    };
    request.open('POST', '/' + ref + '/' + id);
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    request.send(params);
}