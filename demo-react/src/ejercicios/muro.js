import React, { Component } from "react";
import { ErrorMessage, Esperando } from "../comunes";
import "bootstrap/dist/css/bootstrap.css";

export default class Muro extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listado: null,
      loading: true,
      error: null,
      showImgs: true,
    };
  }
  render() {
    if (this.state.loading) return <Esperando />;
    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <h1>Muro</h1>
        <br />
        <main className='container-fluid'>
        <div className='row'>
            {this.state.listado && this.state.listado.map((item, index) => 
                <>{
                    <div className="card" style={{ width: "18rem" }}>
                    {item.visible && <img className="card-img-top" src={item.download_url} alt="Card image cap" />}
                    <div className="card-body">
                        <h5 className="card-title">{item.author}</h5>
                        <p className="card-text">
                        Text
                        </p>
                        <a className="btn btn-primary" onClick={this.activateShowImg(item.id)}>
                        {" "}
                        Mostrar imágenes{" "}
                        </a>
                    </div>
                </div>
                    }</>)}
            </div>
            </main>
      </>
    );
  }

  activateShowImg(index) {
    this.state.listado[index].visible = !this.state.listado[index].visible;
    this.setState({ listado: [...this.state.listado] })
  }

  setError(msg) {
    this.setState({ error: msg });
  }
  load(num) {
    this.setState({ loading: true });
    fetch("https://picsum.photos/v2/list")
      .then((resp) => {
        if (resp.ok) {
          resp.json().then((data) => {
            this.setState({ listado: data });
          });
        } else {
          this.setError(resp.status);
        }
      })
      .catch((err) => this.setError(JSON.stringify(err)))
      .finally(() => this.setState({ loading: false }));
  }
  componentDidMount() {
    this.load(1);
  }
}
