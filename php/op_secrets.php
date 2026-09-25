<?php

declare(strict_types=1);

function fetchOpNote(string $itemName): string
{
    $cmd = ['op', 'item', 'get', $itemName, '--fields', 'notesPlain', '--format', 'json'];
    $process = proc_open(
        $cmd,
        [1 => ['pipe', 'w'], 2 => ['pipe', 'w']],
        $pipes,
    );
    if (!is_resource($process)) {
        throw new RuntimeException('Unable to spawn `op`');
    }
    $stdout = stream_get_contents($pipes[1]);
    $stderr = stream_get_contents($pipes[2]);
    fclose($pipes[1]);
    fclose($pipes[2]);
    $exit = proc_close($process);
    if ($exit !== 0) {
        throw new RuntimeException(sprintf('`op` failed (exit %d): %s', $exit, trim($stderr)));
    }
    $decoded = json_decode($stdout, true, flags: JSON_THROW_ON_ERROR);

    return (string) ($decoded['value'] ?? '');
}

/**
 * Parse lines shaped like NAME="value" into an associative array.
 *
 * @return array<string,string>
 */
function parseDotenvNote(string $raw): array
{
    $vars = [];
    foreach (preg_split('/\R/', $raw) as $line) {
        if (preg_match('/^\s*([A-Za-z_][A-Za-z0-9_]*)\s*=\s*"(.*)"\s*$/', $line, $m)) {
            $vars[$m[1]] = $m[2];
        }
    }

    return $vars;
}

/**
 * Fetch a 1Password note once and return the requested NAME="value" entries.
 *
 * @return array<string,string>
 */
function fetchOpNoteVars(string $itemName, string ...$varNames): array
{
    $vars = parseDotenvNote(fetchOpNote($itemName));
    $result = [];
    foreach ($varNames as $varName) {
        if (!array_key_exists($varName, $vars)) {
            throw new RuntimeException(sprintf('%s absent de la note %s', $varName, $itemName));
        }
        $result[$varName] = $vars[$varName];
    }

    return $result;
}
