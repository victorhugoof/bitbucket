package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.ParcelaCrediario;
import br.com.unisul.verbo.model.ParcelaCrediarioModel;

@RestController
@RequestMapping("/crediario/parcela")
public class ParcelaCrediarioController extends BaseControllerImpl<ParcelaCrediario, ParcelaCrediarioModel> {

}

