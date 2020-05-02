package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.CondicaoPagamento;
import br.com.unisul.verbo.model.CondicaoPagamentoModel;

@RestController
@RequestMapping("/condicao-pagamento")
public class CondicaoPagamentoController extends BaseControllerImpl<CondicaoPagamento, CondicaoPagamentoModel> {
}

