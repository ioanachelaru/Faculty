@ECHO OFF
SET /A "index = 1"
:while
if %index% leq %1 (
START %2
SET /A "index = index + 1"
goto :while
)