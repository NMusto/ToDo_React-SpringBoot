import React from 'react'
import { useState } from 'react'


const TodoForm = ({handleAddTodo}) => {
    const [value, setValue] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        handleAddTodo(value);
        setValue('');
    }


  return (
    <form className='TodoForm' onSubmit={handleSubmit}>
        <input 
          className='todo-input' 
          type='text' 
          placeholder='New task..' 
          value={value}
          onChange={(e) => setValue(e.target.value)} 
        />
        <button type='submit' className='todo-btn'>Add task</button>
    </form>
  )
}

export default TodoForm