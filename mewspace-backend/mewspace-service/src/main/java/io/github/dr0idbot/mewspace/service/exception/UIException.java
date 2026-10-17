package io.github.dr0idbot.mewspace.service.exception;

public class UIException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String desc;

	public UIException(String message, String desc, Throwable cause) {
		super(message, cause);
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
