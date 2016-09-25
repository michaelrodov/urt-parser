#!/bin/bash

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
URT_HOME=/home/c/DATA/UrbanTerror42/q3ut4

cp $URT_HOME/$LOG_NAME $PARSER_HOME/logs/$LOG_NAME

cd $PARSER_HOME/bin
mvn clean install

java -jar $PARSER_HOME/bin/target/urtlog-1.0.jar -l $PARSER_HOME/logs/$LOG_NAME -o $JSON_HOME/$JSON_NAME -lim 15 -types 7 -admin PanzerjagerTiger -exclude "Bonev|OHAD|kobii|deadwomanwalking|Shalom|Kilaka|Dvir|RODOV.*?"
cd $JSON_HOME
git add $JSON_NAME
git commit -m "$JSON_NAME update $TIMESTAMP"
git pull --commit


#git push origin master
