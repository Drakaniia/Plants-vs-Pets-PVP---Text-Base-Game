#!/bin/bash

# Plants vs Pets Game Runner Script
# This script compiles and runs the Plants vs Pets text-based game

echo "Compiling Plants vs Pets game..."
javac -d bin src/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Starting Plants vs Pets game..."
    java -cp bin pvpMain
else
    echo "Compilation failed!"
    echo "Please check your Java installation and source files."
    exit 1
fi