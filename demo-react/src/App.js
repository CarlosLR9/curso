import logo from './logo.svg';
import './App.css';

import React, { Component } from 'react'
import Contador from './componentes';

export default class App extends Component {
  render() {
    return (
      <>
        <main>
         <Contador init={10} delta={2} />
        </main>
        {/* <Home />
        <DemosJSX /> */}
      </>
    )
  }
}

class DemosJSX extends Component {
  render() { 
    let nombre = 'mundo'
    let estilo = 'App-link'
    let saluda = <h1>Hola {nombre.toUpperCase() +'!'}!!</h1>
    let dim = {width: 100, hegiht: 50}
    let errorStyle = {color: 'white', backgroundColor: 'red'}
    return (
      <>
        {saluda}
        <div style={errorStyle}>DemosJSX</div>
        <h2 className={estilo}>Hola {nombre}</h2>
        <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />
      </>
    )
  }
}

function Home() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Hola mundo</h1>
        <h2>url: {process.env.API_URL}</h2>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

// export default App;
