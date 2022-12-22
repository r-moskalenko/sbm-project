CREATE TABLE IF NOT EXISTS api_limiter
    (id SERIAL PRIMARY KEY,
    path VARCHAR(255),
    method VARCHAR(255),
    threshold integer,
    ttl integer,
    active boolean);

insert into api_limiter (path, method, threshold, ttl, active) values
    ('/get', 'GET', 2, 5, true);

insert into api_limiter (path, method, threshold, ttl, active) values
    ('/api/order', 'GET', 2, 5, true);