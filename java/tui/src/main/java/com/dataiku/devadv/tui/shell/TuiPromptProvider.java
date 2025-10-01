package com.dataiku.devadv.tui.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class TuiPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("TUI-DEMO:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
        );
    }
}
