import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService } from 'primeng/api';
import { ClienteConsultaComponent } from 'src/app/consultas/cliente-consulta/cliente-consulta.component';
import { CidadeModel } from 'src/app/models/cidade-model';
import { ClienteModel } from 'src/app/models/cliente-model';
import { EstadoModel } from 'src/app/models/estado-model';
import { ClienteService } from 'src/app/service/cliente.service';
import { MensagensService } from 'src/app/service/mensagens.service';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {
  formulario: FormGroup;
  id: AbstractControl;
  nomPessoa: AbstractControl;
  numCpf: AbstractControl;
  datNasc: AbstractControl;
  numTelefone: AbstractControl;
  flgSexo: AbstractControl;
  cidade; AbstractControl;
  estado: AbstractControl;
  vlrLimiteCredito: AbstractControl;
  nomObservacoes: AbstractControl;

  estados: EstadoModel[];
  cidades: CidadeModel[];

  isEditando: boolean;


  constructor(public dialogService: DialogService, private formBuilder: FormBuilder, private clienteService: ClienteService, private mensagensService: MensagensService) {
    this.formulario = this.formBuilder.group({
      id: [null],
      nomPessoa: [null, Validators.required],
      numCpf: [null, Validators.required],
      datNasc: [null],
      numTelefone: [null],
      flgSexo: ['M', Validators.required],
      cidade: [null],
      estado: [null],
      vlrLimiteCredito: [null, Validators.required],
      nomObservacoes: [null]
    });

    this.id = this.formulario.controls['id'];
    this.nomPessoa = this.formulario.controls['nomPessoa'];
    this.numCpf = this.formulario.controls['numCpf'];
    this.datNasc = this.formulario.controls['datNasc'];
    this.numTelefone = this.formulario.controls['numTelefone'];
    this.flgSexo = this.formulario.controls['flgSexo'];
    this.cidade = this.formulario.controls['cidade'];
    this.estado = this.formulario.controls['estado'];
    this.vlrLimiteCredito = this.formulario.controls['vlrLimiteCredito'];
    this.nomObservacoes = this.formulario.controls['nomObservacoes'];
  }

  ngOnInit() {
    this.clienteService.consultarEstados().subscribe(resposta => {
      this.estados = resposta;
    }, error => {
      this.mensagensService.mensagemErro(error);
    });
  }

  showConsulta() {
    const codigo = this.id.value;
    this.limpar();

    if (codigo) {
      this.clienteService.consultarCodigo(codigo).subscribe(obj => {
        if (obj.datNasc) {
          obj.datNasc = this.formatarData(obj.datNasc);
        }

        this.formulario.patchValue(obj);

        if (obj.cidade) {
          this.estado.setValue(obj.cidade.estado);
          this.buscaCidades();
          this.cidade.setValue(obj.cidade);
        }

        this.isEditando = true;
      }, error => {
        this.mensagensService.mensagemErro(error);
      });

      return;
    }

    const modal = this.dialogService.open(ClienteConsultaComponent, {
      header: 'Consulta de Clientes',
      width: '70%',
      contentStyle: { "min-height": "70%" }
    });

    modal.onClose.subscribe((obj: ClienteModel) => {
      if (obj) {
        if (obj.datNasc) {
          obj.datNasc = this.formatarData(obj.datNasc);
        }

        this.formulario.patchValue(obj);

        if (obj.cidade) {
          this.estado.setValue(obj.cidade.estado);
          this.buscaCidades();
          this.cidade.setValue(obj.cidade);
        }

        this.isEditando = true;
      }

    }, error => {
      this.mensagensService.mensagemErro(error);
    });
  }

  salvar() {
    var model = new ClienteModel();
    model.id = this.id.value;
    model.nomPessoa = this.nomPessoa.value;
    model.numCpf = this.numCpf.value.replace('.', '').replace('-', '').replace('.', '');
    model.flgSexo = this.flgSexo.value;

    if (this.datNasc.value) {
      model.datNasc = this.datNasc.value.toJSON();
    }
    model.numTelefone = this.numTelefone.value;
    model.cidade = this.cidade.value;
    model.vlrLimiteCredito = this.vlrLimiteCredito.value;
    model.vlrCreditoUtilizado = 0;
    model.nomObservacoes = this.nomObservacoes.value;

    if (this.isEditando) {
      this.clienteService.atualizar(model).subscribe(resposta => {

        if (resposta.datNasc) {
          resposta.datNasc = this.formatarData(resposta.datNasc);
        }

        this.formulario.patchValue(resposta);
        this.mensagensService.informacao('Salvo!', 'Cliente atualizado com sucesso');
      }, error => {
        this.mensagensService.mensagemErro(error);
      });
    } else {
      this.clienteService.salvar(model).subscribe(resposta => {

        if (resposta.datNasc) {
          resposta.datNasc = this.formatarData(resposta.datNasc);
        }

        this.formulario.patchValue(resposta);
        this.mensagensService.sucesso('Salvo!', 'Cliente cadastrado com sucesso');
      }, error => {
        this.mensagensService.mensagemErro(error);
      });
    }
  }

  limpar() {
    this.formulario.reset();
    this.flgSexo.setValue('M');
    this.mensagensService.limpar();
    this.isEditando = false;
  }

  buscaCidades() {
    this.clienteService.consultarCidades(this.estado.value.id).subscribe(resposta => {
      this.cidades = resposta;
    }, error => {
      this.mensagensService.mensagemErro(error);
    });
  }

  formatarData(value) {
    return new Date(value);
  }
}
