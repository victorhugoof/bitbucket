import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AlertModule as MkAlertModule, BoxModule } from 'angular-admin-lte';
import { MensagemComponent } from './mensagem/mensagem.component';

@NgModule({
  declarations: [MensagemComponent],
  imports: [
    CommonModule,
    MkAlertModule,
    BoxModule
  ],
  exports: [MensagemComponent],
  entryComponents: [MensagemComponent]
})
export class ComponentsModule { }
