import { useState, useEffect } from 'react';
import { createTodo, deleteTodo, findAllTodos, updateFinished, updateTodo } from '../services/todoService';
import TodoForm from './TodoForm';
import Todo from './Todo';
import EditTodoForm from './EditTodoForm';
import Division from './Division';


const TodoWrapper = () => {
    const [todos, setTodos] = useState([]);


    useEffect(() => {
        findAllTodos().then(todosFromBackend => {   //agrego el atributo isEditing a los todos solo en memoria
            const todosWithEditing = todosFromBackend.map(todo => ({
                ...todo, isEditing: false
            }));
            setTodos(todosWithEditing);
        } );
        console.log(todos);
    }, []);
    

    const handleAddTodo = async (todo) => {
        if(todo.trim() !== '') {
            const newTodo = { title: todo, finished: false };
            const newTodoWithId = await createTodo(newTodo);
            setTodos(prevTodos => [...prevTodos, newTodoWithId]);
        }
    };

   const toggleFinished = async (id) => {
       
       const updateTodo = todos.find(todo => todo.id === id);
       if (!updateTodo) return;
       
       const finished = !updateTodo.finished;
       
       console.log("Actualizando:", { id, finished });
        await updateFinished(id, finished);

        setTodos(prevTodos => prevTodos.map(todo => todo.id === id ? {...todo, finished: finished} : todo));
   };
   
   const handleUpdateIsEditing = (id) => {
        setTodos(prevTodos => prevTodos.map(todo => todo.id === id ? {...todo, isEditing: !todo.isEditing} : todo));
   }


   const handleUpdateTodo = async (id, newTitle) => {

        const updaTaskDTO = {title: newTitle};
        await updateTodo(id, updaTaskDTO);

        setTodos(prevTodos => prevTodos.map(todo => todo.id === id ? {...todo, title: newTitle, isEditing: !todo.isEditing} : todo))
   }

   const handleDeleteTodo = async (id) => {
        await deleteTodo(id);

        setTodos(prevTodos => prevTodos.filter(todo => todo.id !== id));
   }


   

  

  return (
    <div className='TodoWrapper'>
        <h1>To Do List</h1>
        <TodoForm handleAddTodo={handleAddTodo} />
        {todos.filter(todo => !todo.finished).map(todo => (
            todo.isEditing ? (
                <EditTodoForm  key={todo.id} 
                    handleUpdateTodo={handleUpdateTodo} 
                    task={todo} />
            ) : (
                <Todo key={todo.id}
                    task={todo}
                    toggleFinished={toggleFinished}
                    handleDeleteTodo={handleDeleteTodo}
                    handleUpdateIsEditing={handleUpdateIsEditing} />
            )
        ))}

        <Division />
    
        {todos.filter(todo => todo.finished).map(todo => (
            todo.isEditing ? (
                <EditTodoForm key={todo.id}
                    handleUpdateTodo={handleUpdateTodo} 
                    task={todo} />
            ) : (
                <Todo key={todo.id}
                    task={todo}
                    toggleFinished={toggleFinished}
                    handleDeleteTodo={handleDeleteTodo}
                    handleUpdateIsEditing={handleUpdateIsEditing} />
            )
        ))}
         
    </div>
  )
}

export default TodoWrapper