package com.work.amqpboot.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CustomMessage {

	private String text;

	private int priority;

	private boolean secret;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isSecret() {
		return secret;
	}

	public void setSecret(boolean secret) {
		this.secret = secret;
	}

	public CustomMessage(String text, int priority, boolean secret) {
		super();
		this.text = text;
		this.priority = priority;
		this.secret = secret;
	}

	public CustomMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CustomMessage [text=" + text + ", priority=" + priority + ", secret=" + secret + "]";
	}
	
	

}