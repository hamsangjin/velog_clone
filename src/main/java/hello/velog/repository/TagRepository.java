package hello.velog.repository;

import hello.velog.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameAndBlogId(String name, Long blogId);
    List<Tag> findByPostsId(Long postId);
}