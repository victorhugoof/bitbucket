import { ApplicationRef, ComponentFactoryResolver, EmbeddedViewRef, Injectable, Injector } from '@angular/core';
import { MensagemComponent } from '../components/mensagem/mensagem.component';

@Injectable({
    providedIn: 'root'
})
export class MensagensService {
    private mensagemComponent;

    constructor(private componentFactoryResolver: ComponentFactoryResolver,
        private appRef: ApplicationRef,
        private injector: Injector) {
    }

    sucesso(titulo: string, detalhe: string) {
        this.insereMensagem(titulo, detalhe, 'sucesso');
        window.scroll(0, 0);
    }

    informacao(titulo: string, detalhe: string) {
        this.insereMensagem(titulo, detalhe, 'info');
        window.scroll(0, 0);
    }

    alerta(titulo: string, detalhe: string) {
        this.insereMensagem(titulo, detalhe, 'alerta');
        window.scroll(0, 0);
    }

    error(titulo: string, detalhe: string) {
        this.insereMensagem(titulo, detalhe, 'erro');
        window.scroll(0, 0);
    }

    mensagemErro(error) {
        if (!Array.isArray(error)) {
            if (typeof error == 'string') {
                this.error('Erro!', error.toString());
            } else {
                this.error('Erro!', error.mensagem + ' - ' + error.motivo);
            }
        } else {
            for (let erro of error) {
                this.mensagemErro(erro);
            }
        }
        window.scroll(0, 0);
    }

    private insereMensagem(titulo: string, mensagem: string, tipo: string) {

        const componentFactory = this.componentFactoryResolver.resolveComponentFactory(MensagemComponent);
        const componentRef = componentFactory.create(this.injector);

        componentRef.instance.titulo = titulo;
        componentRef.instance.mensagem = mensagem;
        componentRef.instance.tipo = tipo;

        this.appRef.attachView(componentRef.hostView);

        const domElem = (componentRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;
        document.body.getElementsByTagName('router-outlet')[0].appendChild(domElem);

        this.mensagemComponent = componentRef;
    }

    limpar() {
        Array.from(document.body.getElementsByTagName('app-mensagem')).forEach(function (item) {
            item.remove();
        });
    }
}
