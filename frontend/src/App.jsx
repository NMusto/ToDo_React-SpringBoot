import { useState } from 'react'
import TodoWrapper from './components/TodoWrapper';
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className='App'> 
      <TodoWrapper/>
    </div>
  )
}

export default App
