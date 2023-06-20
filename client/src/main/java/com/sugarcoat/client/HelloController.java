package com.sugarcoat.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

	@FXML
	private Label welcomeText;

	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("哈哈，你是大傻!");
	}

}