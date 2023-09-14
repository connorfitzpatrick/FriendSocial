package com.friendsocial.Backend.config;

import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.comment.CommentRepository;
import com.friendsocial.Backend.elasticsearch.ElasticsearchUserService;
import com.friendsocial.Backend.friend.Friend;
import com.friendsocial.Backend.friend.FriendRepository;
import com.friendsocial.Backend.like.Like;
import com.friendsocial.Backend.like.LikeRepository;
import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.user.Role;
import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
public class DummyDataConfig {

  @Bean(name = "userCommandLineRunner")
  CommandLineRunner userConfig(
          UserRepository repository,
          PasswordEncoder passwordEncoder,
          ElasticsearchUserService elasticsearchUserService) {
    return args -> {
      Instant now = Instant.now();
      User user1 = new User(
              "c@gmail.com",
              "fitz_connor",
              passwordEncoder.encode("123"),
              LocalDate.of(1999, 4, 29),
              "Connor",
              "Fitzpatrick",
              "5b37e3f7-b1fa-4342-a45a-0151a6e90e74.png",
              "MY APP",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );
      User user2 = new User(
              "alexrodriguez@gmail.com",
              "arod",
              "aerkg",
              LocalDate.of(2000, 12, 25),
              "Alex",
              "Rodriguez",
              "arod_PP.png",
              "I Alex",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );
      User user3 = new User(
              "kieran@gmail.com",
              "fitz_kieran",
              "bbbbbbb",
              LocalDate.of(1995, 06, 20),
              "Kieran",
              "Fitzpatrick",
              "yankees.png",
              "Dummy data account",
              now.minusSeconds(29 * 25 * 60 * 60),
              Role.USER
      );
      User user4 = new User(
              "peyton@gmail.com",
              "PeytonManning",
              "bbbbsdfbbb",
              LocalDate.of(1979, 03, 12),
              "Peyton",
              "Manning",
              "PeytonManning.png",
              "Not affiliated with real Peyton Manning",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );
      User user5 = new User(
              "bobross@gmail.com",
              "therealbobross",
              "svsb",
              LocalDate.of(1954, 9, 22),
              "Bob",
              "Ross",
              "bob_ross_pp.png",
              "Mistakes are Happy Accidents",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );
      User user6 = new User(
              "johndoe@example.com",
              "johndoe",
              "password1",
              LocalDate.of(1995, 7, 15),
              "John",
              "Doe",
              "1bb90181-ffe0-4d2e-8247-af234fac3734.png",
              "Exploring the world one pixel at a time",
              now.minusSeconds(15 * 24 * 60 * 60),
              Role.USER
      );

      User user7 = new User(
              "jsmith@example.com",
              "jane_smith",
              "password2",
              LocalDate.of(1990, 3, 20),
              "Jane",
              "Smith",
              "6cc7981d-b1eb-495b-aab7-673bcffd6d9a.png",
              "Chasing dreams and capturing moments",
              now.minusSeconds(10 * 24 * 60 * 60),
              Role.USER
      );

      User user8 = new User(
              "Inky@example.com",
              "Inky",
              "password3",
              LocalDate.of(1987, 9, 5),
              "Bashful",
              "Ghost",
              "8cc87ea1-3326-4449-a18a-f3c3c0cf4bd9.png",
              "I am the blue ghost",
              now.minusSeconds(5 * 24 * 60 * 60),
              Role.USER
      );

      User user9 = new User(
              "user4@example.com",
              "E_Brown",
              "password4",
              LocalDate.of(2001, 6, 12),
              "Emily",
              "Brown",
              "8ec8f681-f335-486b-93f6-e242f8d00a11.png",
              "Adventure awaits",
              now.minusSeconds(2 * 24 * 60 * 60),
              Role.USER
      );

      User user10 = new User(
              "Clyde@example.com",
              "Clyde",
              "password5",
              LocalDate.of(1998, 11, 8),
              "Pokey",
              "Ghost",
              "9cae6944-5a7b-423c-9bde-4c60af5101b3.png",
              "I am the orange Ghost",
              now.minusSeconds(8 * 24 * 60 * 60),
              Role.USER
      );

      User user11 = new User(
              "Blinky@example.com",
              "Blinky",
              "password6",
              LocalDate.of(1985, 1, 25),
              "Shadow",
              "Ghost",
              "0734ef5f-01e3-4e5c-9f78-913b13d00c61.png",
              "I am the red ghost",
              now.minusSeconds(23 * 24 * 60 * 60),
              Role.USER
      );

      User user12 = new User(
              "wmartinez@example.com",
              "W!llMartin3z",
              "password7",
              LocalDate.of(1993, 10, 4),
              "William",
              "Martinez",
              "03918e57-d7fc-4454-a341-942be0394aa5.png",
              "Coffee lover",
              now.minusSeconds(18 * 24 * 60 * 60),
              Role.USER
      );

      User user13 = new User(
              "sopharris@example.com",
              "sopharris",
              "password8",
              LocalDate.of(1979, 12, 17),
              "Sophia",
              "Harris",
              "10406a3b-2f70-464c-a9b2-149c55a1cadd.png",
              "Creating memories through my lens",
              now.minusSeconds(32 * 24 * 60 * 60),
              Role.USER
      );

      User user14 = new User(
              "Pinky@example.com",
              "Pinky",
              "password9",
              LocalDate.of(2000, 8, 9),
              "Speedy",
              "Ghost",
              "20603be3-144f-4c37-b861-28ee1610a976.png",
              "I am the pink ghost",
              now.minusSeconds(12 * 24 * 60 * 60),
              Role.USER
      );

      User user15 = new User(
              "avaclark@example.com",
              "avaclark",
              "password10",
              LocalDate.of(2003, 2, 22),
              "Ava",
              "Clark",
              "8c162b0a-4b4e-4bb2-94d6-ce48a0971a2a.png",
              "Dancing through life's ups and downs",
              now.minusSeconds(40 * 24 * 60 * 60),
              Role.USER
      );
      User user16 = new User(
              "aileen@example.com",
              "fitz_aileen",
              "password10",
              LocalDate.of(2003, 2, 22),
              "Aileen",
              "Fitzpatrick",
              "5b17f9c0-47fb-4966-bdf4-707a0383d3e5.png",
              "I am Aileen",
              now.minusSeconds(40 * 24 * 60 * 60),
              Role.USER
      );
      User user17 = new User(
              "ian@example.com",
              "fitz_ian",
              "password10",
              LocalDate.of(2003, 2, 22),
              "Ian",
              "Fitzpatrick",
              "cd78afa0-8510-4432-b461-822e1ec48de9.png",
              "I am Ian",
              now.minusSeconds(40 * 24 * 60 * 60),
              Role.USER
      );


      List<User> users = userRepository.saveAll(
              List.of(user1,
                      user2,
                      user3,
                      user4,
                      user5,
                      user6,
                      user7,
                      user8,
                      user9,
                      user10,
                      user11,
                      user12,
                      user13,
                      user14,
                      user15,
                      user16,
                      user17
              )
      );

      System.out.println("Adding dummy data users to elasticsearch");
      for (User user : users) {
        elasticsearchUserService.indexUser(user);
      }
    };
  }


