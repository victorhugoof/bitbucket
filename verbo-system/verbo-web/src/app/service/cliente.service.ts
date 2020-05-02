import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { ClienteModel } from '../models/cliente-model';
import { Observable } from 'rxjs';
import { EstadoModel } from '../models/estado-model';
import { CidadeModel } from '../models/cidade-model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends BaseService<ClienteModel> {
  url = 'cliente';

  consultarEstados(): Observable<EstadoModel[]> {
    return this.get(this.url + '/estados');
  }

  consultarCidades(id: number): Observable<CidadeModel[]> {
    return this.get(this.url + '/cidades/' + id);
  }
}
