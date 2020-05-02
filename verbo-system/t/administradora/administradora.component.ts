import { Component, OnInit, HostListener, ElementRef } from '@angular/core';
import { AdministradoraService } from 'src/app/service/administradora.service';
import { FormataCpfCnpjPipe } from 'src/app/theme/pipes/formataCpfCnpj/formata-cpf-cnpj.pipe';

@Component({
  selector: 'app-administradora',
  templateUrl: './administradora.component.html',
  styleUrls: ['./administradora.component.scss']
})
export class AdministradoraComponent implements OnInit {

  public columnDefs = [
    { headerName: 'Id', field: 'id', suppressCellFlash: true },
    { headerName: 'Nome', field: 'pessoa.nome', suppressCellFlash: true },
    { headerName: 'Cnpj', field: 'pessoa.documento', valueFormatter: this.formatNumCpfCnpj, suppressCellFlash: true }
  ];

  private heightFixo = 175;
  public height: number;
  public dataSource: any;

  constructor(private administradoraService: AdministradoraService) { }

  ngOnInit() {
    this.dataSource = (model: any) => this.administradoraService.consultar(model);
    this.height = window.innerHeight - this.heightFixo;
  }

  @HostListener('window:resize')
  public onWindowResize(): void {
    this.height = window.innerHeight - this.heightFixo;
  }

  public formatNumCpfCnpj(obj: any): string {
    if (obj.value) {
      return FormataCpfCnpjPipe.formatNumCpfCnpj(obj.value);
    } else {
      return obj.value;
    }
  }
}
