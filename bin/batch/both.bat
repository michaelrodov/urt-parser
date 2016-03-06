@echo off
For /f "tokens=1-4 delims=/ " %%a in ('echo %DATE%') do (set mydate=%%a%%b%%c)

echo This will try to parse the log files, hold on to your hats...
echo Parsing...

cd bin

echo MyServer Rocket Arena
cd C:\DATA\UrbanTerror42\Q3Log
del input\* /Q
rmdir output\* /Q /S
cd bin
java Q3Log both ..\config\default.conf ..\..\q3ut4\games.log ..\input\ut.dat ..\output\ myaccount-%mydate% arena false true

echo ...Finished

cd ..