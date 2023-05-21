To generate database(s) tables, please follow these steps:

1. Install PostgreSQL at the host which is configured in adequate spring-boot profile. 
Usually it would be the local machine for dev and test profiles (localhost:5432).

2. Create database user 'autpae' with the adequate password in the newly installed database with the command:

CREATE USER autpae with encrypted password '<<PASSWORD>>';

///// 3. Manually create empty database 'autpae'. It can be done with pgAdmin or with commands:
/////
///// CREATE DATABASE autpae;

4. Run the application to generate all the tables in "autpae" schema.

Note: Please maintain prefix V### of migration scripts names in string sortable manner.