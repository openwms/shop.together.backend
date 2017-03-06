# shop.together.backend

The Shop2Gether backend is built with less effort possible to back the Shop2Gether mobile application. We just discussed, sketched and implemented the domain model,
identified aggregates and exposed those as RESTful resources. The business object model, the persistency model (EO: Entity Objects) and view object model (VO) look like this:

![DM][1]

# Specialities

- Spatial Query Support. To find other users in the interested area the app requires a database that supports spatial queries. H2 and PostgreSQL are good candidates for that. Spatial Query support
in H2 is much more easy to activate than in PostgreSQL - just add a library (org.orbisgis:h2gis-ext) and call a function to activate it [see](src/main/resources/schema-H2.sql)
- Separation of view model and persistency model. Like we did in other projects, we start from the beginning with separate models for persistence and the public API. We use [Ameba](https://github.com/abraxas-labs/ameba-lib) and [Dozer](http://dozer.sourceforge.net/) for mapping between both.

# Interaction Model

How does the API interaction model look like. In general the flow of interactions looks
 like:
 
 a) Verify the client. Either signup of login is required
 
 b) After successful verification User details are required to load
 
 c) The User may already have items, those are loaded in a third step.

## Login

First we need to get over the login procedure and populate the security context. // to be described

## Get Owner information

After an user is authenticated, the client application may require more information about the authenticated user, so called Owner, and his Items.

```
GET /owners/{id}
```

Where {id} is the persistent identifier. Not the best solution to pass the persistent key up to the client but works well for this little app. Always follow KISS. 

This returns the OwnerVO representation

```
{
  "username": "capitain",
  "phonenumber": "00443250054942",
  "home": {
    "longitude": 7.350166,
    "latitude": 49.450632,
    "longitudeDelta": 0.0421,
    "latitudeDelta": 0.0922
  },
  "_links": {
    "items": [
      {
        "href": "https://shop2gether.herokuapp.com/items/1"
      },
      {
        "href": "https://shop2gether.herokuapp.com/items/2"
      },
      {
        "href": "https://shop2gether.herokuapp.com/items/3"
      },
      {
        "href": "https://shop2gether.herokuapp.com/items/4"
      }
    ],
  }
}
```

## Get Items of Owner

Items are post-loaded. As soon as the client got the Owner info, items are loaded one by one:

```
GET /items/{id}
```

An Item response may look like this:

```
{
  "_type": "tnote",
  "persistentKey": 1,
  "shareable": true,
  "version": 1,
  "title": "Shoppinglist 1",
  "text": "1 x 10 Eggs\n2 x Milk\n1 x Peanutbutter\n3 x Oranges\n1 big Pineapple\nSome cheese\n1pd. Meatballs",
  "color": "#E9E74A",
  "pinned": false,
  "_links": {
    "usergroups": {
      "href": "https://shop2gether.herokuapp.com/usergroups/1"
    }
  }
}
```

In this case an [Item](src/main/java/io/interface21/shop2gether/service/Item.java) is of type [TextNote](src/main/java/io/interface21/shop2gether/service/TextNote.java),
has some attributes and links to the [UserGroup](src/main/java/io/interface21/shop2gether/service/UserGroup.java) it is shared with.

# Development

Build and run locally with a PostgreSQL database

```
$ mvn package
$ java -jar target/backend.jar --spring.profiles.active=PG
```

Currently the postgres driver is commented out in pom.xml!

# Production

PostgreSQL is the database targeted in production. The current state of development uses an embedded H2 in production. That is for sure not the final solution but for now
an easy way to test the spatial stuff. The spatial extension for PostgreSQL requires some more setup. This is especially hard in a cloud environment where we don't have access rights to install 
db extensions. How to install PostGIS on Heroku is described here [https://devcenter.heroku.com/articles/heroku-postgres-extensions-postgis-full-text-search](https://devcenter.heroku.com/articles/heroku-postgres-extensions-postgis-full-text-search)

# Open Issues

No   | Desc
---- | ----
1    | Spring Hateos bug https://github.com/spring-projects/spring-hateoas/issues/169
2    | Heroku PostgreSQL issue
3    | Install PostGIS on Heroku 

[1]: src/docs/res/S2G-Domain_Model.png
