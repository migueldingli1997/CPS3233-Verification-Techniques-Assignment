mkdir bin

javac -d bin/ src/com/liftmania/*.java src/com/liftmania/gui/*.java src/runner/*.java

java -cp bin/ runner.Runner