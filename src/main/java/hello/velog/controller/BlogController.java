package hello.velog.controller;

import hello.velog.domain.*;
import hello.velog.service.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vlog")
@RequiredArgsConstructor
public class BlogController {
    private final UserService userService;
    private final PostService postService;
    private final BlogService blogService;
    private final SeriesService seriesService;
    private final MarkdownService markdownService;
    private final FollowService followService;

    @GetMapping
    public String home(Model model, HttpServletRequest request) {
        User user = userService.getSessionUser(request);
        model.addAttribute("sessionUser", user);
        return "redirect:/vlog/trending";
    }

    @GetMapping("/trending")
    public String trending(Model model) {
        List<Post> posts = postService.getTrendingPosts();
        Map<Long, String> postUsernames = posts.stream()
                .collect(Collectors.toMap(Post::getId, post -> postService.getUsernameByUserId(post.getUserId())));
        model.addAttribute("posts", posts);
        model.addAttribute("postUsernames", postUsernames);
        model.addAttribute("activeTab", "trending");
        return "home";
    }

    @GetMapping("/latest")
    public String latest(Model model) {
        List<Post> posts = postService.getLatestPosts();
        Map<Long, String> postUsernames = posts.stream()
                .collect(Collectors.toMap(Post::getId, post -> postService.getUsernameByUserId(post.getUserId())));
        model.addAttribute("posts", posts);
        model.addAttribute("postUsernames", postUsernames);
        model.addAttribute("activeTab", "latest");
        return "home";
    }

    @GetMapping("/feed")
    public String feed(Model model, HttpServletRequest request) {
        User user = userService.getSessionUser(request);
        if (user == null) {
            model.addAttribute("requiresLogin", true);
        } else {
            List<Follow> follows = followService.findByFollower(user);
            if (follows.isEmpty()) {
                model.addAttribute("noFeeds", true);
            } else {
                List<Long> followedUserIds = follows.stream()
                        .map(follow -> follow.getFollowee().getId())
                        .collect(Collectors.toList());
                List<Post> posts = postService.getPostsFromFollowedUsers(followedUserIds);
                Map<Long, String> postUsernames = posts.stream()
                        .collect(Collectors.toMap(Post::getId, post -> postService.getUsernameByUserId(post.getUserId())));
                model.addAttribute("posts", posts);
                model.addAttribute("postUsernames", postUsernames);
            }
        }
        model.addAttribute("activeTab", "feed");
        return "home";
    }

    @GetMapping("/myblog/@{username}")
    public String myBlog(@PathVariable String username, HttpServletRequest request, Model model) {
        return "redirect:/vlog/myblog/@" + username + "/posts";
    }

