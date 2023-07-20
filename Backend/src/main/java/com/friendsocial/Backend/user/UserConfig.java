package com.friendsocial.Backend.user;

import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.comment.CommentRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
public class UserConfig {
  @Bean(name = "userCommandLineRunner")
  CommandLineRunner userConfig(
          UserRepository repository) {
    return args -> {
      Instant now = Instant.now();
      User connor = new User(
              "c@gmail.com",
              "connorfitz429",
              "svsb",
              LocalDate.of(1999, 4, 29),
              "Connor",
              "Fitzpatrick",
              "https://i.imgur.com/PCXFcu2.jpg",
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
              "https://i.imgur.com/Vdcn5Yj.jpg",
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
              "https://i.imgur.com/79OV5FI.png",
              "Dummy data account",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );
      User peyton = new User(
              "peyton@gmail.com",
              "PeytonManning",
              "bbbbsdfbbb",
              LocalDate.of(1979, 03, 12),
              "Peyton",
              "Manning",
              "https://i.imgur.com/bZaoFOU.jpg",
              "Not affiliated with real Peyton Manning",
              now.minusSeconds(30 * 24 * 60 * 60),
              Role.USER
      );

      repository.saveAll(
              List.of(connor, alex, kieran, peyton)
      );
    };
  }


  @Autowired
  private UserRepository userRepository;

  @Bean
  CommandLineRunner commandLineRunner1(
          PostRepository drepository) {
    return args -> {
      Long userId = 1L;
      Optional<User> userOptional = userRepository.findById(userId);
      User p = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
      Post connorsFirst = new Post(
              userId,
              "Text",
              "This is my first post.",
              Instant.now(),
              ""
      );
      Post connorsSecond = new Post(
              userId,
              "Text",
              "This is my second post.",
              Instant.now(),
              ""
      );
      Post alexFirst = new Post(
              2L,
              "Text",
              "Ayo I am Alex.",
              Instant.now(),
              ""
      );
      Post alexSecond = new Post(
              3L,
              "Image",
              "This is a selfie of me, Connor.",
              Instant.now(),
              "../imageStore/image3"
      );
      Post connorsThird = new Post(
              userId,
              "Text",
              "This is long post to check and see how the information I put down is formatted within the Angular component. I hope" +
                      "that this looks okay! If it doesn't I'll be up all night. figure out how to send pictures next " +
                      "as well as figure out image-post-components.",
              Instant.now(),
              ""
      );
      Post connorsFourth = new Post(
              3L,
              "Text",
              "I'm hungry.",
              Instant.now(),
              ""
      );
      Post connorsFifth = new Post(
              2L,
              "Image",
              "Just more test data.",
              Instant.now(),
              "../imageStore/image3"
      );
      Post peyton1 = new Post(
              4L,
              "Image",
              "Heres a post from my new user.",
              Instant.now(),
              "../imageStore/image3"
      );

      drepository.saveAll(
              List.of(connorsFirst, connorsSecond, peyton1, alexFirst, alexSecond, connorsThird, connorsFourth, connorsFifth)
      );
    };
  }

  @Autowired
  private UserRepository friendRepository;

  @Bean
  CommandLineRunner commandLineRunner2(
          FriendRepository frepository) {
    return args -> {
      Long userId = 1L;
      Long friendId = 2L;
      Long friend2Id = 3L;
      Optional<User> userOptional = userRepository.findById(userId);
      Optional<User> userOptional2 = userRepository.findById(friendId);
      Optional<User> userOptional3 = userRepository.findById(friend2Id);
      User p = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
      User f = userOptional2.orElseThrow(() -> new RuntimeException("User not found"));
      User f2 = userOptional3.orElseThrow(() -> new RuntimeException("User not found"));
      Friend connorfriend = new Friend(
              1L,
              2L,
              LocalDate.now()
      );
      Friend connorfriend2 = new Friend(
              1L,
              3L,
              LocalDate.now()
      );

      frepository.saveAll(
              List.of(connorfriend, connorfriend2)
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
                      comment17
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

      lrepository.saveAll(
              List.of(like1,
                      like2,
                      like3,
                      like4,
                      like5,
                      like6,
                      like7,
                      like8
              )
      );
    };
  }
}
