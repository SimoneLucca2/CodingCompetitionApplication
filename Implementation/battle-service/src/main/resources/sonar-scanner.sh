#!/bin/sh

export SONAR_RUNNER_HOME=/home/marcolino/Documenti/SonarQube/sonar-scanner-cli-5.0.1.3006-linux/sonar-scanner-5.0.1.3006-linux
export PATH=/home/marcolino/Documenti/SonarQube/sonar-scanner-cli-5.0.1.3006-linux/sonar-scanner-5.0.1.3006-linux/bin:$PATH

directory="./analysis"

cd "$directory" || exit

command="sonar-scanner -Dsonar.projectKey=ckb -Dsonar.sources=./ -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqa_c4f04e0210ce47711da0af97bfc49a1f251ca9ae"

$command

echo DONE