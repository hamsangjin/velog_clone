<div align="center">
<h1> 📚V-log </h1>
Spring Boot를 이용한 웹 애플리케이션 개발 프로젝트로, <a href="https://velog.io/">Velog</a>와 유사한 플랫폼을 직접 개발합니다.
</div>

<br>

# 목차
  - [개요](#개요)
  - [기술 스택](#기술-스택)
  - [요구사항](#요구사항)
  - [도메인 설계](#도메인-설계)
  - [시연 영상](#시연-영상)
  - [프로젝트를 마치며](#프로젝트를-마치며)

<br>
<br>

# 개요
- 프로젝트 이름: V-log
- 프로젝트 지속기간: 2024. 06. 18 ~ 2024. 07. 19
- 개인 프로젝트

<br>
<br>

# 기술 스택
| 분류 | 기술 스택 |
|:---:|:---:|
| 개발 언어 | ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E) ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white) |
| 프레임워크/라이브러리 | ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) ![Sprint Secutiry](https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) <!-- ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)--> ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white) | 
| 데이터베이스 | ![Mysql](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white) |
| etc | ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white) ![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white) |
<!-- | 테스팅 | ![Junit](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) | -->
<!-- | 배포 | ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white) | -->

<br>
<br>

# 요구사항
<details><summary> 📚 <b>블로그</b></summary>

        ✅ 회원 1명은 1개의 Blog를 가질 수 있다.
        ✅ 회원가입, 로그인 기능을 제공합니다.
        ✅ http://vlog는 사용자들이 작성한 최신 글, 인기 글을 볼 수 있습니다.(정렬방식에 따라 최신, 인기글을 볼 수 있습니다.)
        ✅ http://도메인에서 보여주는 블로그 글에는 제목, 내용일부, 작성자사진, 작성자아이디, 좋아요 수가 보여집니다.
        ✅ http://vlog/@carami 는 carami 아이디의 사용자의 블로그 페이지입니다.
        ✅ http://vlog/@아이디 를 가면 좌측에 tag 목록이 나오고 tag 에 글의 수가 보여집니다.
        ✅ http://vlog/@아이디 를 가면 글 목록, 시리즈 목록, 소개를 볼 수 있습니다.
</details>

<details><summary> 📖 <b>블로그 포스트</b></summary>

        ✅ 사용자는 임시 글을 작성할 수 있습니다.
        ✅ 사용자는 글을 작성하자 마자 즉시 출간할 수 있습니다.
        ✅ 사용자는 임시 글 목록을 볼 수 있습니다.
        ✅ 글을 작성할 때 이미지, URL 등을 포함시킬 수 있습니다.
        ✅ 글은 제목, 내용, 태그들을 작성할 수 있고 자동으로 작성일이 지정됩니다. (제목, 내용은 필수 입니다.)
        ✅ 임시 글 목록에서 임시 글을 삭제할 수 있습니다.
        ✅ 임시 글은 수정할 수 있습니다. 수정 후 바로 출간할 수 있습니다
</details>

<details><summary> 👍🏻 <b>좋아요, 팔로우</b></summary>
  
        ✅ 사용자는 블로그에서 “좋아하기”를 할 수 있습니다.
        ✅ 좋아하기를 선택한 블로그 글들만 모아서 볼 수 있습니다.
        ❌ 사용자가 읽은 글들만 모아서 볼 수 있습니다.
        ✅ 팔로우 한 사용자 목록을 볼 수 있습니다. 언팔로우 할 수 있습니다
</details>  

<details><summary> 🔒 <b>개인정보</b></summary>
  
        ✅ 사용자는 본인의 프로필 이미지를 등록할 수 있습니다.
        ✅ 본인이 등록한 프로필을 삭제할 수 있습니다.
        ✅ 블로그 제목을 설정할 수 있습니다. 설정하지 않으면 기본적으로 사용자 아이디가 됩니다.
        ✅ 이메일 주소를 변경할 수 있습니다. (회원 가입시 등록할 수 있습니다.)
        ❌ 이메일 수신 설정을 변경할 수 있습니다. (댓글 알림, 업데이트 소식 알림)
        ✅ 회원 탈퇴 기능을 제공합니다
</details> 
        
