import { Component, OnInit } from '@angular/core';
import { LayoutService } from 'angular-admin-lte';
import { RxStompService } from '@stomp/ng2-stompjs';
import { RxStompState } from '@stomp/rx-stomp';
import { map, filter } from 'rxjs/operators';
import { Observable, Subscription } from 'rxjs';
import { Message } from '@stomp/stompjs';
import { TableBody } from 'primeng/table';
import { MensagensService } from './service/mensagens.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: []
})
export class AppComponent implements OnInit{
  public customLayout: boolean;

  public state: Observable<string>;
  private subscription: Subscription;
  public messages: Observable<Message>;
  public subscribed: boolean;
  public count = 0;

  public countResp = 0;

  constructor(private layoutService: LayoutService,
    private _stompService: RxStompService,
    private messageService: MensagensService){ }

  ngOnInit() {
    this.layoutService.isCustomLayout.subscribe((value: boolean) => {
      this.customLayout = value;
    });

    this.state = this._stompService.connectionState$.pipe(
      map ((state: number) => {
        console.log(`Current state: ${RxStompState[state]}`);
        return RxStompState[state];
      })
    );

    const MAX_RETRIES = 3;
    let numRetries = MAX_RETRIES;

    this._stompService.connectionState$.pipe(
      filter((state: number) => state === RxStompState.CLOSED)
    ).subscribe(() => {
      console.log(`Will retry ${numRetries} times`);
      if (numRetries <= 0) {
        this._stompService.deactivate();
      }
      numRetries--;
    });

    this._stompService.connected$.subscribe(() => {
      numRetries = MAX_RETRIES;
    });

    this.subscribed = false;

    this.subscribe();
    this.onSendMessage(this.count++);
  }

  connect() {
    this._stompService.activate();
  }

  disconnect() {
    this._stompService.deactivate();
  }

  public subscribe() {
    if (this.subscribed) {
      return;
    }

    this.messages = this._stompService.watch('/topic/greetings');
    this.subscription = this.messages.subscribe(this.on_next);
    this.subscribed = true;
  }

  public unsubscribe() {
    if (!this.subscribed) {
      return;
    }

    this.subscription.unsubscribe();
    this.subscription = null;
    this.messages = null;
    this.subscribed = false;
  }

  public onSendMessage(qtd?) {
    this._stompService.publish({
      destination: '/app/hello',
      body: 'Victor'
    });
  }

  public on_next = (message: Message) => {
    this.messageService.informacao('Websocket', message.body);
  }
}
