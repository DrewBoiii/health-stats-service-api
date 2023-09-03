CREATE KEYSPACE IF NOT EXISTS healthstats WITH replication = {'class':'SimpleStrategy','replication_factor':1};

CREATE TABLE IF NOT EXISTS day_stats (
    id UUID PRIMARY KEY,
    req_date DATE,
    provider TEXT,
    country TEXT,
    continent TEXT,
    new_cases TEXT,
    critical_cases BIGINT,
    total_infected BIGINT,
    new_deaths TEXT,
    total_deaths BIGINT,
    vaccinated BIGINT
);

CREATE TABLE IF NOT EXISTS countries (
    provider TEXT PRIMARY KEY,
    countries LIST<TEXT>
);
