package hello.velog.repository;

import hello.velog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByUserIdAndPrivacySettingAndTemporarySetting(Long userId, boolean privacySetting, boolean temporarySetting);
    List<Post> findByUserIdAndTemporarySetting(Long userId, boolean temporarySetting);
}