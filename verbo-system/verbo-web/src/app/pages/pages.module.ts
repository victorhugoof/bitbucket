import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { KeyFilterModule } from 'primeng/keyfilter';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';
import { ConsultasModule } from '../consultas/consultas.module';
import { ClienteComponent } from './cliente/cliente.component';
import { CrediarioComponent } from './crediario/crediario.component';
import { DependenteComponent } from './dependente/dependente.component';
import { DespesaComponent } from './despesa/despesa.component';
import { EntradaComponent } from './entrada/entrada.component';
import { FuncionarioComponent } from './funcionario/funcionario.component';
import { HomeComponent } from './home/home.component';
import { ProdutoComponent } from './produto/produto.component';
import { VendaComponent } from './venda/venda.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    InputTextModule,
    KeyFilterModule,
    ButtonModule,
    CalendarModule,
    BrowserAnimationsModule,
    RadioButtonModule,
    DropdownModule,
    InputTextareaModule,
    InputMaskModule,
    DynamicDialogModule,
    ConsultasModule,
    TableModule
  ],
  declarations: [
    HomeComponent,
    ClienteComponent,
    CrediarioComponent,
    DependenteComponent,
    DespesaComponent,
    EntradaComponent,
    FuncionarioComponent,
    ProdutoComponent,
    VendaComponent
  ],
  exports: [
  ]
})
export class PagesModule { }
