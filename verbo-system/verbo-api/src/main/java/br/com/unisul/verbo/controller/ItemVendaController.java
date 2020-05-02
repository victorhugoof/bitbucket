package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.ItemVenda;
import br.com.unisul.verbo.model.ItemVendaModel;

@RestController
@RequestMapping("/venda/item")
public class ItemVendaController extends BaseControllerImpl<ItemVenda, ItemVendaModel> {

}
