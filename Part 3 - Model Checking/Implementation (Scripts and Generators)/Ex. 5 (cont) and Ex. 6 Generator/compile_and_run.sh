read -p "Enter no. of lifts: " lifts
read -p "Enter no. of floors: " floors

echo Compiling...
javac src/generator/** -d out/

echo ...done

echo
read -n1 -r -p "Press any key to continue..." key

java -cp out/ generator.GeneratorRunner $lifts $floors

echo
read -n1 -r -p "Press any key to continue..." key