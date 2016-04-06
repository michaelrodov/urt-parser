pushd %CD%
For /f "tokens=1-4 delims=/ " %%a in ('echo %DATE%') do (set mydate=%%a-%%b-%%c)

:::::::::::: OPTIONS :::::::::::::::::::::::::
:: -l	logs location
:: -o	where to copy the result json file 
:: -lim	X	only add games that are longer then X minutes 
SET JSON_NAME=games.json
SET LOG_NAME=games.log
SET JSON_HOME=C:\GIT\urt\DATA
SET URT_HOME=C:\DATA\UrbanTerror42\q3ut4

XCOPY %URT_HOME%\%LOG_NAME% logs\%LOG_NAME% /Y 

java -jar bin\target\urtlog-1.0.jar -l logs\%LOG_NAME% -o %JSON_HOME%\%JSON_NAME% -lim 15 -types 7
cd %JSON_HOME%
git add %JSON_NAME% 
git commit -m "%JSON_NAME% update %mydate%"

::upload to ftp

::git push origin develop
::git push origin master
popd



