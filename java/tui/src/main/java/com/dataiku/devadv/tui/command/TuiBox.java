package com.dataiku.devadv.tui.command;

import com.dataiku.devadv.tui.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.view.TerminalUI;
import org.springframework.shell.component.view.TerminalUIBuilder;
import org.springframework.shell.component.view.control.BoxView;
import org.springframework.shell.geom.HorizontalAlign;
import org.springframework.shell.geom.VerticalAlign;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TuiBox {
    @Autowired
    TerminalUIBuilder builder;

    @ShellMethod(key = "tui-box")
    public void launch() {
        TerminalUI ui = builder.build();
        BoxView view = new BoxView();
        ui.configure(view);
        view.setDrawFunction((screen, rect) -> {
            screen.writerBuilder().build().text("Box View pour Volcamp", rect, HorizontalAlign.CENTER, VerticalAlign.CENTER);
            return rect;
        });
        ui.setRoot(view, true);
        ui.run();
    }
}