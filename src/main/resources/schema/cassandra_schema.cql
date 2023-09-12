CREATE TABLE IF NOT EXISTS day_stats (
    id              UUID,
    req_date        DATE,
    provider        TEXT,
    country         TEXT,
    continent       TEXT,
    new_cases       TEXT,
    critical_cases  BIGINT,
    total_infected  BIGINT,
    new_deaths      TEXT,
    total_deaths    BIGINT,
    vaccinated      BIGINT,
    PRIMARY KEY ((provider, country), req_date)
)
WITH CLUSTERING ORDER BY (req_date DESC);

CREATE TABLE IF NOT EXISTS countries (
    provider    TEXT PRIMARY KEY,
    countries   LIST<TEXT>
);
