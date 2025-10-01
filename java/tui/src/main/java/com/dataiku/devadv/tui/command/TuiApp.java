package com.dataiku.devadv.tui.command;

import com.dataiku.devadv.tui.view.TuiAppViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.view.TerminalUI;
import org.springframework.shell.component.view.TerminalUIBuilder;
import org.springframework.shell.component.view.control.AppView;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TuiApp {
    @Autowired
    TerminalUIBuilder builder;

    @ShellMethod(key = "tui-app")
    public void launch() {
        TerminalUI ui = builder.build();
        AppView view = new TuiAppViewBuilder().createTuiAppView();

        ui.configure(view);
        ui.setRoot(view, true);
        ui.run();
    }
}