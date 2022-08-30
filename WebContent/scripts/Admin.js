toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": true,
    "progressBar": false,
    "positionClass": "toast-top-right",
    "preventDuplicates": true,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "2000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

function deleteRowFromTable(elementId, quizId) {
    let result = confirm("Are you sure you want to delete?");
    if (!result)
        return
    $.ajax({
        type: 'POST',
        url: './deleteQuiz',
        data: {
            'quizId': quizId
        },
        success: [function (msg) {
            document.getElementById(elementId).remove();
            toastr["success"]("Removed from database.");
        }]
    });
}

function logout() {
    $.ajax({
        type: 'GET',
        url: './login',
        data: {
            'logout': true
        },
        success: [function (msg) {
            window.location = "./login.jsp";
        }]
    });
}

function openTab(evt, tabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

function deleteUser(elementId, username, currentUsername) {
    let result = confirm("Are you sure you want to delete user?");
    if (!result)
        return;

    if (username === currentUsername) {
        toastr["error"]("You can't remove yourself!");
        return;
    }
    $.ajax({
        type: 'POST',
        url: './deleteUser',
        data: {
            'userToRemove': username
        },
        success: [function (msg) {
            document.getElementById(elementId).remove();
            toastr["success"]("Removed from database.");
        }]
    });
}

function exportResults(userId) {
    $.ajax({
        type: 'GET',
        url: './export-results',
        data: {
            'userId': userId
        },
        success: [function (msg) {
            toastr["success"]("Results are exported to csv file!");
        }]
    });
}

function deleteQuestion(questionId) {
    $.ajax({
        type: 'POST',
        url: './deleteQuestion',
        data: {
            'questionId': questionId
        },
        success: [function (msg) {
            toastr["success"]("Question deleted from quiz!");
            setTimeout(function(){ window.location.href='/admin/admin.jsp'; }, 1000);
        }]
    })
}