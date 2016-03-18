pushd %CD%
For /f "tokens=1-4 delims=/ " %%a in ('echo %DATE%') do (set mydate=%%a-%%b-%%c)

:::::::::::: OPTIONS :::::::::::::::::::::::::
:: -l	logs location
:: -o	where to copy the result json file 
:: -lim	X	only add games that are longer then X minutes 
SET JSON_NAME=games.json
SET JSON_HOME=C:\Users\Carlos\Dropbox\urt\DATA
java -jar bin\target\urtlog-1.0.jar -l logs\games.log -o %JSON_HOME%\%JSON_NAME% -lim 15
cd %JSON_HOME%
git add %JSON_NAME% 
git commit -m "%JSON_NAME% update %mydate%"
git push origin develop
::git push origin master
popd



