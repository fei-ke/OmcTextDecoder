# OmcTextDecoder
Decode / Encode Samsung Note 8 (SM-N950N) csc files

## How To Use
Download the latest jar file [here](https://github.com/fei-ke/OmcTextDecoder/releases)

deocode

``` shell
java -jar omc-decoder.jar -i cscfeature.xml -o cscfeature_decoded.xml
```

or try to decode all files under a directory

``` shell
java -jar omc-decoder.jar -i omc -o omc_decoded
```

encode

```
java -jar omc-decoder.jar -e -i cscfeature_decoded.xml -o cscfeature.xml
```

or try to encode all files under a directory

``` shell
java -jar omc-decoder.jar -e -i omc_decoded -o omc_encoded
```

## How To Build

```
./gradlew jar
```

then the jar file output to ```build/libs/```
