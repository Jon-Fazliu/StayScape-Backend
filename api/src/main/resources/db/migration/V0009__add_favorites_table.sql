CREATE TABLE favorites (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    place_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    lock_version INT NOT NULL DEFAULT 0,

    FOREIGN KEY (place_id) REFERENCES places (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (place_id, user_id)
);

CREATE TRIGGER trg_favorites__check_created_at_unchanged
    BEFORE UPDATE ON favorites
    FOR EACH ROW
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot update created_at column.';
    END IF;
END;

CREATE TRIGGER trg_favorites__update_updated_at_and_lock
    BEFORE UPDATE
    ON favorites
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;

    IF OLD.lock_version = NEW.lock_version THEN
        SET NEW.lock_version = OLD.lock_version + 1;
    END IF;
END;