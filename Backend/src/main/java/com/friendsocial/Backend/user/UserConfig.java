package com.friendsocial.Backend.user;

import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.comment.CommentRepository;
import com.friendsocial.Backend.elasticsearch.ElasticsearchUserService;
import com.friendsocial.Backend.friend.Friend;
import com.friendsocial.Backend.friend.FriendRepository;
import com.friendsocial.Backend.like.Like;
import com.friendsocial.Backend.like.LikeRepository;
import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
public class UserConfig {
  @Bean(name = "userCommandLineRunner")
  CommandLineRunner userConfig(
          UserRepository repository,
          ElasticsearchUserService elasticsearchUserService) {
    return args -> {
      Instant now = Instant.now();
      User connor = new User(
              "c@gmail.com",
              "connorfitz429",
              "svsb",
              LocalDate.of(1999, 4, 29),
              "Connor",
              "Fitzpatrick",
              "cf.png",
              "MY APP",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );
      User alex = new User(
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
      User kieran = new User(
              "kieran@gmail.com",
              "kieran620",
              "bbbbbbb",
              LocalDate.of(1995, 06, 20),
              "Kieran",
              "Thomas",
              "yankees.png",
              "Dummy data account",
              now.minusSeconds(29 * 25 * 60 * 60),
              Role.USER
      );
      User peyton = new User(
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
      User bob = new User(
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

      List<User> users = userRepository.saveAll(
              List.of(connor, alex, kieran, peyton, bob)
      );

//      repository.saveAll(
//              List.of(connor, alex, kieran, peyton, bob)
//      );

      System.out.println(users);

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
                      "that this looks okay! If it doesn't I'll be up all night. figure out how to send pictures next " +
                      "as well as figure out image-post-components.",
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
              "painting.png"
      );
      Post bob2 = new Post(
              5L,
              "Image",
              "This post took 30 minutes. You can do it too!",
              Instant.parse("2023-08-10T11:30:15Z"),
              "painting2.jpeg"
      );


      drepository.saveAll(
              List.of(connorsFirst, bob1, connorsSecond, peyton1, alexFirst, alexSecond, bob2, connorsThird, connorsFourth, connorsFifth)
      );
    };
  }

  @Bean
  CommandLineRunner commandLineRunner2(
          FriendRepository frepository) {
    return args -> {
      Instant now = Instant.now();

      Long userId = 1L;
      Long friendId = 2L;
      Long friend2Id = 3L;
      Long friend4Id = 4L;
      Optional<User> userOptional = userRepository.findById(userId);
      Optional<User> userOptional2 = userRepository.findById(friendId);
      Optional<User> userOptional3 = userRepository.findById(friend2Id);
      Optional<User> userOptional4 = userRepository.findById(friend4Id);
      User connor = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
      User alex = userOptional2.orElseThrow(() -> new RuntimeException("User not found"));
      User kieran = userOptional3.orElseThrow(() -> new RuntimeException("User not found"));
      User peyton = userOptional4.orElseThrow(() -> new RuntimeException("User not found"));

      Friend connorfriend = new Friend(
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
      Friend bobfriend1 = new Friend(
              peyton,
              connor,
              LocalDate.now()
      );
      Friend bobfriend2 = new Friend(
              peyton,
              alex,
              LocalDate.now()
      );

      frepository.saveAll(
              List.of(connorfriend, connorfriend2, connorfriend3, bobfriend1, bobfriend2)
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
      Comment comment6 = new Comment(
              1L,
              1L,
              "This is the Fourth comment",
              Instant.now()
      );
      Comment comment7 = new Comment(
              1L,
              1L,
              "This is the Fifth comment",
              Instant.now()
      );
      Comment comment8 = new Comment(
              1L,
              1L,
              "This is the Sixth comment",
              Instant.now()
      );
      Comment comment9 = new Comment(
              1L,
              1L,
              "This is the Seventh comment",
              Instant.now()
      );
      Comment comment10 = new Comment(
              3L,
              1L,
              "This is the Eighth comment",
              Instant.now()
      );
      Comment comment11 = new Comment(
              2L,
              1L,
              "This is the Ninth comment",
              Instant.now()
      );
      Comment comment12 = new Comment(
              1L,
              1L,
              "This is the Tenth comment",
              Instant.now()
      );
      Comment comment13 = new Comment(
              1L,
              1L,
              "This is the Eleventh comment",
              Instant.now()
      );
      Comment comment14 = new Comment(
              3L,
              1L,
              "This is the Twelvth comment",
              Instant.now()
      );
      Comment comment15 = new Comment(
              2L,
              1L,
              "This is the Thirtheenth comment",
              Instant.now()
      );
      Comment comment16 = new Comment(
              1L,
              1L,
              "This is the Fourtheenth comment",
              Instant.now()
      );
      Comment comment17 = new Comment(
              4L,
              1L,
              "This is the Fifteenth comment",
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
                      comment9,
                      comment10,
                      comment11,
                      comment12,
                      comment13,
                      comment14,
                      comment15,
                      comment16,
                      comment17,
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

      lrepository.saveAll(
              List.of(like1,
                      like2,
                      like3,
                      like4,
                      like5,
                      like6,
                      like7,
                      like8,
                      like9
              )
      );
    };
  }
}
