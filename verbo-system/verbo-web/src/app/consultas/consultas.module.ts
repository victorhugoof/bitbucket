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
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';
import { ClienteConsultaComponent } from './cliente-consulta/cliente-consulta.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    InputTextModule,
    KeyFilterModule,
    MessageModule,
    MessagesModule,
    ButtonModule,
    CalendarModule,
    BrowserAnimationsModule,
    RadioButtonModule,
    DropdownModule,
    InputTextareaModule,
    InputMaskModule,
    DynamicDialogModule,
    TableModule
  ],
  declarations: [ClienteConsultaComponent],
  exports: [ClienteConsultaComponent],
  entryComponents: [
    ClienteConsultaComponent
  ]
})
export class ConsultasModule { }
