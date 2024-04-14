CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    lodging_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    lock_version INT NOT NULL DEFAULT 0,

    beginning TIMESTAMP NOT NULL,
    end TIMESTAMP NOT NULL,

    status VARCHAR(255) NOT NULL,
    special_requests TEXT,


    FOREIGN KEY (lodging_id) REFERENCES lodgings (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TRIGGER trg_bookings__check_created_at_unchanged
    BEFORE UPDATE ON bookings
    FOR EACH ROW
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot update created_at column.';
    END IF;
END;

CREATE TRIGGER trg_bookings__update_updated_at_and_lock
    BEFORE UPDATE
    ON bookings
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;

    IF OLD.lock_version = NEW.lock_version THEN
        SET NEW.lock_version = OLD.lock_version + 1;
    END IF;
END;