    @GetMapping("/myblog/@{username}/posts")
    public String myBlogPosts(@PathVariable String username, HttpServletRequest request, Model model) {
        User user = userService.getSessionUser(request);
        User blogOwner = userService.findByUsername(username);

        List<Post> posts;
        if (user != null && user.getId().equals(blogOwner.getId())) {
            posts = postService.getUserPosts(blogOwner.getId(), null, false);
        } else {
            posts = postService.getUserPosts(blogOwner.getId(), false, false);
        }

        long followerCount = followService.getFollowerCount(blogOwner.getId());
        long followingCount = followService.getFollowingCount(blogOwner.getId());

        Blog blog = blogService.findBlogByUserId(blogOwner.getId());
        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("blogOwner", blogOwner);
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);
        model.addAttribute("activeTab", "posts");
        model.addAttribute("isBlogOwner", user != null && user.getId().equals(blogOwner.getId()));
        model.addAttribute("sessionUser", user); // 세션 사용자 추가
        return "myblog";
    }

    @GetMapping("/myblog/@{username}/series")
    public String myBlogSeries(@PathVariable String username, HttpServletRequest request, Model model) {
        User user = userService.getSessionUser(request);
        User blogOwner = userService.findByUsername(username);

        List<Series> seriesList = seriesService.findAllSeriesByBlogId(blogOwner.getBlog().getId());
        List<Map<String, Object>> seriesWithThumbnails = new ArrayList<>();

        for (Series series : seriesList) {
            Post firstPost = seriesService.findFirstPostBySeriesId(series.getId());
            String thumbnailImage = firstPost != null ? firstPost.getThumbnailImage() : "/images/post/default-image.png";
            Map<String, Object> seriesMap = new HashMap<>();
            seriesMap.put("series", series);
            seriesMap.put("thumbnailImage", thumbnailImage);
            seriesWithThumbnails.add(seriesMap);
        }

        long followerCount = followService.getFollowerCount(blogOwner.getId());
        long followingCount = followService.getFollowingCount(blogOwner.getId());

        Blog blog = blogService.findBlogByUserId(blogOwner.getId());
        model.addAttribute("blog", blog);
        model.addAttribute("seriesList", seriesWithThumbnails);
        model.addAttribute("blogOwner", blogOwner);
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);
        model.addAttribute("activeTab", "series");
        model.addAttribute("isBlogOwner", user != null && user.getId().equals(blogOwner.getId()));
        model.addAttribute("sessionUser", user); // 세션 사용자 추가
        return "myblog";
    }

    @GetMapping("/myblog/@{username}/about")
    public String getBlogAbout(@PathVariable String username, Model model, HttpServletRequest request) {
        Blog blog = blogService.findBlogByUsername(username);
        User user = userService.getSessionUser(request);
        boolean isBlogOwner = user != null && user.getUsername().equals(username);

        long followerCount = followService.getFollowerCount(blog.getUser().getId());
        long followingCount = followService.getFollowingCount(blog.getUser().getId());

        model.addAttribute("blog", blog);
        model.addAttribute("blogOwner", blog.getUser());
        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("username", username); // username 추가
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);
        model.addAttribute("activeTab", "about");
        model.addAttribute("sessionUser", user); // 세션 사용자 추가
        return "myblog";
    }

    @PostMapping("/myblog/@{username}/about/update")
    public String updateBlogIntro(@PathVariable String username, HttpServletRequest request, @RequestParam String intro) {
        User user = userService.getSessionUser(request);
        User blogOwner = userService.findByUsername(username);

        if (user == null || !user.getId().equals(blogOwner.getId())) {
            return "redirect:/vlog/myblog/@" + username + "/about";
        }

        Blog blog = blogService.findBlogByUsername(username);
        blog.setIntro(intro);
        blogService.saveBlog(blog);
        return "redirect:/vlog/myblog/@" + username + "/about";
    }

    @GetMapping("/myblog/@{username}/{id}")
    public String getPost(@PathVariable String username, @PathVariable Long id, HttpServletRequest request, Model model) {
        User user = userService.getSessionUser(request);

        Post post = postService.getPostById(id);
        User blogOwner = userService.findById(post.getUserId());
        Blog blog = blogService.findBlogByUserId(blogOwner.getId());

        // URL의 사용자명과 게시물 소유자가 일치하지 않는 경우 리다이렉트
        if (!blogOwner.getUsername().equals(username)) {
            return "redirect:/vlog/myblog/@" + username; // 일치하지 않으면 리다이렉트
        }

        if (post.getPrivacySetting() || post.getTemporarySetting()) {
            if (user == null || !user.getId().equals(blogOwner.getId())) {
                return "redirect:/vlog/myblog/@" + blogOwner.getUsername();
            }
        }

        String htmlContent = markdownService.convertMarkdownToHtml(post.getContent());

        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("blog", blog);
        model.addAttribute("blogOwner", blogOwner);
        model.addAttribute("htmlContent", htmlContent);
        model.addAttribute("sessionUser", user); // 세션 사용자 추가

        return "postDetail";
    }

    @GetMapping("/saves")
    public String save(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getSessionUser(request);

        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMSG", "로그인이 필요한 기능입니다.");
            return "redirect:/vlog/loginform";
        }

        List<Post> posts = postService.getUserPosts(user.getId(), null, true);
        model.addAttribute("posts", posts);
        return "saves";
    }

    @GetMapping("/liked")
    public String liked(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getSessionUser(request);

        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMSG", "로그인이 필요한 기능입니다.");
            return "redirect:/vlog/loginform";
        }

        List<Post> likedPosts = postService.getLikedPosts(user.getId());
        Map<Long, String> postUsernames = likedPosts.stream()
                .collect(Collectors.toMap(Post::getId, post -> postService.getUsernameByUserId(post.getUserId())));

        model.addAttribute("posts", likedPosts);
        model.addAttribute("postUsernames", postUsernames);
        return "liked";
    }
}
