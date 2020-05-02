import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BoxModule, TabsModule, DropdownModule } from 'angular-admin-lte';
import { HeaderComponent } from './header/header.component';
import { MenuComponent } from './menu/menu.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    DropdownModule,
    TabsModule,
    BoxModule
  ],
  declarations: [HeaderComponent, MenuComponent],
  exports: [BoxModule, TabsModule, HeaderComponent, MenuComponent]
})
export class IncludesModule { }