<details><summary> 📝 <b>출간</b></summary>
  
        ✅ 임시 글을 출간하거나, 바로 출간하기를 하게 되면 포스트 미리보기 이미지를 등록할 수 있습니다.
        ✅ 임시 글을 출간하거나, 바로 출간하기를 하게 될 때 글의 제목과 내용을 일부 보여줍니다. 전체공개로 올릴지 비공개로 올릴지를 결정할 수 있습니다.
        ✅ 해당 글의 URL 은 /@아이디/posts/제목이 됩니다. 제목은 URLEncoding 으로 인코딩 되어 있어야 합니다
        ✅ 임시 글을 출간하거나, 바로 출간하기를 하게 될 때 시리즈에 추가할 수 있습니다
</details>
        
<details><summary> 🔍 <b>블로그 글 보기</b></summary>
  
        ✅ 블로그 글 보기 기능을 제공합니다.
        ✅ 비공개 글의 경우 비공개 표시를 제공합니다.
        ✅ 내 글이 아닌 경우 좋아요를 할 수 있습니다.
        ❌ 내 글의 경우 통계정보를 볼 수 있습니다.
        ✅ 내 글의 경우 수정할 수 있습니다.
        ✅ 내 글의 경우 삭제할 수 있습니다.
        ✅ 블로그를 작성한 사람의 프로필 이미지와 아이디를 하단에 보여줍니다.
        ✅ 다른 사람의 글의 경우 팔로우를 할 수 있습니다.
        ❌ 해당 사용자의 이전 블로그글, 이후 블로그글에 대한 링크를 볼 수 있습니다
</details>

<details><summary> 💬 <b>댓글</b></summary>
  
        ✅ 블로그에 사용자는 댓글을 작성할 수 있습니다.
        ✅ 블로그 글보기를 하면 댓글의 수가 표시됩니다.
        ✅ 댓글에 답글을 작성할 수 있습니다.
        ✅ 댓글을 삭제할 수 있습니다.
</details>

<details><summary> ⚙️ <b>관리자</b></summary>
  
        ❌ 관리자 페이지로 가면 모든 포스팅된 글 목록을 볼 수 있습니다.
        ❌ 관리자는 어떤 글이든 삭제할 수 있습니다
</details>

<br>
<br>

# 도메인 설계
<img width="826" alt="스크린샷 2024-07-23 23 02 42" src="https://github.com/user-attachments/assets/3b50340f-7018-494f-bc74-b003437f492a">


<br>
<br>

# 시연 영상
아래 그림을 클릭하면 유튜브로 연결됩니다.

<a href="https://youtu.be/s40xUHc08TI"> 
  <img width="1498" alt="velog 클론 코딩 썸네일" src="https://github.com/user-attachments/assets/b0175278-7ab5-4fa3-8141-78e9c9685606">
</a>

<br>
<br>

# 프로젝트를 마치며

## 배운 점
- 도메인 설계를 하고, 개발을 하면서 설계에 대한 중요성을 알게 되었다.
  - 진짜 설계만 제대로 해도 개발 시간이 많이 단축될 거 같다.(요구사항 분석 및 API 명세서도 !!)
- 개인 프로젝트인 만큼 혼자 모든 걸 구현하다보니, 머리로만 알고있던 MVC 패턴을 직접 구현하며 더욱 더 이해할 수 있었다.
- 컨트롤러와 서비스 로직의 분리를 위한 리팩토링을 하다보니, 어느 코드가 서비스에 들어갈지, 컨트롤러에 들어갈지 알게 되었다.


## 고쳐야할 점
- 기능 하나하나를 구현하기 급급해 테스트 코드를 작성하지 않으면서 개발하여, 디버깅을 할 때 어디서 문제가 났는지 찾기 어려웠다.
- Chat gpt에 대한 의존을 하게 돼서, 동작만 하면 넘어가버린 적이 있다.
  - ps. 새로운 기능을 구현할 때 틀을 잡는 정도로 사용하니 더욱 유용하게 사용할 수 있었다.
- Controller에서 Client한테 데이터를 넘길 때 DTO를 사용하지 않고, 그냥 도메인을 넘겨버린 점을 고쳐야한다.

## 아쉬운 점
- 프로젝트 초반까지는 Spring Security를 배우지 않고 만들다가, 중간에 배워 적용하려다보니 Spring Security의 폼 로그인만 사용했다.
  - JWT, OAuth 등을 추가해보고 싶다
- 체계적인 코드 리뷰를 받지 못해서 내가 올바르게 개발하고 있는가에 대한 의구심이 계속 들었다.
  - 패키지 아키텍처, 클린 코드 등등..
- 개인 프로젝트라고 생각해 git 브랜치 전략을 사용하지 않았다.
