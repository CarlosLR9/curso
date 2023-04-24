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
        "length": 0,
        "description": "",
        "rating": "G",
        "releaseYear": (new Date()).getFullYear(),
        "rentalDuration": 3,
        "rentalRate": 4.99,
        "replacementCost": 19.99,
        "title": "",
        "languageId": 1,
        "languageVOId": '',
        "actors": [],
        "categories": []        
    },
    });
  }
  edit(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}?mode=edit`)
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
              <td>
                <img src={`https://picsum.photos/id/${item.filmId}/500/400`} alt={`Foto de ${item.title}`} width="15%" height="15%" />
                {" " + titleCase(item.title)}
              </td>
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
      <img src={`https://picsum.photos/id/${elemento.filmId}/500/300`} className="img-fluid" alt={`Foto de ${elemento.title}`} />
      <p>
        <ul className="list-inline">
          {elemento.categories.map(item => <li ley={item} className="list-inline-item badge bg-primary">{item}</li>)}
        </ul>
        <b>Código:</b> {elemento.filmId}
        <br />
        <b>Título:</b> {elemento.title}
        <br />
        <b>Duración:</b> {elemento.length} minutos
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
        <b>Precio de alquiler:</b> {elemento.rentalRate}€
        <br />
        <b>Duración del alquiler:</b> {elemento.rentalDuration} días
        <br />
        <b>Precio de reemplazo:</b> {elemento.replacementCost}€
        <h1>Reparto</h1>
        <ul className="list">
          {elemento.actors.map((item, index) => <li ley={index}>{titleCase(item)}</li>)}
        </ul>
        
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
    this.state = { 
      elemento: props.elemento, 
      msgErr: [], 
      invalid: false,
      idActor: null,
      idCategoria: null,
      actores: [],
      categorias: [],
      lenguajes: []
    };
    this.form = React.createRef();
    this.url = (process.env.REACT_APP_API_URL || 'http://localhost:8010');
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

  setError(msg) {
    this.setState({ error: msg, loading: false });
  }

  componentDidMount() {
    this.validar();
    this.setActores();
    this.setCategorias();
    this.setLenguajes();
  }

  setLenguajes() {
    fetch(`${this.url}/api/lenguajes/v1`)
          .then(response => {
              response.json().then(response.ok ? data => {
                  this.setState({lenguajes: data})
              } : error => this.setError(`${error.status}: ${error.error}`))
          })
          .catch(error => this.setError(error.message))
  }

  setCategorias() {
    fetch(`${this.url}/api/categorias/v1`)
        .then(response => {
            response.json().then(response.ok ? data => {
                if (data.length > 0) this.setState({idCategoria: data[0].id})
                this.setState({categorias: data})
            } : error => this.setError(`${error.status}: ${error.error}`))
        })
        .catch(error => this.setError(error.message))
  }

  setActores() {
    fetch(`${this.url}/api/actores/v1?sort=firstName`)
        .then(response => {
            response.json().then(response.ok ? data => {
                if (data.length > 0) this.setState({idActor: data[0].actorId})
                this.setState({actores: data})
            } : error => this.setError(`${error.status}: ${error.error}`))
        })
        .catch(error => this.setError(error.message))
  }

  render() {
    return (
      <form
        ref={(tag) => {
          this.form = tag;
        }}
      >
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
          <div className="input-group">
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
            <span className="input-group-text">minutos</span>
          </div>
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
            max={(new Date()).getFullYear()}
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
                <select className="form-select"
                    id="languageId" name="languageId" value={this.state.elemento.languageId}
                    onChange={this.handleChange} >
                    {this.state.lenguajes.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
                </select>
                <ValidationMessage msg={this.state.msgErr.languageId} />
            </div>
            <div className="form-group">
                <label htmlFor="languageVOId">Lenguaje Versión original</label>
                <select className="form-select"
                    id="languageVOId" name="languageVOId" value={this.state.elemento.languageVOId}
                    onChange={this.handleChange} >
                    <option value=''></option>
                    {this.state.lenguajes.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
                </select>
                <ValidationMessage msg={this.state.msgErr.languageVOId} />
            </div>
        <div className="form-group">
          <label htmlFor="rating">Calificación por edades</label>
          <select 
            className="form-select"
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
          <div className="input-group">
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
            <span className="input-group-text">€</span>
          </div>
          <ValidationMessage msg={this.state.msgErr.rentalRate} />
        </div>
        <div className="form-group">
          <label htmlFor="rentalDuration">Duración del alquiler</label>
          <div className="input-group">
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
            <span className="input-group-text">días</span>
          </div>
          <ValidationMessage msg={this.state.msgErr.rentalDuration} />
        </div>
        <div className="form-group">
          <label htmlFor="replacementCost">Precio de reemplazo</label>
          <div className="input-group">
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
            <span className="input-group-text">€</span>
          </div>
          <ValidationMessage msg={this.state.msgErr.replacementCost} />
        </div>
        <div className="form-group">
                <label htmlFor="categories">Categorías</label>
                <div className="input-group">
                    <select className="form-select" id="categories" name="categories" onChange={ev => this.setState({idCategoria: ev.target.value})}>
                        {this.state.categorias.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
                    </select>
                    <button className="btn btn-outline-secondary" type="button" onClick={() => {
                        if (this.state.elemento.categories.includes(this.state.idCategoria)) return
                        this.state.elemento.categories.push(this.state.idCategoria)
                        this.setState({elemento: { ...this.state.elemento }})
                    }} ><i>+</i></button>
                </div>
                <ValidationMessage msg={this.state.msgErr.categories} />
                <ul>
                    {this.state.elemento.categories.map((item, index) =>
                        <li key={index}>{this.state.categorias.find(ele => ele.id == item)?.nombre} <button className="btn btn-link" type="button"
                            title='Quita categoría' onClick={() => {
                              this.state.elemento.categories.splice(index, 1)
                              this.setState({elemento: { ...this.state.elemento }})
                            }}><i>Borrar</i></button></li>
                    )}
                </ul>
            </div>
            <div className="form-group">
                <label htmlFor="actors">Reparto</label>
                <div className="input-group">
                    <select className="form-select" id="actors" name="actors" onChange={ev => this.setState({idActor: ev.target.value})}>
                        {this.state.actores.map(item => <option key={item.actorId} value={item.actorId}>{titleCase(item.nombre)}</option>)}
                    </select>
                    <button className="btn btn-outline-secondary" type="button" onClick={() => {
                        if (this.state.elemento.actors.includes(this.state.idActor)) return
                        this.state.elemento.actors.push(this.state.idActor)
                        this.setState({elemento: { ...this.state.elemento }})
                    }} ><i>+</i></button>
                </div>
                <ValidationMessage msg={this.state.msgErr.actors} />
                <ul>
                    {this.state.elemento.actors.map((item, index) =>
                        <li key={index}>{titleCase(this.state.actores.find(ele => ele.actorId == item)?.nombre)} <button className="btn btn-link" type="button"
                            title='Quita categoría' onClick={() => {
                              this.state.elemento.actors.splice(index, 1)
                              this.setState({elemento: { ...this.state.elemento }})
                            }}><i>Borrar</i></button></li>
                    )}
                </ul>
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
