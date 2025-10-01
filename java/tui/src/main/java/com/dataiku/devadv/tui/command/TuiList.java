package com.dataiku.devadv.tui.command;

import com.dataiku.devadv.tui.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.view.TerminalUI;
import org.springframework.shell.component.view.TerminalUIBuilder;
import org.springframework.shell.component.view.control.BoxView;
import org.springframework.shell.component.view.control.ListView;
import org.springframework.shell.geom.HorizontalAlign;
import org.springframework.shell.geom.VerticalAlign;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class TuiList {
    @Autowired
    TerminalUIBuilder builder;

    @ShellMethod(key = "tui-list")
    public void launch() {
        TerminalUI ui = builder.build();
        ListView<String> view = new ListView<>();
        view.setItems(List.of("item1", "item2"));
        ui.setRoot(view, true);
        ui.run();
    }

}