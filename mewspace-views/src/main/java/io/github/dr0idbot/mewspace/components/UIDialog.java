package io.github.dr0idbot.mewspace.components;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Pre;

import io.github.dr0idbot.vlucide.LucideIcon;

public final class UIDialog {

	public static final String CLASS_DIALOG = "UIDialog";
	public static final String CLASS_SHOW_MESSAGE = "showMessage";
	public static final String CLASS_SHOW_TRACE = "showTrace";

	private static final String TITLE_SHOW_TRACE = "Show trace";
	private static final String TITLE_HIDE_TRACE = "Hide trace";

	private UIDialog() {
	}

	public static void show(Object caller, String title, Exception e) {
		Logger logger = LoggerFactory.getLogger(caller.getClass());
		logger.error(title, e);
		showInternal(title, e != null ? e.getMessage() : null, e != null ? getStackTrace(e) : null);
	}

	public static void show(Object caller, String title, String message) {
		Logger logger = LoggerFactory.getLogger(caller.getClass());
		logger.warn("{}: {}", title, message);
		showInternal(title, message, null);
	}

	private static void showInternal(String title, String message, String trace) {
		var dialog = new Dialog();
		dialog.addClassName(CLASS_DIALOG);
		dialog.setHeaderTitle(title);

		var closeButton = new Button(LucideIcon.X.create(), event -> dialog.close());
		dialog.getHeader().add(closeButton);

		var messageDiv = new Div(message != null ? message : "");
		messageDiv.addClassName(CLASS_SHOW_MESSAGE);
		dialog.add(messageDiv);

		if (trace != null) {
			var traceButton = new Button(TITLE_SHOW_TRACE);
			var tracePre = new Pre(trace);
			tracePre.addClassName(CLASS_SHOW_TRACE);
			tracePre.setVisible(false);
			traceButton.addClickListener(event -> {
				boolean visible = !tracePre.isVisible();
				tracePre.setVisible(visible);
				traceButton.setText(visible ? TITLE_HIDE_TRACE : TITLE_SHOW_TRACE);
			});
			dialog.add(traceButton, tracePre);
		}

		dialog.open();
	}

	private static String getStackTrace(Exception e) {
		var writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}
}
