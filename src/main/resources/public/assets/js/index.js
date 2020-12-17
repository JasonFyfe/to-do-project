readAll();
// CREATE
// checks if enter was press on the text input
$("#createNoteText").keypress(function (event) {
    if (event.which === 13) {
        // Store and clear data from text input
        var noteTitle = $(this).val();
        $(this).val("");

        fetch("http://localhost:8081/notes", {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify(noteTitle)
        })
            .then(response => response.json())
            .then(data => appendNote(data))
            .catch(err => console.error(err));
    }
});

// READALL
function readAll() {
    $("#content").empty();
    // fetch get all
    fetch("http://localhost:8081/notes")
        .then(response => response.json())
        .then(data => parseNotes(data))
        .catch(err => console.error(err));
}

// Helper function
function parseNotes(data) {
    data.forEach(note => {
        appendNote(note);
    });
}

// Add note to main page
function appendNote(data) {
    // create a new card and add it to the container    
    let head = `
    <div class="card bg-info" id="note-${data.id}">
        <div class="card-body">
            <h5 class="card-title">${data.title}</h5>
        </div>
        <ul class="list-group list-group-flush">`;
    let foot = `
        </ul>
        <div class="card-footer text-right">
        <button type="button" class="btn btn-light"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
        </div>
    </div>`;
    let list = ``;

    if (data.todos !== null) {
        data.todos.forEach(todo => {
            list += `<li class="list-group-item">${todo.body}</li>`
        });
    }
    $("#content").append(head + list + foot);
}

// Update main page when modal is closed
$("#Modal").on("hidden.bs.modal", readAll);