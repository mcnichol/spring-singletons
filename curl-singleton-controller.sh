#!/usr/bin/env bash

curl localhost:8080/work?do=ThingOne &
sleep .25 # Provide time for initial call to set ComponentIsSingleton.stringValue and Sleep
curl localhost:8080/morework?do=ThingTwo