package br.com.unisul.verbo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import br.com.unisul.verbo.model.MensagemModel;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public MensagemModel index() {

		return new MensagemModel("OK");
	}

	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MensagemModel greeting(String message) throws Exception {
		Thread.sleep(5000); // simulated delay
		return new MensagemModel("Hello, " + HtmlUtils.htmlEscape(message) + "!" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
