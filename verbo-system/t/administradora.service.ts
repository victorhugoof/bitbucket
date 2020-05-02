import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { BaseService } from './base.service';
import { AdministradoraModel } from '../model/administradora.model';
import { Page } from '../model/page-model';
import { SelectListModel } from '../model/select-list-model';

@Injectable({
  providedIn: 'root'
})
export class AdministradoraService extends BaseService {

  private url = 'administradora';

  consultar(model: any): Observable<Page<AdministradoraModel>> {
    return super.post(this.url + '/consultar', model);
  }

  alterar(model: AdministradoraModel): Observable<AdministradoraModel> {
    return super.post(this.url, model);
  }
}
