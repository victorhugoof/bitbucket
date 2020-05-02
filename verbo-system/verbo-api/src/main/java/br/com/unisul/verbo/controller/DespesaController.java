package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Despesa;
import br.com.unisul.verbo.model.DespesaModel;

@RestController
@RequestMapping("/despesa")
public class DespesaController extends BaseControllerImpl<Despesa, DespesaModel> {
}