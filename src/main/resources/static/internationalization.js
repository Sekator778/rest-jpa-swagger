$(document).ready(function() {
    $("#locales").change(function () {
        const selectedOption = $('#locales').val();
        if (selectedOption !== ''){
            window.location.replace('international/?lang=' + selectedOption);
        }
    });
});