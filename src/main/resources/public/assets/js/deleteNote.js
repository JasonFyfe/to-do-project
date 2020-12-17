// DELETE
// click to delete Note
$("#content").on("click", ".btn", function (event) {
    // Fade out parent card
    let title = $(this).closest(".card").find(".card-title").text();
    let val = $(this).closest(".card").attr('id');
    let id = val.substr(val.indexOf("-") + 1);

    $(this).closest(".card").fadeOut(500, function () {
        // after fade, remove the card
        $(this).closest(".card").remove();
        // FETCH DELETE
        fetch("http://localhost:8081/notes/" + id, {
            method: "DELETE",
        })
            .then(response => showToast(title))
            .catch(err => console.error(err));
    });
    // stops event bubbling
    event.stopPropagation();
});

function showToast(title) {
    $(".toast-body").text(`${title}, has been deleted.`);
    $(".toast").toast({delay: 2000});
    $(".toast").toast('show');
}