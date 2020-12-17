// READONE
// Card click - readOne - Open Modal
$("#content").on("click", ".card-body, .list-group, .card-footer", function () {
    // get the id of the clicked note
    let val = $(this).closest(".card").attr('id');
    let id = val.substr(val.indexOf("-") + 1);
    // fetch get one
    fetch("http://localhost:8081/notes/" + id)
        .then(response => response.json())
        .then(data => updateModal(data))
        .catch(err => console.error(err));
    // open modal
    $('#Modal').modal('show');
});

// Update modal with desired note data
function updateModal(note) {
    $(".modal-content ul").empty();
    $("#modalTitle").val(`${note.title}`);
    $(".modal-header").attr("id", `${note.id}`);
    note.todos.forEach(todo => {
        $(".modal-content ul").append(`
        <div class="input-group">
            <input type="text" class="form-control todo-input" value="${todo.body}" id="todo-${todo.id}">
            <div class="input-group-append">
                <button class="btn btn-danger" id="todo-${todo.id}"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
            </div>
        </div>`);
    });

    $(".modal-header input").keypress(function (event) {
        let title = $(this).val();
        let id = $(".modal-header").attr("id");
        updateNote(id, title);
    });

    $(".list-group input").keypress(function (event) {
        let val = $(this).attr("id");
        let id = val.substr(val.indexOf("-") + 1);
        let body = $(this).val();

        updateTodo(id, body);
    });

    $(".input-group-append").on("click", ".btn", function (event) {
        // Fade out parent todo
        let val = $(this).attr('id');
        let id = val.substr(val.indexOf("-") + 1);
        let todo = $(this).closest(".input-group");
    
        todo.fadeOut(500, function () {
            todo.remove();
            deleteTodo(id);
        });
        // stops event bubbling
        event.stopPropagation();
    });
}

// Update specified todo
function updateTodo(id, body) {
    fetch("http://localhost:8081/todos/" + id, {
        headers: { "Content-Type": "application/json" },
        method: "PUT",
        body: JSON.stringify({ body: body })
    })
        .catch(err => console.error(err));
}

// Update specified Note
function updateNote(id, title) {
    fetch("http://localhost:8081/notes/" + id, {
        headers: { "Content-Type": "application/json" },
        method: "PUT",
        body: JSON.stringify({ title: title })
    })
        .catch(err => console.error(err));
}

// Delete todo
function deleteTodo(id) {
    fetch("http://localhost:8081/todos/" + id, {
        method: "DELETE",
    })
        .catch(err => console.error(err));
}