get all Meals for User:
curl -v http://localhost:8080/topjava/rest/meals

get Meal with id=100007:
curl -v http://localhost:8080/topjava/rest/meals/100007

get Meals between dates:
curl -v http://localhost:8080/topjava/rest/meals/filter?"startDate=2020-01-30&startTime=00:00&endDate=2020-01-30&endTime=23:59"

get Meals when filters are null:
curl -v http://localhost:8080/topjava/rest/meals/filter?"startDate=&startTime=&endDate=&endTime="

create new Meal:
curl -H "Content-Type: application/json" -X POST -d '{"dateTime":"2020-04-01T21:00:00","description":"Dinner","calories":700}' 
http://localhost:8080/topjava/rest/meals

update Meal description and calories with id=100003
curl -X PUT -H "Content-Type: application/json" -d '{"dateTime":"2020-01-30T13:00:00","description":"Changed description","calories":1003}' 
http://localhost:8080/topjava/rest/meals/100003

delete Meal with id=100004:
curl -X DELETE http://localhost:8080/topjava/rest/meals/100004



