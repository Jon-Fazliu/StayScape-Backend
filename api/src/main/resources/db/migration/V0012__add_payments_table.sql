CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    booking_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    lock_version INT NOT NULL DEFAULT 0,

    status VARCHAR(255) NOT NULL,

    payment_date TIMESTAMP,
    transaction_id VARCHAR(255),

    amount DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (booking_id) REFERENCES bookings (id)
);

CREATE TRIGGER trg_payments__check_created_at_unchanged
    BEFORE UPDATE ON payments
    FOR EACH ROW
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot update created_at column.';
    END IF;
END;

CREATE TRIGGER trg_payments__update_updated_at_and_lock
    BEFORE UPDATE
    ON payments
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;

    IF OLD.lock_version = NEW.lock_version THEN
        SET NEW.lock_version = OLD.lock_version + 1;
    END IF;
END;