  @Autowired
  private UserRepository userRepository;

  @Bean
  CommandLineRunner commandLineRunner1(
          PostRepository drepository) {
    return args -> {
      Optional<User> userOptional = userRepository.findById(1L);
      User p = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
      Post connorsFirst = new Post(
              1L,
              "Text",
              "This is my first post.",
              Instant.parse("2023-08-01T10:15:30Z"),
              ""
      );
      Post connorsSecond = new Post(
              1L,
              "Text",
              "This is my second post.",
              Instant.parse("2023-08-02T14:30:45Z"),
              ""
      );
      Post alexFirst = new Post(
              2L,
              "Text",
              "Ayo I am Alex.",
              Instant.parse("2023-08-03T08:20:15Z"),
              ""
      );
      Post alexSecond = new Post(
              3L,
              "Text",
              "This is a selfie of me, Connor.",
              Instant.parse("2023-08-04T16:45:00Z"),
              ""
      );
      Post connorsThird = new Post(
              1L,
              "Text",
              "This is long post to check and see how the information I put down is formatted within the Angular component. I hope" +
                      "that this looks okay! If it doesn't I'll have to fix it. I am going to figure out how to out how to upload pictures next " +
                      ".",
              Instant.parse("2023-08-05T12:10:30Z"),
              ""
      );
      Post connorsFourth = new Post(
              3L,
              "Text",
              "I'm hungry.",
              Instant.parse("2023-08-06T18:25:15Z"),
              ""
      );
      Post connorsFifth = new Post(
              2L,
              "Text",
              "Just more test data.",
              Instant.parse("2023-08-07T09:55:45Z"),
              ""
      );
      Post peyton1 = new Post(
              4L,
              "Text",
              "Heres a post from my new user.",
              Instant.parse("2023-08-08T15:40:00Z"),
              ""
      );
      Post bob1 = new Post(
              5L,
              "Image",
              "This is a nice one",
              Instant.parse("2023-08-09T13:05:30Z"),
              "re_resized_7469946d-d78a-4648-a7fe-eaa023d8e24f.png"
      );
      Post bob2 = new Post(
              5L,
              "Image",
              "This post took 30 minutes. You can do it too!",
              Instant.parse("2023-08-10T11:30:15Z"),
              "re_resized_605b1bd5-200e-4eb5-8311-ae7e62b1f5ea.png"
      );
      Post inky1 = new Post(
              8L,
              "Text",
              "I combine the positions of Blinky and a spot relative to myself to determine where to go",
              Instant.parse("2023-08-29T15:40:00Z"),
              ""
      );
      Post inky2 = new Post(
              8L,
              "Text",
              "In other words, my movement is unpredictable",
              Instant.parse("2023-08-29T15:39:00Z"),
              ""
      );
      Post clyde = new Post(
              10L,
              "Text",
              "I chase pacman directly but scatter when close",
              Instant.parse("2023-08-29T15:00:00Z"),
              ""
      );
      Post blinky = new Post(
              11L,
              "Text",
              "I head directly towards pac-man's current position",
              Instant.parse("2023-08-29T15:20:00Z"),
              ""
      );
      Post pinky = new Post(
              14L,
              "Text",
              "I try to sneak up in front of pac-man's path",
              Instant.parse("2023-08-29T15:10:00Z"),
              ""
      );

      drepository.saveAll(
              List.of(connorsFirst,
                      bob1,
                      connorsSecond,
                      peyton1,
                      alexFirst,
                      alexSecond,
                      bob2,
                      connorsThird,
                      connorsFourth,
                      connorsFifth,
                      inky1,
                      inky2,
                      clyde,
                      blinky,
                      pinky
              )
      );
    };
  }

