# reachy-demo

Minimal [TamboUI](https://github.com/tamboui/tamboui) demos running on Java 25.
Each file is a standalone JBang script — no build file — and a self-contained part of
the talk.

| File | What it shows |
|------|---------------|
| `HelloDsl.java` | Hello world with the declarative Toolkit DSL: a tree of elements, no explicit event loop |
| `HelloReachy.java` | Two sliders piloting a live Reachy Mini — head **yaw** (±60°) and **pitch** (±30°) — through the [reachy-mini-client](https://gitlab.com/TitiMoby/reachy-client) library |

Both use `tamboui-toolkit` with the Panama backend: a pure-JDK terminal backend
(FFM, no JLine).

## Requirements

- JDK 25
- JBang

`HelloReachy.java` also needs:

- the `show.calm:reachy-mini-client` library in the local Maven repository:

  ```bash
  git clone https://gitlab.com/TitiMoby/reachy-client.git
  cd reachy-client && mvn install
  ```

- a running Reachy Mini daemon on `127.0.0.1:8000` (real robot or simulation).

## Run

```bash
jbang HelloDsl.java     # or simply ./HelloDsl.java
```

Press `q` to quit.

```bash
jbang HelloReachy.java   # or simply ./HelloReachy.java
```

`↑`/`↓` select a slider, `←`/`→` move it by 5°, `q` quits. The robot wakes up on
start and goes back to sleep on quit.

## Notes

The Panama backend (`tamboui-panama-backend`) talks to the terminal through the
JDK's Foreign Function & Memory API instead of JLine — hence
`//RUNTIME_OPTIONS --enable-native-access=ALL-UNNAMED`, required on Java 25 to
allow restricted native calls. Its only transitive dependency is `tamboui-core`.

`HelloReachy.java` sends its targets over the daemon's WebSocket, always as a full
pose passed through the library's `Safety` guards — see the
[reachy-mini-client README](https://gitlab.com/TitiMoby/reachy-client) for why.

These demos use TamboUI 0.5.0-SNAPSHOT, pulled from
https://central.sonatype.com/repository/maven-snapshots/.
