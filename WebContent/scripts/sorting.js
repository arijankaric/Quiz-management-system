let newOrder = []

$( function() {
    let tempArray = [];
    $( "#sortable" ).sortable({
        stop: function(event, ui) {
            tempArray = [];
            var itemOrder = $('#sortable').sortable("toArray");

            for (var i = 0; i < itemOrder.length; i++) {
                console.log("Position: " + i + " ID: " + itemOrder[i]);
                tempArray.push((itemOrder[i]));
            }
            newOrder = tempArray.slice();
            console.log(newOrder);
        }
    });

});

function onSortModalClose() {
    const quizId = document.getElementById('quizIdForQuestion').value;
    const indexes = newOrder.toString();
    $.ajax({
        type: 'POST',
        url: './sortQuestions',
        data: {
            'quizId': quizId,
            'indexes': indexes
        },
        success: [function (msg) {
            toastr["success"]("Question order changed!");
            setTimeout(function(){ window.location.href='/admin/admin.jsp'; }, 1000);
        }]
    });
}