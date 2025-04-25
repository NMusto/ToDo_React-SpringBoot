import axios from "axios";

const TODO_API_URL = "http://localhost:8080/api/task";


export const createTodo = async (todo) => {
    const response = await axios.post(TODO_API_URL, todo);
    return response.data;
};

export const findAllTodos = async () => {
    const response = await axios.get(TODO_API_URL);
    return response.data;
};

export const updateTodo = async (id, title) => {
    await axios.put(`${TODO_API_URL}/update_task/${id}`,title);
};

export const updateFinished = async (id, update) => {
    await axios.patch(`${TODO_API_URL}/update_task_finished/${id}`, null, {
        params : { key: update }
    });
};

export const deleteTodo = async (id) => {
    await axios.delete(`${TODO_API_URL}/delete/${id}`);
};

