package com.dataiku.devadv.tui.view;

import com.dataiku.devadv.tui.data.SampleData;
import org.springframework.shell.component.view.control.*;
import org.springframework.shell.component.view.event.KeyEvent;
import org.springframework.shell.geom.HorizontalAlign;
import org.springframework.shell.geom.VerticalAlign;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.List;

public class TuiAppViewBuilder {
  public AppView createTuiAppView() {

    MenuBarView menuBar = createMenuBarView();
    StatusBarView statusBar = createStatusBarView();
    BoxView main = createMainView();

    AppView view = new AppView(
            main, menuBar, statusBar
    );
    return view;
  }

  private static BoxView createMainView() {
    TableModel model = new ArrayTableModel(SampleData.getAnimals());
    TableBuilder tableBuilder = new TableBuilder(model);
    tableBuilder.addFullBorder(BorderStyle.fancy_light_triple_dash);

    BoxView main = new BoxView();
    main.setDrawFunction((screen, rect) -> {
      screen.writerBuilder().build().text(tableBuilder.build().render(80), rect, HorizontalAlign.CENTER, VerticalAlign.CENTER);
      return rect;
    });
    return main;
  }

  private static StatusBarView createStatusBarView() {
    StatusBarView.StatusItem item1 = new StatusBarView.StatusItem("Item1");
    StatusBarView statusBar = new StatusBarView(List.of(item1));
    return statusBar;
  }

  private static MenuBarView createMenuBarView() {
    Runnable quitAction = () -> {};
    Runnable aboutAction = () -> {};
    MenuBarView menuBar = MenuBarView.of(
            MenuBarView.MenuBarItem.of("File",
                            MenuView.MenuItem.of("Quit", MenuView.MenuItemCheckStyle.NOCHECK, quitAction))
                    .setHotKey(KeyEvent.Key.f | KeyEvent.KeyMask.AltMask),
            MenuBarView.MenuBarItem.of("Help",
                    MenuView.MenuItem.of("About", MenuView.MenuItemCheckStyle.NOCHECK, aboutAction))
    );
    return menuBar;
  }
}