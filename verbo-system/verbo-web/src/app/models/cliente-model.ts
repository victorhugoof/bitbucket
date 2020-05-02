import { BaseModel } from './base-model';
import { CidadeModel } from './cidade-model';

export class ClienteModel extends BaseModel {
    nomPessoa: string;
	numCpf: string;
	flgSexo: string;
	datNasc: Date;
	numTelefone: string;
	cidade: CidadeModel;
	vlrLimiteCredito: number;
	vlrCreditoUtilizado: number;
	nomObservacoes: string;
}
