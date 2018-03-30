# OmcTextDecoder
Decode / Encode Samsung Note 8 (SM-N950N) csc files

## How To Use
Download the jar file [here](https://github.com/fei-ke/OmcTextDecoder/releases/download/v0.1/omc-decoder.jar)

deocode

``` shell
java -jar omc-decoder.jar -i cscfeature.xml -o cscfeature_decoded.xml
```

encode

```
java -jar omc-decoder.jar -e -i cscfeature_decoded.xml -o cscfeature.xml
```

## How To Build

```
./gradlew jar
```

then the jar file output to ```build/libs/```
