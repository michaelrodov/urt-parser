pushd %CD%
For /f "tokens=1-4 delims=/ " %%a in ('echo %DATE%') do (set mydate=%%a-%%b-%%c)

:::::::::::: OPTIONS :::::::::::::::::::::::::
:: -l	logs location
:: -o	where to copy the result json file 
:: -lim	X	only add games that are longer then X minutes 
SET JSON_NAME=games.json
SET LOG_NAME=ctf_games.log
SET JSON_HOME=C:\GIT\urt-react\DATA
SET PARSER_HOME=C:\GIT\urt-parser
SET URT_HOME=C:\DATA\UrbanTerror42\q3ut4

XCOPY %URT_HOME%\%LOG_NAME% %PARSER_HOME%\logs\%LOG_NAME% /Y 

cd bin
call mvn clean install

java -jar %PARSER_HOME%\bin\target\urtlog-1.0.jar -l %PARSER_HOME%\logs\%LOG_NAME% -o %JSON_HOME%\%JSON_NAME% -lim 15 -types 7 -admin PanzerjagerTiger -exclude "Bonev|OHAD|kobii|dead\s?woman\s?walking|Shalom|Kilaka|\[\sA\sC\sE\s\]|RODOV.*|PanzerjagerTiger_.*"
cd %JSON_HOME%
git add %JSON_NAME% 
git commit -m "%JSON_NAME% update %mydate%"
git pull --commit

::upload to ftp

::git push origin develop
git push origin
popd



