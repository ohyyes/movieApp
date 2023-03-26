# movieApp

MVTI 🎬
===========================================
> - MBTI 기반 영화 추천 및 영화 감상 기록 어플 <br/><br/> 
> - 2학년 2학기 5인 프로젝트 (2022년 11월 ~ 12월) <br/><br/>
> - 시연영상 링크 (https://youtu.be/fsD2ozhgTb0) <br/><br/>

<br/><br/>

## Overview
1. [프로젝트 목표](https://github.com/ohyyes/movieApp/blob/main/README.md#1-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%AA%A9%ED%91%9C)
2. [앱 소개](https://github.com/ohyyes/movieApp/blob/main/README.md#2-%EC%95%B1-%EC%86%8C%EA%B0%9C)
3. [기능 구현](https://github.com/ohyyes/movieApp/blob/main/README.md#3-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84)
4. [문제 해결](https://github.com/ohyyes/movieApp/blob/main/README.md#4-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)
5. [팀원](https://github.com/ohyyes/movieApp/blob/main/README.md#5-%ED%8C%80%EC%9B%90)

<br/><br/>

## 1. 프로젝트 목표
* 일별 박스오피스 및 영화 검색.
* 나의 성격 유형-MBTI 에 맞는 영화 추천.  
* 본 영화에 대한 내 생각과 느낌을 그때그때 기록.  
* 내가 본 영화들과 그에 대한 나의 감상평을 한눈에 !!
> #### 이러한 기능을 갖춘 어플을 만들어 직접 사용하자!
> #### 깃과 여러 협업툴 사용법 숙지 등 협업 능력 향상
> #### 모바일 프로그래밍 경험이 없는 팀원들로 구성 -> 전반적인 프로그래밍 경험 쌓기

<br/><br/>




<img src="" width="19%"/>




## 2. 앱 소개 
### 기본 정보
* 실행 환경: 모바일 어플리케이션 (안드로이드)
* 개발 환경: 안드로이드 스튜디오 SDK 12s
* 사용 언어: JAVA

<br/><br/>

### 주요 화면 및 기능
#### 1) 홈 화면 - 박스오피스, MBTI 기반 영화 추천
> - 박스오피스 - 상단의 일별 박스오피스는 현재 상영 중인 영화들을 일간 관객수 기준으로 1위부터 5위까지 순서대로 보여준다. <br/>
> - MBTI 기반 영화 추천 - 하단에는 사용자의 mbti를 기반으로 20개의 추천 영화를 보여준다. <br/>

<img src="https://user-images.githubusercontent.com/84231143/227759812-38af1042-0a5a-4a3d-8b5b-1ffa96d94357.JPG" width="200" height="400"/> </br>

</br>

#### 2) 영화 검색 화면
> - 상단의 검색창에 검색어를 입력해 돋보기 버튼을 누르면 영화 제목에 해당 keyword를 포함하는 영화들을 검색한다.
> - 영화 포스터, 평점, 장르, 개봉일 등의 정보를 확인해 원하던 영화를 찾아 클릭하면 해당 영화 상세 페이지로 이동한다. <br/>

<img src="https://user-images.githubusercontent.com/84231143/227759838-799b104a-9160-457c-9e1a-0443c66c86ef.JPG" width="20%"/> <img src="https://user-images.githubusercontent.com/84231143/227759841-8b9788b9-4d16-44e6-8b45-25d569c77829.JPG" width="20%"/> 

<br/><br/><br/>

</br>

#### 3) 영화 상세 화면
> 줄거리, 감독, 출연 배우 등 더 상세한 정보를 볼 수 있다. <br/>
> 중앙의 [내가 쓴 리뷰 보러가기 !] 버튼을 클릭해 이전에 그 영화로 작성한 감상평이 없다면, <br/>
> “작성한 리뷰가 없네요!” 메세지를 띄운 팝업창이 뜨고 [작성하기] 버튼을 눌러 감상평을 작성하는 페이지로 이동 리뷰작성 페이지로 이동하고,<br/>

<img src="https://user-images.githubusercontent.com/84231143/227760500-8adaeaf7-0967-4265-81a1-1293ecdf3539.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760507-1d724910-e99d-43a3-b7d0-d63351452c6a.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760508-7575bbcd-f7c4-4edc-8cdb-865d5de8d7d7.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760511-0b89fffb-4584-4fd9-b008-41421127ced7.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760513-7f5ad873-beb2-45e1-8b28-de65c92289a7.JPG" width="19%"/> 
<br/><br/><br/>

> 기존에 작성한 리뷰가 있었다면 저장된 해당 영화의 감상평 상세 페이지로 이동한다.</br>
> 영화 상세 페이지에서는 파이어베이스와 연동하여 사용자가 리뷰를 작성하였는지 먼저 확인한다. <br/>
> 사용자가 작성했을 경우, 평점과 글을 파이어베이스에서 받아 setText로 넘겨주고 작성하지 않았을 경우 값을 넘겨주지 않도록 설정했다. </br>

<img src="https://user-images.githubusercontent.com/84231143/227759844-e2620cf9-1656-494e-afeb-17205a787cba.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227759851-afa6d72a-74ec-450c-9933-a03a9c16b282.JPG" width="19%"/>
</br>





</br><br/><br/>

#### 3) 감상평 목록 및 상세 화면
>감상평 목록 페이지에선 사용자가 작성한 감상평들을 보여준다. 영화 포스터 이미지, 제목, 내 평점, 개봉연도, 내가 작성한 감상평 일부분을 확인할 수 있다. 감상평을 클릭하면 해당 감상평 상세 페이지로 이동한다.<br/>
>감상평 상세 페이지에서는 해당 영화에 나만의 평점과 감상평을 남길 수 있다. 상단에 [수정] 버튼을 클릭하면 작성모드로 바뀐다. 작성모드에서 별점을 눌러 나의 별점을 남기고, 감상평을 입력한 후에 상단에 [등록] 버튼을 누르면 감상평이 저장되고, 작성된 감상평을 뷰어모드로 전환된다. </br>

<img src="https://user-images.githubusercontent.com/84231143/227760898-a2a165b6-787b-49fe-9d2d-8d98f28d739c.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760900-999e3538-b151-466b-8f88-6342f5ca33c6.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760901-f4414fe9-12ed-4fbd-9961-f1016dc0c020.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227760902-04404edc-b63c-4bc5-9deb-c4c07e8a1427.JPG" width="19%"/>


</br></br>

#### 4) 마이페이지 - 정보 수정, 감상평 목록
> 사용자의 닉네임과 MBTI 정보를 보여준다.</br> 
> 마이페이지의 하단에는 사용자가 감상평을 작성한 영화들을 좌우로 스크롤하며 볼 수 있다. 원하는 영화를 클릭하면, 해당 영화로 작성한 감상평 상세 페이지로 이동한다. </br>

<img src="https://user-images.githubusercontent.com/84231143/227760903-5bc74bcf-76ef-43c3-aff6-c5d62af99f73.JPG" width="19%"/>
<br/><br/>

> [내 프로필 수정하기] 버튼을 누르면 닉네임과 MBTI를 수정할 수 있는 페이지로 이동한다.<br/>

<img src="https://user-images.githubusercontent.com/84231143/227760903-5bc74bcf-76ef-43c3-aff6-c5d62af99f73.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227760904-b70aa3a1-6a1d-4cb4-ae8e-a28dce2b8efe.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227760905-0bc98e2f-e9ec-4ae8-bf23-8e55da22bf15.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227760908-871eab6e-df00-4b0d-be6e-60eb2dcd7cb6.JPG" width="19%"/>


</br>

> 마이페이지의 하단 오른쪽의 [더보기] 버튼을 누르면 사용자가 작성한 전체 감상평 목록으로 이동한다.  </br>

<img src="https://user-images.githubusercontent.com/84231143/227760909-a988d4ee-1e24-4874-a336-faa37d299b9c.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227761805-a14ced2e-f334-4d95-a811-c6da0422448e.JPG" width="19%"/>

</br>

## 3. 기능 구현

1) 스플래쉬 화면 </br>
**front** </br>
- 어플을 처음 실행했을 때 스플래쉬를 통해 애니메이션을 추가했다. </br>
<img src="https://user-images.githubusercontent.com/84231143/227761100-3cb5884f-8495-4801-96c5-1e5e8542e130.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227761101-9bdfe0e8-695b-47a0-b354-dd81d8a02706.JPG" width="19%"/>

</br>

2) 회원가입 화면 </br>
**front** </br>
- 로그인 화면과 비슷하게 각 입력값과 MBTI 토글을 하나라도 입력하지 않으면 버튼이 눌리지 않게 구현했다. </br>
- 모든 입력값이 있다면 버튼이 활성화된 이미지로 변경된다. </br>
- 비밀번호는  toggle을 설정하여 비밀번호가 안보이는 것을 기본값으로 구현했다.</br>

<img src="https://user-images.githubusercontent.com/84231143/227761347-73534fca-a315-4628-8161-067baca1749e.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227761348-f0b856e6-cad1-408b-b393-0e6cef2da174.JPG" width="19%"/>
</br>
<img src="https://user-images.githubusercontent.com/84231143/227761350-30fac2c5-9953-41b0-92e7-21144cba502f.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227761351-a74d9b47-005a-4bfd-9969-186d34d4c9b8.JPG" width="19%"/> 
</br>
<img src="https://user-images.githubusercontent.com/84231143/227761352-885ec660-fe00-4f8a-9ecd-4561b5f9143f.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227761345-77aa325a-fe36-4c1b-997f-df03b66b65b1.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227761346-672c3d19-7e9a-46c1-85e2-b718586c18c5.JPG" width="19%"/>

</br></br>


3) 로그인 화면 </br>
**front** </br>
- 로그인 화면은 이메일과 비밀번호 입력받는 가능과 입력값 유무에 따라 에러메세지를 Toast로 표시한다. </br>
- 비밀번호는 회원가입과 마찬가지로 toggle을 설정하여 비밀번호가 안보이는 것을 기본값으로 구현했다. </br>
<img src="https://user-images.githubusercontent.com/84231143/227761936-13655761-8506-42c8-b046-bd35211c2016.JPG" width="19%"/><img src="https://user-images.githubusercontent.com/84231143/227761977-718f8a6e-4032-4b1c-969b-49e632d66d9f.JPG" width="19%"/> <img src="https://user-images.githubusercontent.com/84231143/227761938-63051a32-22ba-4dcd-9b25-277483100720.JPG" width="19%"/>

</br></br>


4) 홈화면 </br>
**front** </br>
- 홈화면에서는 네비게이션 바를 이용해 프래그먼트 전환할 수 있다. </br>
- 홈화면의 주요 기능은 실시간 박스오피스 영화와 유저 mbti를 기반으로 추천되는 영화를 표시하는 것이다. 이 부분은 recyclerview로 구현했다. </br>
**back** </br>
- 네이버에서 “박스오피스”라고 검색했을 때 나오는 페이지에서 1위부터 5위까지의 영화 정보(제목, 포스터)를 Jsoup 라이브러리를 이용해 가져와 UI에 띄울 수 있도록 fragment와 연결한다. </br>
- 추천 영화는 임의로 인기 있는 영화 목록 데이터를 데이터베이스에 저장해두고 각 영화마다 16개의  MBTI 중 하나를 매칭하여 사용자의 MBTI 유형에서 4개 항목 중 3개 이상이 겹치는 영화들을 20개 보여준다. 

</br></br>


5) 영화 검색 페이지 (fragment) </br>
**front** </br>
- 상단에는 검색어 입력창과 중앙에는 리사이클러뷰로 구성이 되어있다. 
- 영화 검색 결과는 백엔드의 권해담님이 api를 이용해 추린 결과를 리사이클러뷰의 아이템 형식으로 나오도록 했다.</br> 
- 검색을 했을 때, 검색 결과가 있다면, 그 결과를 수직 리사이클러뷰로 보여주고, 없을 때는 해당 검색 결과가 없다는 레이아웃을 보여준다. </br>
- 리사이클러뷰의 각 아이템을 클릭하면, 클릭된 현재 아이템의 데이터를 영화 상세페이지 프래그먼트에 전달하면서 화면전환하도록 구현했다.</br>
**back** </br>
- 사용자가 입력한 키워드를 네이버 영화에서 검색한 결과 나오는 영화 목록을 Jsoup으로 가져와 각 영화의 제목, 포스터 이미지, 평점, 장르, 상영 시간, 감독, 출연 배우, 줄거리 정보를 MovieMainData 객체에 저장한다. 
- MovieMainData 객체들을 어댑터를 사용해 프래그먼트에 띄운다. </br>
- 영화를 하나 클릭하면 영화 상세 프래그먼트로 MovieMainData 객체를 보내준다.

 </br></br>


6) 영화 상세 페이지 </br>
**front** </br>
- 중앙 콘텐트는 스크롤뷰로 구성을 했다.
- [내가 쓴 리뷰 보러가기] 버튼을 눌렀을 때, 가져올 감상평 데이터가 있는지 확인한 후, 감상평이 있다면 해당 영화로 작성된 감상평 상세페이지 프래그먼트로 이동하고, 없다면 감상평이 없다는 팝업창에 [작성하기] 버튼으로 감상평 상세페이지 프래그먼트로 이동하도록 구현했다. </br>
**back** </br>
- 영화 검색 페이지에서 bundle 형태로 전달한 MovieMainData 객체를 상세 페이지 프래그먼트로 받아와 화면에 띄운다. 


