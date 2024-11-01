JRE 21 is required for correcting work and avoiding unexpected behavior or problems. 
By default, server use 8085 port for listing.

Data sources required to be under "app" level in application.yaml file. Currently, support only PostgreSQL database.

Here is an example of data sources:

```yaml
app:
   data-sources:
      - name: {data_source_name}
        url: jdbc:postgresql://localhost:{database_server_port:5432}/{database_name}
        table: {table_name}
        user: {database_username}
        password: {database_password}
        mapping:
            id: user_id
            username: login
            name: first_name
            surname: last_name
```

http://localhost:8085/swagger-ui/index.html - link to see swagger.

http://localhost:8085/users - API endpoint for getting all users from all data sources from application.yaml file.
