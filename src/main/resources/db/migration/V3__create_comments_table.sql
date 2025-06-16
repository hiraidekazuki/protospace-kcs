-- V3__create_comments_table.sql
CREATE TABLE comments (
  id       SERIAL       NOT NULL,
  text     VARCHAR(512) NOT NULL,
  user_id  INT          NOT NULL,
  proto_id INT          NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id)  REFERENCES users(id),
  FOREIGN KEY (proto_id) REFERENCES protos(id) ON DELETE CASCADE
);
