CREATE TABLE IF NOT EXISTS users
(
    user_id   TEXT PRIMARY KEY,
    firstName TEXT NOT NULL,
    lastName  TEXT NOT NULL,
    email     TEXT NOT NULL,
    password  TEXT NOT NULL,
    avatar    BLOB,
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id TEXT NOT NULL,
    role    TEXT NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS languages
(
    language_name TEXT PRIMARY KEY,
    language_scale_name TEXT NOT NULL,
    note TEXT,
    FOREIGN KEY (language_scale_name) REFERENCES language_scales(language_scale_name)
);

CREATE TABLE IF NOT EXISTS language_scales
(
    language_scale_name TEXT PRIMARY KEY,
    language_scale_description TEXT
);

CREATE TABLE IF NOT EXISTS scale_levels
(
    scale_level_id TEXT,
    scale_level_name TEXT,
    language_scale_name TEXT,
    PRIMARY KEY (scale_level_id, scale_level_name),
    FOREIGN KEY (language_scale_name) REFERENCES language_scales(language_scale_name)
);

CREATE TABLE IF NOT EXISTS teachers
(
    teacher_id TEXT PRIMARY KEY,
    education  TEXT,
    FOREIGN KEY (teacher_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS teacher_languages
(
    teacher_id TEXT NOT NULL,
    language_name TEXT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
    FOREIGN KEY (language_name) REFERENCES languages(language_name)
);

CREATE TABLE IF NOT EXISTS students
(
    student_id TEXT PRIMARY KEY,
    age int,
    hobbies TEXT,
    channel TEXT,
    note TEXT,
    FOREIGN KEY (student_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS groups
(
    group_id   TEXT PRIMARY KEY,
    name       text NOT NULL,
    teacher_id TEXT,
    language_name TEXT,
    scale_level_id TEXT,
    FOREIGN KEY (scale_level_id) REFERENCES scale_levels(scale_level_id),
    FOREIGN KEY (language_name) REFERENCES languages (language_name),
    FOREIGN KEY (teacher_id) REFERENCES teachers (teacher_id)
);

CREATE TABLE IF NOT EXISTS student_group
(
    student_id  TEXT NOT NULL,
    group_id TEXT NOT NULL,
    PRIMARY KEY (student_id, group_id),
    FOREIGN KEY (student_id) REFERENCES users (user_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE IF NOT EXISTS payments
(
    payment_id  TEXT PRIMARY KEY,
    user_id     TEXT    NOT NULL,
    amount      INTEGER NOT NULL,
    date        TEXT    NOT NULL,
    description TEXT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS schedule
(
    schedule_id TEXT PRIMARY KEY,
    group_id    TEXT NOT NULL,
    dayOfWeek   TEXT NOT NULL,
    startTime   TEXT,
    endTime     TEXT,
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

