package com.dataiku.devadv.tui.view;

import com.dataiku.devadv.tui.data.SampleData;
import org.springframework.shell.component.view.control.*;
import org.springframework.shell.component.view.event.KeyEvent;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.List;

public class TuiAppViewBuilder {

    public AppView createTuiAppView() {

        MenuBarView menuBar = createMenuBarView();
        StatusBarView statusBar = createStatusBarView();
        ListView main = createMainView();

        return new AppView(
                main, menuBar, statusBar
        );
    }

    private static ListView createMainView() {
        TableModel model = new ArrayTableModel(SampleData.getAnimals());
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light_triple_dash);

        ListView main = new ListView();
        String tableRendered = tableBuilder.build().render(80);
        String[] lines = tableRendered.split("\n");

        main.setItems(List.of(lines));

        return main;
    }

    private static StatusBarView createStatusBarView() {
        StatusBarView.StatusItem item1 = new StatusBarView.StatusItem("Item1");
        return new StatusBarView(List.of(item1));
    }

    private static MenuBarView createMenuBarView() {
        Runnable quitAction = () -> {
        };
        Runnable aboutAction = () -> {
        };
        return MenuBarView.of(
                MenuBarView.MenuBarItem.of("File",
                                MenuView.MenuItem.of("Quit", MenuView.MenuItemCheckStyle.NOCHECK, quitAction))
                        .setHotKey(KeyEvent.Key.f | KeyEvent.KeyMask.AltMask),
                MenuBarView.MenuBarItem.of("Help",
                        MenuView.MenuItem.of("About", MenuView.MenuItemCheckStyle.NOCHECK, aboutAction))
        );
    }
}