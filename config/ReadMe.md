curl "http://localhost:8080/topjava/rest/meals/"

curl "http://localhost:8080/topjava/rest/meals/between?
startLocalDateTime=2015-05-30T01:00:00&endLocalDateTime=2015-05-30T23:00:00

curl "http://localhost:8080/topjava/rest/meals/100004"

curl -X "DELETE" http://localhost:8080/topjava/rest/meals/100004
