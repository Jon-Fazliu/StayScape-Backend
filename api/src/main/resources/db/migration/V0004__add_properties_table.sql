CREATE TABLE properties (
    id INT AUTO_INCREMENT PRIMARY KEY,
    place_id INT NOT NULL UNIQUE ,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    lock_version INT NOT NULL DEFAULT 0,

    type VARCHAR(255) DEFAULT 'HOTEL' NOT NULL,

    name VARCHAR(255) NOT NULL,
    description text NOT NULL,
    phone_number VARCHAR(255),
    website VARCHAR(255),
    FOREIGN KEY (place_id) REFERENCES places (id)
);

CREATE TRIGGER trg_properties__check_created_at_unchanged
    BEFORE UPDATE ON properties
    FOR EACH ROW
BEGIN
    IF OLD.created_at != NEW.created_at THEN
            SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot update created_at column.';
    END IF;
END;

CREATE TRIGGER trg_properties__update_updated_at_and_lock
    BEFORE UPDATE
    ON properties
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;

    IF OLD.lock_version = NEW.lock_version THEN
        SET NEW.lock_version = OLD.lock_version + 1;
    END IF;
END;