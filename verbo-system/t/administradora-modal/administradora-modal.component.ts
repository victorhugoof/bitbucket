import { Component, ViewChild, ElementRef } from '@angular/core';
import { AdministradoraModel } from 'src/app/model/administradora.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { CustomValidation } from 'src/app/theme/validation/validation.module';
import { AdministradoraService } from 'src/app/service/administradora.service';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-administradora-modal',
  templateUrl: './administradora-modal.component.html',
  styleUrls: ['./administradora-modal.component.scss']
})
export class AdministradoraModalComponent {

  @ViewChild('modal')
  private modal: ElementRef;

  private gridApi: any;
  private form: FormGroup;
  private submitObservable: Subscription;
  private model: AdministradoraModel;
  private administradoraModal: NgbModalRef;
  private nome: AbstractControl;
  private cnpj: AbstractControl;

  constructor(private fb: FormBuilder,
    private modalService: NgbModal,
    private administradoraService: AdministradoraService,
    private toastr: ToastrService) {

    this.form = this.fb.group({
      'nome': ['', Validators.compose([Validators.required])],
      'cnpj': ['', Validators.compose([Validators.required, CustomValidation.cnpj])],
      'codigoCielo': ['']
    });
    this.nome = this.form.controls['nome'];
    this.cnpj = this.form.controls['cnpj'];
  }

  public start(model: AdministradoraModel, gridApi?: any) {

    this.gridApi = gridApi;
    this.model = model;

    this.nome.setValue(this.model.pessoa.nome);
    this.cnpj.setValue(this.model.pessoa.documento);

    this.administradoraModal = this.modalService.open(this.modal, { size: 'lg' });
  }

  public onSubmit() {

    if (this.form.valid) {

      this.model.pessoa.nome = this.trin(this.nome.value);
      this.model.pessoa.documento = this.trin(this.cnpj.value);

      this.submitObservable = this.administradoraService.alterar(this.model).subscribe(res => {
        this.toastr.success('Administradora salva com sucesso', 'Administradora');
        this.administradoraModal.close();
        this.atualizarGrid();
      }, error => this.toastr.error(error, 'Administradora'));
    }
  }

  private trin(val: string): string {
    return val.trim();
  }

  private atualizarGrid() {
    if (this.gridApi) {
      this.gridApi.refreshCells();
    }
  }
}
