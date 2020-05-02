package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.FormaPagamento;
import br.com.unisul.verbo.model.FormaPagamentoModel;

@RestController
@RequestMapping("/forma-pagamento")
public class FormaPagamentoController extends BaseControllerImpl<FormaPagamento, FormaPagamentoModel> {
}