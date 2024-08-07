package hello.velog.repository;

import hello.velog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findByUserId(Long userId);

    void deleteByUserId(Long userId);
}