package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Entrada;
import br.com.unisul.verbo.model.EntradaModel;

@RestController
@RequestMapping("/entrada")
public class EntradaController extends BaseControllerImpl<Entrada, EntradaModel> {

}