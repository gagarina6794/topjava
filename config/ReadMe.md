curl "http://localhost:8080/topjava/rest/meals/"

curl "http://localhost:8080/topjava/rest/meals/between?
startLocalDateTime=2015-05-30T01:00:00&endLocalDateTime=2015-05-30T23:00:00

curl "http://localhost:8080/topjava/rest/meals/100004"

curl -X "DELETE" http://localhost:8080/topjava/rest/meals/100004

curl -X PUT -H "Content-Type:application/json;charset=UTF-8" -d '{"dateTime":"2015-05-31T21:21","description":"Lunch","calories":20}' http://localhost:8080/topjava/rest/meals/100004

curl -X POST -H "Content-Type:application/json;charset=UTF-8" -d '{"dateTime":"2018-08-26T07:07","description":"Lunch","calories":20}' http://localhost:8080/topjava/rest/meals