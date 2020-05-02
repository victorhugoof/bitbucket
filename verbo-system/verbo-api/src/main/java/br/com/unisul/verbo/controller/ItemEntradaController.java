package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.ItemEntrada;
import br.com.unisul.verbo.model.ItemEntradaModel;

@RestController
@RequestMapping("/entrada/item")
public class ItemEntradaController extends BaseControllerImpl<ItemEntrada, ItemEntradaModel> {

}
