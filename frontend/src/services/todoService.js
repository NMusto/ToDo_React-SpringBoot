
const TODO_API_URL = "http://localhost:8080/api/task";


export const createTodo = async (todo) => {
    const response = await fetch(TODO_API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
    });
    return response.json();
};

export const findAllTodos = async () => {
    const response = await fetch(TODO_API_URL);
    return response.json();
};

export const updateTodo = async (id, title) => {
    await fetch(`${TODO_API_URL}/update_task/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(title)
    });
};


export const updateFinished = async (id, update) => {
        await fetch(`${TODO_API_URL}/update_task_finished/${id}?key=${update}`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        //body: JSON.stringify(update),
    });
    //return response.json();
};


export const deleteTodo = async (id) => {
    await fetch(`${TODO_API_URL}/delete/${id}`, { method: "DELETE" });
};