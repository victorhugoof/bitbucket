import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef, SelectItem } from 'primeng/api';
import { ClienteModel } from 'src/app/models/cliente-model';
import { ClienteService } from 'src/app/service/cliente.service';
import { MensagensService } from 'src/app/service/mensagens.service';

@Component({
  selector: 'app-cliente-consulta',
  templateUrl: './cliente-consulta.component.html',
  styleUrls: ['./cliente-consulta.component.css']
})
export class ClienteConsultaComponent implements OnInit {
  filtros: SelectItem[];
  situacoes: SelectItem[];
  selectFiltro: SelectItem;
  selectSituacao: SelectItem;
  selectOrdem: SelectItem;
  formConsulta: FormGroup;
  nome: AbstractControl;
  cols: any[];
  lista: ClienteModel[];

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private fb: FormBuilder, private clienteService: ClienteService, private mensagensService: MensagensService) {
    this.formConsulta = this.fb.group({
      'nomeFiltro': ['', Validators.required]
    });
    this.nome = this.formConsulta.controls['nomFiltro'];
    
    this.filtros = [
      { label: 'Nome', value: 1 },
      { label: 'Código', value: 2 },
      { label: 'CPF', value: 3 },
      { label: 'Cidade', value: 4 },
      { label: 'Estado', value: 5 }
    ];

    this.situacoes = [
      { label: 'Ativos', value: 1 },
      { label: 'Inativos', value: 2 },
      { label: 'Todos', value: 3 },
    ];

    this.cols = [
      { field: 'id', header: 'Código', width: '8%' },
      { field: 'nomPessoa', header: 'Nome' },
      { field: 'numCpf', header: 'CPF', width: '15%' },
      { field: 'flgSexo', header: 'Sexo', width: '8%' },
      { field: 'datNasc', header: 'Data de Nasc.', width: '8%' },
      { field: 'numTelefone', header: 'Telefone', width: '10%' },
      { field: 'cidade.nomCidade', header: 'Cidade', width: '15%' }
    ];
  }

  ngOnInit(): void {
    this.clienteService.consultarTodos().subscribe(res => {
      this.lista = res;
    }, error => {
      this.ref.close();
      this.mensagensService.mensagemErro(error);
    });

  }

  seleciona(obj: any) {
    this.ref.close(obj);
  }

  public onSubmit() {
    
    if (this.formConsulta.valid) {

      this.clienteService.consultarTodos().subscribe(res => {
        this.lista = res;
      }, error => {
        this.ref.close();
        this.mensagensService.mensagemErro(error);
      });
    }
  }
}
