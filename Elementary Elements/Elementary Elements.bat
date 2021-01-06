@echo off

echo In order to run this program Java 13 must be installed to the following directory
echo C:\Program Files\java\jdk-13.0.1\

"C:\Program Files\java\jdk-13.0.1\bin\java" -Dfile.encoding=UTF-8 -classpath "bin" src\ElementaryElements.java Elements2edited.csv

pause