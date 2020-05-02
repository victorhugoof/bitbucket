import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BoxModule, LayoutModule } from 'angular-admin-lte';
import { LoadingPageModule, MaterialBarModule } from 'angular-loading-page';
import { DialogService } from 'primeng/api';
import { adminLteConf } from '../layout.conf';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComponentsModule } from './components/components.module';
import { IncludesModule } from './includes/includes.module';
import { PagesModule } from './pages/pages.module';
import { environment } from 'src/environments/environment';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';

const myRxStompConfig: InjectableRxStompConfig = {
  brokerURL: environment.urlApiSocket,

  connectHeaders: {
    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjU5MDE5ODYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFIl0sImp0aSI6IjNjNmMyZmUzLTI1NDktNDBkZi05MDliLTBiNDQ4NTFjZWJkMCIsImNsaWVudF9pZCI6ImFuZ3VsYXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.mTGplDvGCpHNI3aWrbi-8X1lrLb88Jrgsxh2hpt5sik',
  },

  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 5000,

};

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BoxModule,
    LayoutModule.forRoot(adminLteConf),
    LoadingPageModule,
    MaterialBarModule,
    AppRoutingModule,
    PagesModule,
    IncludesModule,
    ComponentsModule
  ],
  providers: [DialogService,
    {
    provide: InjectableRxStompConfig,
    useValue: myRxStompConfig
  },
  {
    provide: RxStompService,
    useFactory: rxStompServiceFactory,
    deps: [InjectableRxStompConfig]
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
