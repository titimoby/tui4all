///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//REPOS central,central-portal-snapshots
//DEPS dev.tamboui:tamboui-toolkit:0.5.0-SNAPSHOT
//DEPS dev.tamboui:tamboui-panama-backend:0.5.0-SNAPSHOT
//DEPS show.calm:reachy-mini-client:0.1.0-SNAPSHOT
//RUNTIME_OPTIONS --enable-native-access=ALL-UNNAMED

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.lineGauge;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.text;

import java.util.Locale;

import dev.tamboui.style.Color;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyCode;
import dev.tamboui.tui.event.KeyEvent;

import show.calm.reachymini.client.Axis;
import show.calm.reachymini.client.Pose;
import show.calm.reachymini.client.ReachyClient;
import show.calm.reachymini.client.Safety;

/// Two sliders driving the Reachy Mini head: yaw (left/right) and pitch (up/down).
///
/// ↑↓ select a slider · ←→ move it · q quit
///
/// Requires the reachy-mini-client library in the local Maven repository
/// (`mvn install` in ../../../Reachy/reachy-client) and a running daemon.
public class HelloReachy extends ToolkitApp {

    /// A slider over one head axis, in degrees.
    record Slider(String label, Axis axis, double min, double max) {}

    // Demo ranges, kept well inside the limits measured on the real robot.
    static final Slider[] SLIDERS = {
            new Slider("yaw", Axis.HEAD_YAW, -60, 60),
            new Slider("pitch", Axis.HEAD_PITCH, -30, 30),
    };
    static final double STEP = 5;

    private final ReachyClient reachy = new ReachyClient();   // 127.0.0.1:8000
    private final double[] values = new double[SLIDERS.length];
    private int selected;
    private ReachyClient.TargetStream stream;

    @Override
    protected void onStart() {
        try {
            reachy.wakeUp();                      // smooth move to the neutral pose
            stream = reachy.openTargetStream();   // real-time targets over WebSocket
        } catch (Exception e) {
            throw new IllegalStateException("Cannot reach the Reachy daemon", e);
        }
    }

    @Override
    protected void onStop() {
        try {
            stream.close();
            reachy.goToSleep();
        } catch (Exception ignored) {
            // best effort on the way out
        }
        reachy.close();
    }

    @Override
    protected Element render() {
        var sliders = column();
        for (int i = 0; i < SLIDERS.length; i++) {
            Slider s = SLIDERS[i];
            String label = String.format(Locale.ROOT, "%s %-5s %+6.1f°",
                    i == selected ? ">" : " ", s.label(), values[i]);
            var gauge = lineGauge((values[i] - s.min()) / (s.max() - s.min())).label(label);
            sliders.add(i == selected ? gauge.filledColor(Color.YELLOW) : gauge);
        }
        sliders.add(text(" "));
        sliders.add(text("↑↓ select · ←→ move · q quit").dim());

        return panel(sliders)
                .title("reachy-demo")
                .rounded()
                .focusable()
                .onKeyEvent(this::onKey);
    }

    private EventResult onKey(KeyEvent e) {
        if (e.isCharIgnoreCase('q')) {
            quit();
        } else if (e.code() == KeyCode.UP || e.code() == KeyCode.DOWN) {
            selected = (selected + 1) % SLIDERS.length;
        } else if (e.code() == KeyCode.LEFT) {
            move(-STEP);
        } else if (e.code() == KeyCode.RIGHT) {
            move(STEP);
        } else {
            return EventResult.UNHANDLED;
        }
        return EventResult.HANDLED;
    }

    private void move(double delta) {
        Slider s = SLIDERS[selected];
        values[selected] = Math.clamp(values[selected] + delta, s.min(), s.max());

        // Always send the full pose: the daemon only enforces the head/body
        // yaw constraint deterministically when the head pose is in the call.
        Pose pose = Pose.NEUTRAL;
        for (int i = 0; i < SLIDERS.length; i++) {
            pose = pose.with(SLIDERS[i].axis(), Math.toRadians(values[i]));
        }
        stream.send(Safety.apply(pose).pose());
    }

    public static void main(String[] args) throws Exception {
        new HelloReachy().run();
    }
}
