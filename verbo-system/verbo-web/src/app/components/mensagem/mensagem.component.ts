import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-mensagem',
  templateUrl: './mensagem.component.html',
  styleUrls: ['./mensagem.component.css']
})
export class MensagemComponent implements OnInit {

  @Input()
  public titulo: string;

  @Input()
  public mensagem: string;

  @Input()
  public tipo: string;

  public icone: string;
  public class: string;

  constructor() {}

  ngOnInit() {
    if (this.tipo == 'alerta') {
      this.class = 'warning';
      this.icone = 'fa-warning';
    } else if (this.tipo == 'sucesso') {
      this.class = 'success';
      this.icone = 'fa-check';
    } else if (this.tipo == 'erro') {
      this.class = 'danger';
      this.icone = 'fa-ban';
    } else {
      this.class = 'info';
      this.icone = 'fa-info';
    }
  }

}
