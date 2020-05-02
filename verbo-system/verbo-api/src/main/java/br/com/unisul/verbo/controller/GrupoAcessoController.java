package br.com.unisul.verbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.GrupoAcesso;
import br.com.unisul.verbo.model.GrupoAcessoModel;

@RestController
@RequestMapping("/grupo-acesso")
public class GrupoAcessoController extends BaseControllerImpl<GrupoAcesso, GrupoAcessoModel> {
}