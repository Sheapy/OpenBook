package models;

import java.util.*;
import javax.persistence.*;

import controllers.Security;

import play.db.jpa.*;

@Entity
public class Post extends Status {

  public String title;

  @Lob
  public String text;

  public Post(User author, String title, String content) {
    super(author, content);
    this.title = title;
    this.text = content;
  }

  public Post previous() {
    return Post.find("author = ? AND date < ? order by date desc",
                     this.author, this.createdAt).first();
  }

  public Post next() {
    return Post.find("date > ? order by date asc", this.createdAt)
      .first();
  }

  public boolean byCurrentUser() {
    return author.email.equals( Security.connected() );
  }
}
