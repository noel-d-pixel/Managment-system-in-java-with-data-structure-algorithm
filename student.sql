
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


CREATE TABLE `student` (
  `name` varchar(50) NOT NULL,
  `entrynumber` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `contactnumber` varchar(15) NOT NULL,
  `homecity` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `student` (`name`, `entrynumber`, `email`, `contactnumber`, `homecity`) VALUES
('Studenti1', '00000000', 'studenti1@gmail.com', '0690000000', 'Tirana'),
('Studenti2', '00000001', 'studenti2@gmail.com', '0690000001', 'Shkodra'),
('Studenti3', '00000002', 'studenti3@gmail.com', '0690000002', 'Vlorë'),
('Studenti4', '00000003', 'studenti4@gmail.com', '0690000003', 'Durrës'),
('Studenti5', '00000004', 'studenti5@gmail.com', '0690000004', 'Korçë');
COMMIT;


CREATE TABLE IF NOT EXISTS student_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER after_student_insert
AFTER INSERT ON students
FOR EACH ROW
BEGIN
    INSERT INTO student_log (message) VALUES ('New student added with ID: ' || NEW.student_id);
END;


CREATE TRIGGER after_student_delete
AFTER DELETE ON students
FOR EACH ROW
BEGIN
    INSERT INTO student_log (message) VALUES ('Student deleted with ID: ' || OLD.student_id);
END;

CREATE TRIGGER after_student_update
AFTER UPDATE ON students
FOR EACH ROW
BEGIN
    INSERT INTO student_log (message) VALUES ('Student updated with ID: ' || NEW.student_id);
END;
//
DELIMITER ;




