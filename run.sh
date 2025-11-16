#!/bin/bash

# Plants vs Pets Auto-Run Script
echo "Starting Plants vs Pets (Auto-Run Mode)..."
echo "This script will compile and run the game with slower pace for demonstration."
echo ""

# Compile the Java files
echo "Compiling Java files..."
javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Compilation successful!"
echo ""

# Run the game with slower pace by modifying the sleep time in the code
# We'll use a simple method to run the game automatically by piping automated inputs

echo "Starting game with slower pace for demonstration..."
echo "Game will run automatically until completion..."
echo ""

# Run the demo game
java -cp bin DemoGame

echo ""
echo "Game finished!"