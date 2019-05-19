#!/bin/bash

git submodule update --init --recursive

cd src/main/cpp/quantum-simulator/
mkdir build && cd build
cmake ..
cmake --build .

cd ../../../../..
mvn clean install
