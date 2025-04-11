-- users
CREATE TABLE "users" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  username VARCHAR(255) NOT NULL,
  phone VARCHAR(255),
  password VARCHAR(512) NOT NULL,
  gender VARCHAR(50),  -- Reduced length for gender
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_users_modtime BEFORE UPDATE ON "users" FOR EACH ROW EXECUTE PROCEDURE update_modified_column();





--sessions
CREATE TABLE sessions (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          start_date TIMESTAMP WITH TIME ZONE NOT NULL,
                          end_date TIMESTAMP WITH TIME ZONE NOT NULL,
                          image_url VARCHAR(255),
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
                          updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_sessions_modtime BEFORE UPDATE ON sessions FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
