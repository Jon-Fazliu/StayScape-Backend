CREATE TABLE activities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,

    last_activity TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_activity_type VARCHAR(255),

    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY unique_user_id (user_id)
);
