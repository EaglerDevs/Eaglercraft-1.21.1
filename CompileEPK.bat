@echo off
title epkcompiler
cd resources
python optimize.py
cd ..
echo compiling, please wait...
java -jar "resources/CompileEPK.jar" "resources/optimizedResources" "javascript/assets.epk"
echo finished compiling epk
pause