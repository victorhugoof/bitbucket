package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Crediario;
import br.com.unisul.verbo.model.CrediarioModel;

@RestController
@RequestMapping("/crediario")
public class CrediarioController extends BaseControllerImpl<Crediario, CrediarioModel> {

}
