import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ClienteComponent } from './pages/cliente/cliente.component';
import { DependenteComponent } from './pages/dependente/dependente.component';
import { VendaComponent } from './pages/venda/venda.component';
import { FuncionarioComponent } from './pages/funcionario/funcionario.component';
import { ProdutoComponent } from './pages/produto/produto.component';
import { DespesaComponent } from './pages/despesa/despesa.component';
import { EntradaComponent } from './pages/entrada/entrada.component';
import { CrediarioComponent } from './pages/crediario/crediario.component';

const routes: Routes = [
  { path: '', data: { title: 'Início', }, component: HomeComponent },
  { path: 'vendas', data: { title: 'Vendas' }, component: VendaComponent },
  {
    path: 'cadastros', data: { title: 'Cadastros' }, children: [
      { path: 'clientes', data: { title: 'Clientes' }, component: ClienteComponent },
      { path: 'dependentes', data: { title: 'Dependentes' }, component: DependenteComponent },
      { path: 'funcionarios', data: { title: 'Funcionários' }, component: FuncionarioComponent },
      { path: 'produtos', data: { title: 'Produtos' }, component: ProdutoComponent }
    ]
  },
  {
    path: '', data: { title: 'Movimentos' }, children: [
      { path: 'despesas', data: { title: 'Despesas' }, component: DespesaComponent },
      { path: 'entradas', data: { title: 'Entradas de Produtos' }, component: EntradaComponent },
      { path: 'crediarios', data: { title: 'Crediários' }, component: CrediarioComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
