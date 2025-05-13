import axios from 'axios';

export class Servicio {
  getServicios() {
    return axios
      .get('./servicioJSON.json')
      .then(res => res.json())
      .then(d => d.data);
  }
}
