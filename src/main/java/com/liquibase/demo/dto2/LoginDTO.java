package com.liquibase.demo.dto2;

import com.liquibase.demo.model.Comment;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private List<Comment> comment;
    private List<Post> post;
    private List<Reaction> reaction;
}