  @Bean
  CommandLineRunner commandLineRunner2(
          FriendRepository frepository) {
    return args -> {
      Instant now = Instant.now();

//      Long userId = 1L;
//      Long friendId = 2L;
//      Long friend2Id = 3L;
//      Long friend4Id = 4L;
      Optional<User> userOptional1 = userRepository.findById(1L);
      Optional<User> userOptional2 = userRepository.findById(2L);
      Optional<User> userOptional3 = userRepository.findById(3L);
      Optional<User> userOptional4 = userRepository.findById(4L);
      Optional<User> userOptional5 = userRepository.findById(5L);
      Optional<User> userOptional6 = userRepository.findById(6L);
      Optional<User> userOptional7 = userRepository.findById(7L);
      Optional<User> userOptional8 = userRepository.findById(8L);
      Optional<User> userOptional9 = userRepository.findById(9L);
      Optional<User> userOptional10 = userRepository.findById(10L);
      Optional<User> userOptional11 = userRepository.findById(11L);
      Optional<User> userOptional12 = userRepository.findById(12L);
      Optional<User> userOptional13 = userRepository.findById(13L);
      Optional<User> userOptional14 = userRepository.findById(14L);
      Optional<User> userOptional15 = userRepository.findById(15L);
      Optional<User> userOptional16 = userRepository.findById(16L);
      Optional<User> userOptional17 = userRepository.findById(17L);

      User connor = userOptional1.orElseThrow(() -> new RuntimeException("User not found"));
      User alex = userOptional2.orElseThrow(() -> new RuntimeException("User not found"));
      User kieran = userOptional3.orElseThrow(() -> new RuntimeException("User not found"));
      User peyton = userOptional4.orElseThrow(() -> new RuntimeException("User not found"));
      User bob = userOptional5.orElseThrow(() -> new RuntimeException("User not found"));
      User john = userOptional6.orElseThrow(() -> new RuntimeException("User not found"));
      User jane = userOptional7.orElseThrow(() -> new RuntimeException("User not found"));
      User bashful = userOptional8.orElseThrow(() -> new RuntimeException("User not found"));
      User emily = userOptional9.orElseThrow(() -> new RuntimeException("User not found"));
      User pokey = userOptional10.orElseThrow(() -> new RuntimeException("User not found"));
      User shadow = userOptional11.orElseThrow(() -> new RuntimeException("User not found"));
      User william = userOptional12.orElseThrow(() -> new RuntimeException("User not found"));
      User sophia = userOptional13.orElseThrow(() -> new RuntimeException("User not found"));
      User speedy = userOptional14.orElseThrow(() -> new RuntimeException("User not found"));
      User ava = userOptional15.orElseThrow(() -> new RuntimeException("User not found"));
      User aileen = userOptional16.orElseThrow(() -> new RuntimeException("User not found"));
      User ian = userOptional17.orElseThrow(() -> new RuntimeException("User not found"));

      Friend connorfriend1 = new Friend(
              connor,
              alex,
              LocalDate.now()
      );
      Friend connorfriend2 = new Friend(
              connor,
              kieran,
              LocalDate.now()
      );
      Friend connorfriend3 = new Friend(
              connor,
              peyton,
              LocalDate.now()
      );
      Friend connorfriend4 = new Friend(
              connor,
              bob,
              LocalDate.now()
      );
      Friend connorfriend5 = new Friend(
              connor,
              john,
              LocalDate.now()
      );
      Friend connorfriend6 = new Friend(
              connor,
              jane,
              LocalDate.now()
      );
      Friend connorfriend7 = new Friend(
              connor,
              bashful,
              LocalDate.now()
      );
      Friend connorfriend8 = new Friend(
              connor,
              emily,
              LocalDate.now()
      );
      Friend connorfriend9 = new Friend(
              connor,
              pokey,
              LocalDate.now()
      );
      Friend connorfriend10 = new Friend(
              connor,
              shadow,
              LocalDate.now()
      );
      Friend connorfriend11 = new Friend(
              connor,
              william,
              LocalDate.now()
      );
      Friend connorfriend12 = new Friend(
              connor,
              sophia,
              LocalDate.now()
      );
      Friend connorfriend13 = new Friend(
              connor,
              speedy,
              LocalDate.now()
      );
      Friend connorfriend14 = new Friend(
              connor,
              ava,
              LocalDate.now()
      );
      Friend connorfriend15 = new Friend(
              connor,
              aileen,
              LocalDate.now()
      );
      Friend connorfriend16 = new Friend(
              connor,
              ian,
              LocalDate.now()
      );
      Friend peytonfriend1 = new Friend(
              peyton,
              connor,
              LocalDate.now()
      );
      Friend peytonfriend2 = new Friend(
              peyton,
              alex,
              LocalDate.now()
      );

      frepository.saveAll(
              List.of(connorfriend1,
                      connorfriend2,
                      connorfriend3,
                      connorfriend4,
                      connorfriend5,
                      connorfriend6,
                      connorfriend7,
                      connorfriend8,
                      connorfriend9,
                      connorfriend10,
                      connorfriend11,
                      connorfriend12,
                      connorfriend13,
                      connorfriend14,
                      connorfriend15,
                      connorfriend16,
                      peytonfriend1,
                      peytonfriend2)
      );
    };
  }