</br></br>

7) 감상평 목록 페이지 </br>
**front** </br>
- 감상평 목록 페이지도 영화 상세 페이지와 동일하게 라시이클러뷰로 구현을 했다. 감상평 데이터가 없을 때에는 감상평이 없다는 뷰를 삽입했다.</br> 
- 편집 버튼을 통해 다중선택과 삭제룰 할 수 있고 spinner 위젯으로 각 기준이 따른 정렬도 가능하도록 했다. </br>
**back** 

</br></br>
 

8) 감상평 상세 페이지 </br>
**front** </br>
- 감상평 상세 페이지에서는 데이터 유무로 표시되는 화면이 다르다. 데이터가 있다면 감상평 수정을 할 수 있고 데이터가 없다면 오른쪽 화면을 보여주며 별점을 선택하고 내용을 등록하는 기능을 담았다. </br>
**back** </br>
- 해당하는 영화의 포스터, 제목, 별점, 평점을 Firebase에서 가져와 보여준다. 


</br></br>


9) 마이페이지 </br>
**front** </br>
- 마이페이지의 상단에는 회원정보를 TextView로 보이게했고, 중앙에는 [프로필 수정하기] 버튼이, 하단에는 내가 작성한 감상평 목록을 리사이클러뷰로 보여준다. </br>
- [프로필 수정하기] 버튼을 누르면 프로필 수정 페이지로 이동한다. </br>
- 마이페이지의 하단에는 수평 리사이클러뷰를 사용해서 내 감상평 목록들을 포스터 형식으로 보이게 구현했다. 앞전에 소개한 영화 검색페이지와 비슷하게 기존에 작성한 감상평이 있을때만 리사이클러뷰를 보이게 설정했다. </br>
- 각 아이템을 클릭하면 해당 감상평 상세 페이지로 이동한다. </br>
**back** </br>
- Firerbase에서 닉네임, MBTI값을 가져와 보여준다. 사용자가 작성한 리뷰를 띄울 수 있도록 데이터를 보내준다. 


</br></br>

10) 프로필 수정 페이지 </br>
**front** </br>
- 기존 회원 정보 중 닉네임과 MBTI를 불러오고 사용자가 수정할 때는 닉네임 EditText에 입력값이 있는 경우에만 [완료] 버튼이 활성화 되도록 만들었다. </br>
- [완료] 버튼을 누르면 저장한 후, 마이페이지로 다시 돌아온다. </br> 
**back** </br>
- Firerbase에서 닉네임, MBTI값을 가져와 보여준다. 사용자가 작성한 리뷰를 띄울 수 있도록 데이터를 보내준다. </br>

<br/><br/>

## 4. 문제 해결 </br>

<br/><br/>

## 5. 팀원
##### 팀장: 송규원
##### 프론트: 김혜은, 박수연
##### 백: 권해담, 송규원, 유다영
