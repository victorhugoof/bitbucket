import { Component, OnInit } from '@angular/core';
import { MensagensService } from 'src/app/service/mensagens.service';

@Component({
  selector: 'app-dependente',
  templateUrl: './dependente.component.html',
  styleUrls: ['./dependente.component.css']
})
export class DependenteComponent implements OnInit {

  constructor(private mensagensService: MensagensService) { }

  ngOnInit() {
    this.mensagensService.limpar();
  }

}
