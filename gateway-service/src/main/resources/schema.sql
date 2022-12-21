CREATE TABLE IF NOT EXISTS api_limiter
    (id SERIAL PRIMARY KEY,
    path VARCHAR(255),
    method VARCHAR(255),
    threshold integer,
    ttl integer,
    active boolean);
