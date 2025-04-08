import React from 'react'
import { useState } from 'react'


const EditTodoForm = ({task, handleUpdateTodo}) => {
    const [value, setValue] = useState(task.task || "");

    const handleSubmit = (e) => {
        e.preventDefault();
        handleUpdateTodo(task.id, value);
        setValue('');
    }


  return (
    <form className='TodoForm' onSubmit={handleSubmit}>
        <input 
          className='todo-input' 
          type='text' 
          placeholder='Update task..' 
          value={value}
          onChange={(e) => setValue(e.target.value)} />
        <button type='submit' className='todo-btn'>Up
          date task</button>
    </form>
  )
}

export default EditTodoForm