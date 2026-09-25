///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//REPOS central-portal-snapshots
//DEPS dev.tamboui:tamboui-toolkit:0.5.0-SNAPSHOT
//DEPS dev.tamboui:tamboui-panama-backend:0.5.0-SNAPSHOT
//RUNTIME_OPTIONS --enable-native-access=ALL-UNNAMED

import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.text;

import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;

public class HelloDsl extends ToolkitApp {

    @Override
    protected Element render() {
        return panel(
                text("Hello Reachy, from the Toolkit DSL!").centered()
        ).title("reachy-demo");
    }

    public static void main(String[] args) throws Exception {
        new HelloDsl().run();
    }
}
