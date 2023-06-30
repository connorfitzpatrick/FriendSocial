-- profiles table dummy data
INSERT INTO profiles (email, username, password, profile_picture, first_name, last_name, bio, join_timestamp, age)
VALUES
  ('john_doe@gmail.com', 'john_doe', 'password', 'profile1.jpg', 'John', 'Doe', 'Hello, I am John!', '2023-06-29 10:00:00', 20),
  ('jane_smith@gmail.com', 'jane_smith', 'password', 'profile2.jpg', 'Jane', 'Smith', 'Nice to meet you!', '2023-06-30 14:30:00', 23),
  ('alex_green@gmail.com', 'alex_green', 'password', 'profile3.jpg', 'Alex', 'Green', 'Tech enthusiast and nature lover.', '2023-07-01 09:15:00', 25);

-- posts table dummy data
INSERT INTO posts (profile_id, content, image_url, content_type, timestamp)
VALUES
  (1, 'Hello, world!', '', 'text', '2023-06-29 10:15:00'),
  (2, 'Having a great day!', '', 'text', '2023-06-30 15:00:00'),
  (3, '', 'image1.jpg', 'image', '2023-07-01 10:30:00');

-- followers table dummy data
INSERT INTO followers (follower_id, following_id)
VALUES
  (1, 2),
  (1, 3),
  (2, 1),
  (3, 1);

-- post_likes table dummy data
INSERT INTO post_likes (profile_id, post_id)
VALUES
  (1, 1),
  (1, 3),
  (2, 2),
  (3, 1),
  (3, 2);

-- comments table dummy data
INSERT INTO comments (profile_id, post_id, content, timestamp)
VALUES
  (1, 1, 'Great post!', '2023-06-29 10:30:00'),
  (2, 1, 'I agree!', '2023-06-29 11:00:00'),
  (2, 3, 'Nice picture!', '2023-06-30 16:30:00'),
  (3, 2, 'Well said!', '2023-07-01 11:15:00');
