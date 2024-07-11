package hello.velog.controller;

import hello.velog.domain.*;
import hello.velog.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/vlog")
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final SeriesService seriesService;

    @GetMapping("/newpost")
    public String newPostForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMSG", "로그인이 필요한 기능입니다.");
            return "redirect:/vlog/loginform";
        }

        model.addAttribute("user", user);
        model.addAttribute("post", new Post());
        model.addAttribute("seriesList", seriesService.findAllSeries());
        return "newpost";
    }

    @PostMapping("/newpost")
    public String createNewPost(
            @ModelAttribute Post post,
            @RequestParam("thumbnailImageFile") MultipartFile thumbnailImageFile,
            @RequestParam(value = "seriesId", required = false) Long seriesId,
            @RequestParam(value = "newSeries", required = false) String newSeries,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMSG", "로그인이 필요한 기능입니다.");
            return "redirect:/vlog/loginform";
        }

        if (newSeries != null && !newSeries.trim().isEmpty()) {
            Series series = new Series();
            series.setTitle(newSeries);
            series.setBlog(user.getBlog());
            seriesService.saveSeries(series);
            post.setSeries(series);
        } else if (seriesId != null) {
            Series series = seriesService.findById(seriesId);
            post.setSeries(series);
        }

        String thumbnailImagePath = "/images/post/default-image.png";
        if (!thumbnailImageFile.isEmpty()) {
            try {
                String uploadDir = "/Users/sangjin/Desktop/likelion/velog/src/main/resources/static/images/post/";
                String uuid = UUID.randomUUID().toString();
                String originalFilename = thumbnailImageFile.getOriginalFilename();
                String storedFilename = uuid + "_" + originalFilename;

                File destFile = new File(uploadDir + storedFilename);
                thumbnailImageFile.transferTo(destFile);

                thumbnailImagePath = "/images/post/" + storedFilename;
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMSG", "썸네일 이미지 업로드 중 오류가 발생했습니다.");
                return "redirect:/vlog/newpost";
            }
        }

        post.setUserId(user.getId());
        post.setThumbnailImage(thumbnailImagePath);

        postService.savePost(post);
        redirectAttributes.addFlashAttribute("message", "글이 성공적으로 작성되었습니다.");
        return "redirect:/vlog/myblog/@" + user.getUsername();
    }
}