package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Venda;
import br.com.unisul.verbo.model.VendaModel;

@RestController
@RequestMapping("/venda")
public class VendaController extends BaseControllerImpl<Venda, VendaModel> {
}
