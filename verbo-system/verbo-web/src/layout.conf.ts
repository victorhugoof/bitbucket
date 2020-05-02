export const adminLteConf = {
  skin: 'blue',
  sidebarLeftMenu: [
    { label: 'Início', route: '/', iconClasses: 'fa fa-home'},
    { label: 'Vendas', route: 'vendas', iconClasses: 'fa fa-cart-arrow-down'},

    { label: 'CADASTROS', separator: true },
    { label: 'Clientes', route: 'cadastros/clientes', iconClasses: 'fa fa-user' },
    { label: 'Dependentes', route: 'cadastros/dependentes', iconClasses: 'fa fa-user-plus' },
    { label: 'Funcionários', route: 'cadastros/funcionarios', iconClasses: 'fa fa-users' },
    { label: 'Produtos', route: 'cadastros/produtos', iconClasses: 'fa fa-shopping-cart' },
    { label: 'MOVIMENTOS', separator: true },
    { label: 'Despesas', route: 'despesas', iconClasses: 'fa fa-money' },
    { label: 'Entradas de Produtos', route: 'entradas', iconClasses: 'fa fa-cart-plus' },
    { label: 'Crediários', route: 'crediarios', iconClasses: 'fa fa-cart-plus' }
  ]
};
