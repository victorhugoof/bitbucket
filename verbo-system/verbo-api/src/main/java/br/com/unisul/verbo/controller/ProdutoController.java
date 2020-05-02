package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Produto;
import br.com.unisul.verbo.model.ProdutoModel;

@RestController
@RequestMapping("/produto")
public class ProdutoController extends BaseControllerImpl<Produto, ProdutoModel> {
}
