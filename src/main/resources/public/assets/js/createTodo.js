$("#createTodo").keypress(function (event) {
    if (event.which == 13) {
        // Store and clear data from text input
        let todoBody = $(this).val();
        $(this).val("");
        let noteID = parseInt($(".modal-header").attr("id"));

        let todo = {
            body: todoBody,
            note: {
                id: noteID
            }
        }

        fetch("http://localhost:8081/todos", {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify(todo)
        })
            .then(response => response.json())
            .then(data => appendTodo(data))
            .catch(err => console.error(err));
    }
});

// Add the todo to the modal
function appendTodo(todo) {
    $(".modal-content ul").append(`
    <div class="input-group">
      <input type="text" class="form-control todo-input" value="${todo.body}" id="todo-${todo.id}">
      <div class="input-group-append">
        <button class="btn btn-danger"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
      </div>
    </div>`);
}