  @Autowired
  private CommentRepository cRepository;

  @Bean
  CommandLineRunner commandLineRunner3(
          CommentRepository crepository) {
    return args -> {
      Comment comment1 = new Comment(
              2L,
              1L,
              "This is a comment",
              Instant.now()
      );
      Comment comment2 = new Comment(
              3L,
              1L,
              "This is another comment",
              Instant.now()
      );
      Comment comment3 = new Comment(
              1L,
              3L,
              "This is the third comment",
              Instant.now()
      );
      Comment comment4 = new Comment(
              1L,
              1L,
              "Too many Comments!",
              Instant.now()
      );
      Comment comment5 = new Comment(
              2L,
              4L,
              "Weird post bro",
              Instant.now()
      );
      Comment comment8 = new Comment(
              11L,
              11L,
              "Wait you do what?",
              Instant.now()
      );
      Comment comment7 = new Comment(
              10L,
              11L,
              "Huh",
              Instant.now()
      );
      Comment comment6 = new Comment(
              14L,
              11L,
              "That makes no sense",
              Instant.now()
      );
      Comment comment18 = new Comment(
              4L,
              9L,
              "Nice one Bob",
              Instant.now()
      );
      Comment comment19 = new Comment(
              4L,
              10L,
              "Fire",
              Instant.now()
      );

      crepository.saveAll(
              List.of(comment1,
                      comment2,
                      comment3,
                      comment4,
                      comment5,
                      comment6,
                      comment7,
                      comment8,
                      comment18,
                      comment19
              )
      );
    };
  }

