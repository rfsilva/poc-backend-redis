@echo off
echo Running tests and checking coverage...
call mvn clean test jacoco:report jacoco:check
if %ERRORLEVEL% NEQ 0 (
    echo Test coverage failed. Please check the report.
    exit /b 1
) else (
    echo All tests passed and coverage requirements met!
    exit /b 0
)