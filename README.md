# shop.together.backend

The Shop2Gether backend is built with the less effort possible. We just discussed, sketched and implemented the domain model,
identified aggregates and exposed those as RESTful resources. The model looks like this:

![DM][1]

# Development

Build and run locally with a PostgreSQL database

```
$ mvn package
$ java -jar target/backend.jar --spring.profiles.active=PG
```

To see some already inserted data visit http://localhost:8080/api/owners

[1]: src/docs/res/S2G-Domain_Model.png
