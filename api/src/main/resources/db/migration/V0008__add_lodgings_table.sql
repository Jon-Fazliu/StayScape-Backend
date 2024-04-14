CREATE TABLE lodgings (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    property_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    lock_version INT NOT NULL DEFAULT 0,

    single_beds INT NOT NULL,
    double_beds INT NOT NULL,
    room_count INT NOT NULL,

    FOREIGN KEY (property_id) REFERENCES properties (id)
);

CREATE TRIGGER trg_lodgings__check_created_at_unchanged
    BEFORE UPDATE ON lodgings
    FOR EACH ROW
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot update created_at column.';
    END IF;
END;

CREATE TRIGGER trg_lodgings__update_updated_at_and_lock
    BEFORE UPDATE
    ON lodgings
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;

    IF OLD.lock_version = NEW.lock_version THEN
        SET NEW.lock_version = OLD.lock_version + 1;
    END IF;
END;