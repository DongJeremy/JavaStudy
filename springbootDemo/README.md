# 构建

```
mkdir -p /opt/postgres
docker run -d \
    --name postgres \
    --restart=always \
    -e POSTGRES_PASSWORD=password \
    -e PGDATA=/var/lib/postgresql/data/pgdata \
    -v /opt/postgres:/var/lib/postgresql/data \
    -p 5432:5432 \
    postgres:9.5.22-alpine
```

# 配置数据库

```
docker exec -it postgres /bin/bash
psql -Upostgres
CREATE USER admin WITH PASSWORD 'password';
ALTER role admin with superuser;
CREATE DATABASE mydb;
GRANT ALL ON DATABASE mydb TO admin;
```