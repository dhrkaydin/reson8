CREATE TABLE IF NOT EXISTS practice_routine (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_date DATE NOT NULL,
    category ENUM('SCALES', 'CHORDS', 'ARPEGGIOS', 'TECHNIQUE', 'SONGS', 'IMPROVISATION') NOT NULL,
    target_bpm INT,
    target_frequency_interval INT,
    target_frequency_unit VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS practice_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_date DATE NOT NULL,
    bpm INT,
    duration INT,
    practice_routine_id BIGINT,
    FOREIGN KEY (practice_routine_id) REFERENCES practice_routine(id)
);

CREATE TABLE IF NOT EXISTS practice_statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_practice_time INT,
    total_sessions INT,
    highest_bpm INT,
    total_bpm_increase INT,
    practice_routine_id BIGINT,
    FOREIGN KEY (practice_routine_id) REFERENCES practice_routine(id)
);


-- Dummy data for testing
INSERT INTO practice_routine (title, description, created_date, category, target_bpm, target_frequency_interval, target_frequency_unit)
VALUES
    ('Warm Up Routine', 'A simple warm-up routine.', '2025-01-01', 'TECHNIQUE', 100, 2, 'Days'),
    ('Technique Practice', 'Focus on technique exercises.', '2025-01-10', 'TECHNIQUE', 120, 1, 'Weeks'),
    ('Performance Routine', 'The 5 Major Modes', '2025-01-15', 'SCALES', 130, 3, 'Days');

INSERT INTO practice_session (session_date, bpm, duration, practice_routine_id)
VALUES
    ('2025-01-02', 100, 30, 1),
    ('2025-01-12', 120, 45, 2),
    ('2025-01-18', 130, 60, 3);

INSERT INTO practice_statistics (total_practice_time, total_sessions, highest_bpm, total_bpm_increase, practice_routine_id)
VALUES
    (180, 3, 120, 10, 1),
    (210, 4, 130, 15, 2),
    (240, 5, 140, 20, 3);