@echo off
set /p floors="Enter no. of floors: "

echo Compiling...
javac src/generator/** -d out/

echo ...done

echo.
pause

java -cp out/ generator.GeneratorRunner %floors%

echo.
pause