  @Autowired
  private LikeRepository lRepository;

  @Bean
  CommandLineRunner commandLineRunner4(
          LikeRepository lrepository) {
    return args -> {
      Like like1 = new Like(
              2L,
              1L,
              Instant.now()
      );
      Like like2 = new Like(
              3L,
              1L,
              Instant.now()
      );
      Like like3 = new Like(
              1L,
              2L,
              Instant.now()
      );
      Like like4 = new Like(
              2L,
              2L,
              Instant.now()
      );
      Like like5 = new Like(
              3L,
              2L,
              Instant.now()
      );
      Like like6 = new Like(
              1L,
              3L,
              Instant.now()
      );
      Like like7 = new Like(
              1L,
              4L,
              Instant.now()
      );
      Like like8 = new Like(
              4L,
              2L,
              Instant.now()
      );
      Like like9 = new Like(
              4L,
              9L,
              Instant.now()
      );
      Like like10 = new Like(
              10L,
              11L,
              Instant.now()
      );
      Like like11 = new Like(
              11L,
              11L,
              Instant.now()
      );
      Like like12 = new Like(
              14L,
              11L,
              Instant.now()
      );
      Like like13 = new Like(
              14L,
              14L,
              Instant.now()
      );
      Like like14 = new Like(
              10L,
              14L,
              Instant.now()
      );
      Like like15 = new Like(
              8L,
              14L,
              Instant.now()
      );
      Like like16 = new Like(
              11L,
              15L,
              Instant.now()
      );
      Like like17 = new Like(
              10L,
              15L,
              Instant.now()
      );
      Like like18 = new Like(
              8L,
              15L,
              Instant.now()
      );
      Like like19 = new Like(
              14L,
              13L,
              Instant.now()
      );
      Like like20 = new Like(
              8L,
              13L,
              Instant.now()
      );
      Like like21 = new Like(
              11L,
              13L,
              Instant.now()
      );

      lrepository.saveAll(
              List.of(like1,
                      like2,
                      like3,
                      like4,
                      like5,
                      like6,
                      like7,
                      like8,
                      like9,
                      like10,
                      like11,
                      like12,
                      like13,
                      like14,
                      like15,
                      like16,
                      like17,
                      like18,
                      like19,
                      like20,
                      like21
              )
      );
    };
  }
}
