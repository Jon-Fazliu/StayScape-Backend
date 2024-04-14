CREATE TABLE places (
    id INT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    lock_version INT NOT NULL DEFAULT 0,

    street VARCHAR(255),
    street_number VARCHAR(255),
    postal_code VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),

    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),

    type VARCHAR(255) NOT NULL
);

CREATE TRIGGER trg_places__check_created_at_unchanged
    BEFORE UPDATE ON places
    FOR EACH ROW
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot update created_at column.';
    END IF;
END;

CREATE TRIGGER trg_places__update_updated_at_and_lock
    BEFORE UPDATE
    ON places
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;

    IF OLD.lock_version = NEW.lock_version THEN
        SET NEW.lock_version = OLD.lock_version + 1;
    END IF;
END;