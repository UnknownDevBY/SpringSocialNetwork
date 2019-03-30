function reg(pattern) {
    if (!pattern.test(String.fromCharCode(event.charCode))) {
        setTimeout(function() {
            console.clear();
        }, 3000);
        event.preventDefault();
    }
}