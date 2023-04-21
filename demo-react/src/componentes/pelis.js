import React, { Component } from "react";
import {
  ValidationMessage,
  ErrorMessage,
  Esperando,
  PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
import { titleCase } from "../biblioteca/formateadores";
export class PelisMnt extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modo: "list",
      listado: null,
      elemento: null,
      error: null,
      loading: true,
      pagina: 0,
      paginas: 0,
    };
    this.idOriginal = null;
    this.url =
      (process.env.REACT_APP_API_URL || "http://localhost:8010") +
      "/api/pelis/v1";
  }

  setError(msg) {
    this.setState({ error: msg, loading: false });
  }

  list(num) {
    let pagina = this.state.pagina;
    if (num || num === 0) pagina = num;
    this.setState({ loading: true });
    fetch(`${this.url}?sort=title&page=${pagina}&size=10`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "list",
                  listado: data.content,
                  loading: false,
                  pagina: data.number,
                  paginas: data.totalPages,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }

  add() {
    this.setState({
      modo: "add",
      elemento: {
        "filmId": 0,
        "length": '',
        "description": "",
        "rating": "G",
        "releaseYear": "",
        "rentalDuration": "",
        "rentalRate": "",
        "replacementCost": "",
        "title": "",
        "languageId": "",
        "languageVOId": ""
    },
    });
  }
  edit(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "edit",
                  elemento: data,
                  loading: false,
                });
                this.idOriginal = key;
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }

  view(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "view",
                  elemento: data,
                  loading: false,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }
  delete(key) {
    if (!window.confirm("¿Seguro?")) return;
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`, { method: "DELETE" })
      .then((response) => {
        if (response.ok) this.list();
        else
          response.json().then((error) =>
            this.setError(`${error.status}:
                        ${error.error}`)
          );
        this.setState({ loading: false });
      })
      .catch((error) => this.setError(error));
  }

  componentDidMount() {
    this.list(0);
  }

  cancel() {
    this.list();
  }
  send(elemento) {
    this.setState({ loading: true });
    // eslint-disable-next-line default-case
    switch (this.state.modo) {
      case "add":
        fetch(`${this.url}`, {
          method: "POST",
          body: JSON.stringify(elemento),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (response.ok) this.cancel();
            else
              response.json().then((error) =>
                this.setError(`${error.status}:
                ${error.detail}`)
              );
            this.setState({ loading: false });
          })
          .catch((error) => this.setError(error));
        break;
      case "edit":
        fetch(`${this.url}/${this.idOriginal}`, {
          method: "PUT",
          body: JSON.stringify(elemento),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (response.ok) this.cancel();
            else
              response.json().then((error) =>
                this.setError(`${error.status}:
    ${error.detail}`)
              );
            this.setState({ loading: false });
          })
          .catch((error) => this.setError(error));
        break;
    }
  }

  render() {
    if (this.state.loading) return <Esperando />;
    let result = [
      <ErrorMessage
        key="error"
        msg={this.state.error}
        onClear={() => this.setState({ error: null })}
      />,
    ];
    switch (this.state.modo) {
      case "add":
      case "edit":
        result.push(
          <PelisForm
            key="main"
            isAdd={this.state.modo === "add"}
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
            onSend={(e) => this.send(e)}
          />
        );
        break;
      case "view":
        result.push(
          <PelisView
            key="main"
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
          />
        );
        break;
      default:
        if (this.state.listado)
          result.push(
            <PelisList
              key="main"
              listado={this.state.listado}
              pagina={this.state.pagina}
              paginas={this.state.paginas}
              onAdd={(e) => this.add()}
              onView={(key) => this.view(key)}
              onEdit={(key) => this.edit(key)}
              onDelete={(key) => this.delete(key)}
              onChangePage={(num) => this.list(num)}
            />
          );
        break;
    }
    return result;
  }
}

function PelisList(props) {
  return (
    <>
      <table className="table table-hover table-striped">
        <thead className="table-info">
          <tr>
            <th>Lista de Películas</th>
            <th className="text-end">
              <input
                type="button"
                className="btn btn-primary"
                value="Añadir"
                onClick={(e) => props.onAdd()}
              />
            </th>
          </tr>
        </thead>
        <tbody className="table-group-divider">
          {props.listado.map((item) => (
            <tr key={item.filmId}>
              <td>{titleCase(item.title)}</td>
              <td className="text-end">
                <div className="btn-group text-end" role="group">
                  <input
                    type="button"
                    className="btn btn-primary"
                    value="Ver"
                    onClick={(e) => props.onView(item.filmId)}
                  />
                  <input
                    type="button"
                    className="btn btn-primary"
                    value="Editar"
                    onClick={(e) => props.onEdit(item.filmId)}
                  />
                  <input
                    type="button"
                    className="btn btn-danger"
                    value="Borrar"
                    onClick={(e) => props.onDelete(item.filmId)}
                  />
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Paginacion
        actual={props.pagina}
        total={props.paginas}
        onChange={(num) => props.onChangePage(num)}
      />
    </>
  );
}

function PelisView({ elemento, onCancel }) {
  return (
    <div>
      <p>
        <b>Código:</b> {elemento.filmId}
        <br />
        <b>Título:</b> {elemento.title}
        <br />
        <b>Duración:</b> {elemento.length}
        <br />
        <b>Año de lanzamiento:</b> {elemento.releaseYear}
        <br />
        <b>Descripción:</b> {elemento.description}
        <br />
        <b>Lenguaje:</b> {elemento.language}
        <br />
        <b>Lenguaje versión original:</b> {elemento.languageVO}
        <br />
        <b>Calificación por edades:</b> {elemento.rating}
        <br />
        <b>Precio de alquiler:</b> {elemento.rentalRate}
        <br />
        <b>Duración del alquiler:</b> {elemento.rentalDuration}
        <br />
        <b>Precio de reemplazo:</b> {elemento.replacementCost}
        <br />
        <b>Actores:</b> {elemento.actors.map(item => " -" + item)}
      </p>
      <p>
        <button
          className="btn btn-primary"
          type="button"
          onClick={(e) => onCancel()}
        >
          Volver
        </button>
      </p>
    </div>
  );
}

class PelisForm extends Component {
  constructor(props) {
    super(props);
    this.state = { elemento: props.elemento, msgErr: [], invalid: false };
    this.handleChange = this.handleChange.bind(this);
    this.onSend = () => {
      if (this.props.onSend) this.props.onSend(this.state.elemento);
    };
    this.onCancel = () => {
      if (this.props.onCancel) this.props.onCancel();
    };
  }
  handleChange(event) {
    const cmp = event.target.name;
    const valor = event.target.value;
    this.setState((prev) => {
      prev.elemento[cmp] = valor;
      return { elemento: prev.elemento };
    });
    this.validar();
  }
  validarCntr(cntr) {
    if (cntr.name) {
      // eslint-disable-next-line default-case
      switch (cntr.name) {
        // case "rentalRate":
        //   cntr.setCustomValidity(
        //     cntr.value.length > 10
        //       ? "Debe ser más pequeño"
        //       : ""
        //   );
        //   break;
      }
    }
  }
  validar() {
    if (this.form) {
      const errors = {};
      let invalid = false;
      for (var cntr of this.form.elements) {
        if (cntr.name) {
          this.validarCntr(cntr);
          errors[cntr.name] = cntr.validationMessage;
          invalid = invalid || !cntr.validity.valid;
        }
      }
      this.setState({ msgErr: errors, invalid: invalid });
    }
  }
  componentDidMount() {
    this.validar();
  }
  render() {
    return (
      <form
        ref={(tag) => {
          this.form = tag;
        }}
      >
        <div className="form-group">
          <label htmlFor="filmId">Código</label>
          <input
            type="number"
            className={"form-control" + (this.props.isAdd ? "" : "-plaintext")}
            id="filmId"
            name="filmId"
            value={this.state.elemento.filmId}
            onChange={this.handleChange}
            required
            readOnly={!this.props.isAdd}
          />
          <ValidationMessage msg={this.state.msgErr.filmId} />
        </div>
        <div className="form-group">
          <label htmlFor="title">Título</label>
          <input
            type="text"
            className="form-control"
            id="title"
            name="title"
            value={this.state.elemento.title}
            onChange={this.handleChange}
            required
            minLength="2"
            maxLength="128"
          />
          <ValidationMessage msg={this.state.msgErr.title} />
        </div>
        <div className="form-group">
          <label htmlFor="length">Duración</label>
          <input
            type="number"
            className="form-control"
            id="length"
            name="length"
            value={this.state.elemento.length}
            onChange={this.handleChange}
            required
            min="1"
            max="99999"
          />
          <ValidationMessage msg={this.state.msgErr.length} />
        </div>
        <div className="form-group">
          <label htmlFor="releaseYear">Año de lanzamiento</label>
          <input
            type="number"
            className="form-control"
            id="releaseYear"
            name="releaseYear"
            value={this.state.elemento.releaseYear}
            onChange={this.handleChange}
            min="1901"
          />
          <ValidationMessage msg={this.state.msgErr.releaseYear} />
        </div>
        <div className="form-group">
          <label htmlFor="description">Descripción</label>
          <input
            type="text"
            className="form-control"
            id="description"
            name="description"
            value={this.state.elemento.description}
            onChange={this.handleChange}
          />
          <ValidationMessage msg={this.state.msgErr.description} />
        </div>
        <div className="form-group">
          <label htmlFor="languageId">Lenguaje</label>
          <input
            type="text"
            className="form-control"
            id="languageId"
            name="languageId"
            value={this.state.elemento.languageId}
            onChange={this.handleChange}
            required
            minLength="1"
          />
          <ValidationMessage msg={this.state.msgErr.languageId} />
        </div>
        <div className="form-group">
          <label htmlFor="languageVOId">Lenguaje versión original</label>
          <input
            type="text"
            className="form-control"
            id="languageVOId"
            name="languageVOId"
            value={this.state.elemento.languageVOId}
            onChange={this.handleChange}
            minLength="1"
          />
          <ValidationMessage msg={this.state.msgErr.languageVOId} />
        </div>
        <div className="form-group">
          <label htmlFor="rating">Calificación por edades</label>
          <select 
            className="form-control"
            id="rating"
            name="rating"
            value={this.state.elemento.rating}
            onChange={this.handleChange}>
              {['G', 'PG', 'PG-13', 'R', 'NC-17']
                .map(item => <option key={item} value={item}>{item}</option>)}
          </select>
          <ValidationMessage msg={this.state.msgErr.rating} />
        </div>
        <div className="form-group">
          <label htmlFor="rentalRate">Precio de alquiler</label>
          <input
            type="number"
            step="0.01"
            className="form-control"
            id="rentalRate"
            name="rentalRate"
            value={this.state.elemento.rentalRate}
            onChange={this.handleChange}
            required
            min="0"
          />
          <ValidationMessage msg={this.state.msgErr.rentalRate} />
        </div>
        <div className="form-group">
          <label htmlFor="rentalDuration">Duración del alquiler</label>
          <input
            type="number"
            className="form-control"
            id="rentalDuration"
            name="rentalDuration"
            value={this.state.elemento.rentalDuration}
            onChange={this.handleChange}
            required
            min="1"
          />
          <ValidationMessage msg={this.state.msgErr.rentalDuration} />
        </div>
        <div className="form-group">
          <label htmlFor="replacementCost">Precio de reemplazo</label>
          <input
            type="number"
            step="0.01"
            className="form-control"
            id="replacementCost"
            name="replacementCost"
            value={this.state.elemento.replacementCost}
            onChange={this.handleChange}
            required
            min="0"
          />
          <ValidationMessage msg={this.state.msgErr.replacementCost} />
        </div>
        <div className="form-group">
          <button
            className="btn btn-primary"
            type="button"
            disabled={this.state.invalid}
            onClick={this.onSend}
          >
            Enviar
          </button>
          <button
            className="btn btn-primary"
            type="button"
            onClick={this.onCancel}
          >
            Volver
          </button>
        </div>
      </form>
    );
  }
}
