# Manager

Эмулятор для [kfu-quantum-platform](https://bitbucket.org/MFIsmagilov/kfu-quantum-platform.git).

```bash
git clone https://bitbucket.org/rstm-sf/qmanager.git
cd qmanager
git submodule update --init --recursive

cd src/main/cpp/quantum-simulator/
mkdir build && cd build
cmake ..
cmake --build .

cd ../../../../..
mvn clean install
```
