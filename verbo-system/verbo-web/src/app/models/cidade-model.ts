import { BaseModel } from './base-model';
import { EstadoModel } from './estado-model';

export class CidadeModel extends BaseModel {
	estado: EstadoModel;
	nomCidade: string;
}
