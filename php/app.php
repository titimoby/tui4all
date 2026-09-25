<?php

declare(strict_types=1);

require __DIR__ . '/vendor/autoload.php';
require __DIR__ . '/op_secrets.php';

use PhpTui\Term\Actions;
use PhpTui\Term\Event\CharKeyEvent;
use PhpTui\Term\Terminal;
use PhpTui\Tui\DisplayBuilder;
use PhpTui\Tui\Extension\Core\Widget\BlockWidget;
use PhpTui\Tui\Extension\Core\Widget\GridWidget;
use PhpTui\Tui\Extension\Core\Widget\ParagraphWidget;
use PhpTui\Tui\Layout\Constraint;
use PhpTui\Tui\Text\Title;
use PhpTui\Tui\Widget\Borders;
use PhpTui\Tui\Widget\Direction;

try {
    $vars = fetchOpNoteVars('php-note', 'VALUE1', 'VALUE2');
    $left = $vars['VALUE1'];
    $right = $vars['VALUE2'];
} catch (Throwable $e) {
    $left = $right = '[error] ' . $e->getMessage();
}

$grid = GridWidget::default()
    ->direction(Direction::Horizontal)
    ->constraints(Constraint::percentage(50), Constraint::percentage(50))
    ->widgets(
        BlockWidget::default()
            ->borders(Borders::ALL)
            ->titles(Title::fromString('VALUE1'))
            ->widget(ParagraphWidget::fromString($left)),
        BlockWidget::default()
            ->borders(Borders::ALL)
            ->titles(Title::fromString('VALUE2'))
            ->widget(ParagraphWidget::fromString($right)),
    );

$terminal = Terminal::new();
$terminal->execute(Actions::alternateScreenEnable());
$terminal->execute(Actions::cursorHide());
$terminal->enableRawMode();

$display = DisplayBuilder::default()->build();

try {
    while (true) {
        $display->draw($grid);

        while (null !== $event = $terminal->events()->next()) {
            if ($event instanceof CharKeyEvent && $event->char === 'q') {
                break 2;
            }
        }

        usleep(50_000);
    }
} finally {
    $terminal->disableRawMode();
    $terminal->execute(Actions::cursorShow());
    $terminal->execute(Actions::alternateScreenDisable());
}
