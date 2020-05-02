package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Dependente;
import br.com.unisul.verbo.model.DependenteModel;

@RestController
@RequestMapping("/dependente")
public class DependenteController extends BaseControllerImpl<Dependente, DependenteModel> {
}