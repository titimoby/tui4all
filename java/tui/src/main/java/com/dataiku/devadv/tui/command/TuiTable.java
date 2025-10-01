package com.dataiku.devadv.tui.command;

import com.dataiku.devadv.tui.data.SampleData;
import com.dataiku.devadv.tui.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.component.view.TerminalUI;
import org.springframework.shell.component.view.TerminalUIBuilder;
import org.springframework.shell.component.view.control.BoxView;
import org.springframework.shell.geom.HorizontalAlign;
import org.springframework.shell.geom.VerticalAlign;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

@ShellComponent
public class TuiTable  {
    @Autowired
    TerminalUIBuilder builder;

    @Autowired
    ShellHelper shellHelper;

    @ShellMethod(key = "tui-table")
    public void launch() {
        TableModel model = new ArrayTableModel(SampleData.getAnimals());
        TableBuilder tableBuilder = new TableBuilder(model);

        shellHelper.printInfo("air border style");
        tableBuilder.addFullBorder(BorderStyle.air);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("fancy_light border style");
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("fancy_double border style");
        tableBuilder.addFullBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));

        shellHelper.printInfo("mixed border style");
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));
    }
}
