import React, { Component } from "react";
import "./calculadora.css";

export class Calculadora extends Component {
  constructor(props) {
    super(props);
    this.state = {
      result: document.querySelector("#rslt"),
      op: document.querySelector("#op"),
      btnRe: document.querySelector("#re"),
      btnBorrar: document.querySelector("#borrar"),
      btnDec: document.querySelector("#dec"),
      btnNums: document.querySelectorAll("#num"),
      btnCalcs: document.querySelectorAll("#calc"),

      operador: "+",
      n1: "0",
      n: "0",
      nfin: true,
      solucion: 0,
    };
  }

  operacion(value) {
    let { operador, solucion, n, op, result, nfin } = this.state;
    switch (operador) {
      case "+":
        solucion += +n;
        break;
      case "-":
        solucion -= +n;
        break;
      case "*":
        solucion *= +n;
        break;
      case "/":
        solucion /= +n;
        break;
    }

    op.textContent = value == "=" ? "" : `${solucion} ${value}`;
    n = parseFloat(solucion.toPrecision(15)).toString();
    result.textContent = n;
    nfin = false;
    operador = value;
  }

  reiniciar() {
    this.setState({
        operador: "+",
        n: "0",
        nfin: true,
        solucion: 0,
        result: this.state.n,
        op: ''
    });
  }

  borrar() {
    let { n, result, nfin } = this.state;
    if (!nfin || n.length == 1 || (n.length == 2 && n.startsWith("-"))) {
      n = "0";
      nfin = true;
    } else n = n.slice(0, -1);
    result.textContent = n;
  }

  decimal() {
    let { n, result, nfin } = this.state;
    if (!nfin) {
      n = "0.";
      nfin = true;
    } else if (n.indexOf(".") === -1) n += ".";
    result.textContent = n;
  }

  render() {
    return (
      <>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Calculadora</title>
        <link rel="stylesheet" href="./calculadora.css" />
        <div className="Calculadora">
          <form name="form"></form>
          <table id="teclado">
            <tbody>
              <tr>
                <td colSpan={2}>
                  <output id="op">{this.state.op}</output>
                </td>
              </tr>
              <tr>
                <td colSpan={4}>
                  <output id="rslt">{this.state.n}</output>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="re"
                    defaultValue="C"
                    onClick={this.reiniciar}
                  />
                </td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="borrar"
                    defaultValue="<="
                  />
                </td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="calc"
                    defaultValue="="
                  />
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input type="button" id="num" defaultValue={7} />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={8} />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={9} />
                </td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="calc"
                    defaultValue="/"
                  />
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input type="button" id="num" defaultValue={4} />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={5} />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={6} />
                </td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="calc"
                    defaultValue="*"
                  />
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input type="button" id="num" defaultValue={3} />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={2} />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={1} />
                </td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="calc"
                    defaultValue="+"
                  />
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="dec"
                    defaultValue="."
                  />
                </td>
                <td>
                  <input type="button" id="num" defaultValue={0} />
                </td>
                <td>
                  <input
                    className="operaciones"
                    type="button"
                    id="calc"
                    defaultValue="-"
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </>
    );
  }
}

// function Calc(){

//     btnCalcs.forEach(btn => {
//         btn.addEventListener('click', ev => {
//             operacion(ev.target.value)
//     })})

//     btnNums.forEach(btn => {
//         btn.addEventListener('click', ev => {
//             if(nfin && n !=0 )
//                 n += ev.target.value
//             else
//             {
//                 n = ev.target.value
//                 nfin = true
//             }
//             result.textContent = n
//     })})

//     btnRe.addEventListener('click', reiniciar)
//     btnBorrar.addEventListener('click', borrar)
//     btnDec.addEventListener('click', decimal)
// }
