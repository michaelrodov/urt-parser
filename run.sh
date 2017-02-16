#!/bin/bash

function error_exit
{
	echo "$1" 1>&2
	read -p "Press [Enter] key to close the window..."
	exit 1
}

#:::::::::::: OPTIONS :::::::::::::::::::::::::
#:: -l	logs location
#:: -o	where to copy the result json file
#:: -lim	X	only add games that are longer then X minutes
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
TIMESTAMP=`date +%Y-%m-%d_%H%M%S`

JSON_NAME=games.json
LOG_NAME=ctf_games.log
JSON_HOME=/home/rodov/git/urt-react/DATA
PARSER_HOME=/home/rodov/git/urt-parser
URT_HOME=/home/rodov/.q3a/q3ut4
#/home/rodov/.q3a/q3ut4/home/c/DATA/UrbanTerror42/q3ut4
cp $URT_HOME/$LOG_NAME $PARSER_HOME/logs/$LOG_NAME  || error_exit "failed to copy. Aborting"

cd $PARSER_HOME/bin
mvn clean install  || error_exit "failed to build urt-parser. Aborting"

java -jar $PARSER_HOME/bin/target/urtlog-1.0.jar -l $PARSER_HOME/logs/$LOG_NAME -o $JSON_HOME/$JSON_NAME -lim 15 -types 7 -admin PanzerjagerTiger -exclude "Bonev|Panzerjager\sTiger|OHAD|kobii|dead\s?woman\s?walking|Shalom|Kilaka|\[\sA\sC\sE\s\]|RODOV.*|Tal|PanzerjagerTiger_.*|KING_.*|DOD|Ed_.*"  || error_exit "failed to run parser. Aborting"

cd $JSON_HOME

git add $JSON_NAME
git commit -m "$JSON_NAME update $TIMESTAMP"
git pull --commit
git push -u origin HEAD:master  || error_exit "failed to push. Aborting"

read -p "Press [Enter] key to close the window..."
