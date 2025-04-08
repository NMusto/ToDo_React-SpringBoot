import React from 'react'
import { FaPenSquare } from "react-icons/fa";
import { FaTrash } from "react-icons/fa6";
import { FaSquare } from "react-icons/fa";
import { FaSquareCheck } from "react-icons/fa6";

const Todo = ({ task, toggleFinished, handleDeleteTodo, handleUpdateIsEditing }) => {
  return (
    <div className='Todo'>
        <p onClick={() => toggleFinished(task.id)} className={`${task.finished ? 'completed' : 'incompleted'}`}>{task.title}</p>
        <div className='icons-box'>
            {  task.finished ? <FaSquareCheck className='check-icon' onClick={() => toggleFinished(task.id)} />
             : <FaSquare className='check-icon' onClick={() => toggleFinished(task.id)} /> }
            <FaTrash className='delete-icon' onClick={() => handleDeleteTodo(task.id)}/>
            <FaPenSquare className='edit-icon' onClick={() => handleUpdateIsEditing(task.id,)}/>
        </div>
    </div>
  )
}

export default Todo