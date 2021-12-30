# JNUCenter
> 학교 공식 앱의 불편하거나 없는 기능을 모은 전남대학교 종합 정보 앱입니다.
</br>
</br>
</br>

# Preview
<p align="left">
<img src="https://user-images.githubusercontent.com/82270774/147670764-182f1e28-01c3-42f7-b0cb-89f5882f5a8e.png" width="23%"/>
<img src="https://user-images.githubusercontent.com/82270774/147670545-3bad463f-2494-4e96-b0d1-c40f5e16652b.png" width="23%"/>
<img src="https://user-images.githubusercontent.com/82270774/147673166-026ff0c2-bbb8-4f62-b8e2-97776d07367e.png" width="23%"/>
<img src="https://user-images.githubusercontent.com/82270774/147740298-b9e468b0-ec45-47d4-a44e-b00f5007cf74.png" width="23%"/>
</p>
</br>
<p align="left">
<img src="https://user-images.githubusercontent.com/82270774/147674058-326d687c-9aa5-484b-8bf6-317bb17fbbd3.png" width="23%"/>
<img src="https://user-images.githubusercontent.com/82270774/147675505-5306fd56-2fcd-47f1-b3f0-123c39584976.png" width="23%"/>
<img src="https://user-images.githubusercontent.com/82270774/147675699-b1ae4fa1-12c5-4869-bca1-83d4ad3552f6.png" width="23%"/>
<img src="https://user-images.githubusercontent.com/82270774/147675841-6383c1f0-7c1e-42f6-aee9-cda0008b0c40.png" width="23%"/>
</p>

</br>

## 1. 제작기간 & 참여인원 
> - 2021년 11월 9일 ~ 12월 25일 
> - 개인 프로젝트 

</br>
</br>
</br>

## 2. 개발 환경 및 기술 

> **Client**
> * Kotlin - Android Stuio
> * MVVM 패턴 
> * Paging3 
> * Coroutine
> * Retrofit2 
> * CustomView
> * LiveData
> * DataBinding
> * ViewPager
> * Fragment
> * Room Database
> * FCM / Googel Drive Api / Kakao Map Api 

> **Server**
> * Java8 - SpringBoot - InteliJ  
> * AWS EC2, ROUTE53
> * Jsoup 
> * FCM 
> * Lombok

> **DataBase**
> * MySQL

</br>
</br>

## 3. 서비스 전체 흐름

<img src="https://user-images.githubusercontent.com/82270774/147727548-63cdd798-aec0-44d8-b700-54d1567aa1fe.PNG" width="100%"/>



## 4. 핵심 기능 

> JNUCenter의 핵심 기능은 학교 지도 찾기입니다. 세부 기능은 다음과 같습니다.  

</br>

> * 현재 위치에서 걸어서, 뛰어서 걸리는 시간 제공
> * 부서명만으로 건물 정확히 찾기   
> * 위치 힌트 제공 

</br>

<details>
<summary><b>세부 설명</b></summary>
<div markdown="1">
  
### 4.1 개발 의도 
  - 넓고, 많은 학교 건물들로 인한 혼란 방지</br> 
  - 걸었을 때, 뛰었을 때 걸리는 시간 제공으로 지각 방지</br>
  - 장소 힌트로 편의성 제공  
</br>

### 4.2 전체 흐름

<img src="https://user-images.githubusercontent.com/82270774/147760644-b03a8051-7061-49ff-ba79-e8e94bb41756.PNG" width="100%"/>
  

### 4.3 유저 장소 검색 (PlaceActivity)

<img src="https://user-images.githubusercontent.com/82270774/147756579-7ca26ac4-48fe-4d2a-bd0e-2beac503a554.PNG" width="100%"/>
</br>
</br>

- **getPlacesByName 함수** :pushpin: [코드 확인](https://github.com/millionswillnd/JNUCenter/blob/2a2d1546f7cf16967eef30f6c71b8ad664fdc9ac/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceActivity.kt#L65)
  - PlaceViewModel의 getPlacesByName 함수를 호출합니다. 
  - 검색어가 변할 때 마다 실행됩니다. 

</br>
</br>

### 4.4 PlaceViewModel

<img src="https://user-images.githubusercontent.com/82270774/147758074-148de48b-5b71-4b0c-aba4-ab2269f65894.PNG" width="100%"/>
</br>
</br>

- **getPlacesByName 함수** :pushpin: [코드 확인](https://github.com/millionswillnd/JNUCenter/blob/b93fc3b05dbbe233acabb2bdc7a2dafb0320765c/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceViewModel.kt#L34)
  - PlaceRepository의 getPlacesByName 함수를 호출합니다. 

</br>
</br>

### 4.5 PlaceRepository

<img src="https://user-images.githubusercontent.com/82270774/147759843-5d41904c-62f8-4d0a-913e-c134a457e84b.PNG" width="100%"/>
</br>
</br>

- **getPlacesByName 함수** :pushpin: [코드 확인](https://github.com/millionswillnd/JNUCenter/blob/05bea843ba4488853ed7665e8d64b8f15296adf4/app/src/main/java/com/jiib/jnucenter/mvvm/repository/PlaceRepository.kt#L26)
  - PlacePagingSource 객체의 파라미터는 순서대로 다음과 같습니다  
  (1) Retrofit Service 객체 </br>
  (2) 검색어 </br>
  (3) 검색 중인지, 첫 화면이지 구분하기 위한 플래그 </br>   

</br>
</br>

### 4.6 PlacePagingSource

<img src="https://user-images.githubusercontent.com/82270774/147762300-156e29c3-ca89-4749-a699-6c551bcc4c68.PNG" width="100%"/>
</br>
</br>

- :pushpin: [코드 확인](https://github.com/millionswillnd/JNUCenter/blob/8d96134d9ee19748eb0b1bcdc17f3a65b4862a72/app/src/main/java/com/jiib/jnucenter/mvvm/repository/network/retrofit/place/PlaceService.kt#L14)
 
</br>
</br>



### 4.7 PlaceService, PlaceApi (Retrofit)

<img src="https://user-images.githubusercontent.com/82270774/147764873-74e71d3d-1558-4f95-8fab-cd5af8ef62d5.PNG" width="100%"/>
</br>
</br>

- :pushpin: [코드 확인](https://github.com/millionswillnd/JNUCenter/blob/2d2f2faa92a242c38ceb200c3bc7ee945d2496c5/app/src/main/java/com/jiib/jnucenter/mvvm/repository/network/retrofit/place/PlacePagingSource.kt#L27)
 
</br>
</br>



### 4.8 SpringBoot (BackEnd Server)

<img src="https://user-images.githubusercontent.com/82270774/147766829-8e782013-a25a-4cde-9839-47b6dc3e71b3.PNG" width="80%"/>
</br>
</br>

- Database : MySQL
- Mapper의 SQL문에서 LIKE CONCAT('%', #{search_word}, '%') 를 통해 검색어가 포함된 컬럼을 가진 ROW를 선별했습니다. 
- Mapper의 SQL문에서 LIMIT 12 OFFSET #{start_idx} 를 통해 시작 인덱스인 row 부터 12개의 row를 뽑아왔습니다.   
 
</br>
</br>



### 4.9 Response 받고 검색 목록 표시하기 

<img src="https://user-images.githubusercontent.com/82270774/147769899-86eaf364-002f-4110-87fc-ead373e5e120.PNG" width="80%"/>

</br>
</br>

- :pushpin: [프래그먼트 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/2be3edd4756aade12b7728ffbf6901cb81290511/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceSearchFragment.kt#L72)
 
</br>
</br>

<img src="https://user-images.githubusercontent.com/82270774/147673166-026ff0c2-bbb8-4f62-b8e2-97776d07367e.png" width="25%"/>


</br>
</br>
</br>


### 4.10 장소 찾기 

<img src="https://user-images.githubusercontent.com/82270774/147773289-65575b09-84d0-4675-b1d4-5909ad5e9525.PNG" width="80%"/>
</br>
</br>

- :pushpin: [find_listener 함수 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/98d28261edf5c6cd17fefb7d22fb81c215aaa4e7/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceSearchFragment.kt#L77)
- :pushpin: [Adapter 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/db6eee59bde9c4f22d25e6adc370666d9ee9d6ec/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceAdapter.kt#L16) 
 
</br>
</br>



### 4.11 현재 위치 위도,경도 구하기

<img src="https://user-images.githubusercontent.com/82270774/147778440-7780fc98-a408-434c-8bae-2ce1fc0c7820.PNG" width="80%"/>
</br>
</br>

- :pushpin: [PlaceMapFragment 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/db6eee59bde9c4f22d25e6adc370666d9ee9d6ec/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceMapFragment.kt#L21)
- :pushpin: [유틸클래스 getCurrentPosition 함수 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/db6eee59bde9c4f22d25e6adc370666d9ee9d6ec/app/src/main/java/com/jiib/jnucenter/mvvm/utils/PlaceUtil.kt#L58)
 
</br>
</br>



### 4.12 지도 말풍선 표시(건물이름, 장소힌트, 걸리는 시간) 

<img src="https://user-images.githubusercontent.com/82270774/147780458-318c68c4-fbaf-4ee0-a5fc-e756f9e51ca6.PNG" width="80%"/>
</br>
</br>

- :pushpin: [CustomBallonAdapter 클래스 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/5dd519ef0bca2f86652bdf3540081dde87648d9a/app/src/main/java/com/jiib/jnucenter/mvvm/feature/place/PlaceMapFragment.kt#L86)
- :pushpin: [유틸클래스 getDistance 함수 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/5dd519ef0bca2f86652bdf3540081dde87648d9a/app/src/main/java/com/jiib/jnucenter/mvvm/utils/PlaceUtil.kt#L17)
- :pushpin: [유틸클래스 getTimeByDistance 함수 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/5dd519ef0bca2f86652bdf3540081dde87648d9a/app/src/main/java/com/jiib/jnucenter/mvvm/utils/PlaceUtil.kt#L45)
 
</br>
</br>

<img src="https://user-images.githubusercontent.com/82270774/147740298-b9e468b0-ec45-47d4-a44e-b00f5007cf74.png" width="25%"/>


</div>
</details>

</br>

## 5. 트러블 슈팅


<details>
</br>
<summary><b>5.1 FCM 앱 백그라운드 상태시 수신 문제</b></summary>

### 문제상황 
> - 구글 FCM 푸시알람이 앱이 백그라운드 상태일 때 헤드업 알람이 수신되지 않는 문제가 있었습니다
> - 서버에서 스케줄링을 통해 앱 상태와 상관없이 지속적으로 장학, 학식, 강의 기한 경고 알람을 보내기 때문에 큰 문제가 됐습니다

</br>

### 해결 
> - 문제 해결을 위해 클라이언트 단에서 상당히 해멘 뒤에 다음과 같은 결론을 내렸습니다</br>
  1. 결국 앱이 백그라운드일 때, FCM을 수신하는 주체는 안드로이드 운영체제 시스템일 수 밖에 없다. 
  2. 따라서 안드로이드 운영체제에서 '이 FCM은 백그라운드 헤드업 노티피케이션을 하지 않는다' 라고 받아들였을 것이다    
  3. 따라서 FCM 서버에 리퀘스트를 할 때, 즉 백엔드인 스프링부트단의 코드에 문제가 있다.
  4. 무언가 설정이나 정보를 덜 줬거나, 삭제햐아 할 수 있다.  
</br>

> - 이에 초점을 두고 구글링을 계속한 결과 다음과 같은 스택오버플로 답변을 확인했습니다. [스택오버플로](https://stackoverflow.com/questions/37711082/how-to-handle-notification-when-app-in-background-in-firebase)
</br>
<img src="https://user-images.githubusercontent.com/82270774/147811050-0ac05043-a069-4eb0-b380-f8a82d09b9a7.PNG" width="80%"/>
</br>
</br>

> - 즉 FCM 서버에 리퀘스트를 보낼 때 JSON 내의 notification 항목을 삭제하고, data 항목에 자신이 보낼 알람의 title과 body를 담아야 했습니다.  
> - 이를 통해 성공적으로 백그라운드 헤드업 노티를 수신할 수 있었습니다 

</br>

### 개선된 코드 

</br>
<img src="https://user-images.githubusercontent.com/82270774/147811703-23a98c90-751e-40e0-a0fb-1966fe0001ae.PNG" width="80%"/>
</br>

</details>




<details>
</br>
<summary><b>5.2 구글 로그아웃시 GSO 객체 문제</b></summary>
<div markdown="1">  

</br>

### 문제상황
> - 구글 로그인을 한 상태에서 로그아웃을 시도할 경우 반드시 GoogleSignInOptions 객체가 필요합니다 </br>
> - 하지만 Repository 레이어서 GSO객체가 할당되기 때문에, 액티비티에서 구글 로그아웃시 GSO 객체에 접근하지 못하는 문제가 발생했습니다 </br> 
</br>

### 해결
> - 로그인 과정을 모두 액티비티단에서 구현하면 해결되지만, MVVM 패턴으로 코드 결합도를 낮춘 의미가 사라진다 생각했습니다. 
> - 따라서 액티비티에서 GSO 객체를 미리 null로 선언한 뒤 
> - 구글 드라이브를 이용할 때, 파라미터로 넘어온 GSO를 할당하는 함수를 파라미터로 넘겨줘, Repository 레이어에서 이를 호출하는 방식으로 해결했습니다.  
</br>
<img src="https://user-images.githubusercontent.com/82270774/147819021-4a56d57c-4657-4e28-abe2-0d527a60a160.PNG" width="90%"/>

- :pushpin: [GoogleLogin 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/5dd519ef0bca2f86652bdf3540081dde87648d9a/app/src/main/java/com/jiib/jnucenter/mvvm/repository/network/google/GoogleLogin.kt#L21)

</br>
</div>
</details>





<details>
</br>
<summary><b>5.3 페이징 백엔드 처리 </b></summary>
<div markdown="1">  

</br>

### 문제상황
> - 처음엔 페이징 리퀘스트를 처리하기 위해 백엔드에서 일일이 DB 테이블의 컬럼에 Page 인덱스를 줘서 구별했습니다.</br>
> - 하지만 이런 경우 테이블에 CRUD가 잦을 경우 인덱스 수정 부가작업이 너무 많게 됩니다. 또한 검색 기능 시 row가 뒤섞이기에 페이지 인덱스가 소용이 없게 됩니다.</br>  
> - 따라서 새로운 방법을 생각해야했습니다. 
</br>

### 해결
> - 아예 페이지 index 컬럼을 삭제했습니다. 대신 sql 문 내에서 해결하기로 했습니다. 
> - 전체적인 로직은 다음과 같습니다. 
  
</br>
<img src="https://user-images.githubusercontent.com/82270774/147826992-5085e576-6f58-4d28-ba82-603927adda84.PNG" width="80%"/>
</br>
</div>
</details>






<details>
</br>
<summary><b>5.4 Jsoup 학교 이클래스 리스폰스 문제 </b></summary>
<div markdown="1">  

</br>

### 문제상황
> - Jsoup으로 학교의 E-CLASS html 코드를 받기 위해 리퀘스트를 보낸 경우, 디자인 양식만 있고 내용은 텅 빈(강의 이름 등)</br> 페이지가 리스폰스로 왔습니다. </br>
> - 이클래스 접속에 앞서 포털 로그인 시 거치는 Login.aspx -> Logon.aspx -> Login.aspx(포털메인페이지) </br>
3가지의 경우 2,3 번째의 경우 제대로 리스폰스가 왔기 때문에
> - 왜 이클래스에 해당되는 sel.jnu.ac.kr만 내용이 비어있는 리스폰스가 오는지 해답을 며칠간 찾지 못했습니다.       
</br>
<img src="https://user-images.githubusercontent.com/82270774/147832674-13d1550b-9aa8-457a-b0d3-c1fa70fef035.PNG" width="28%"/>
</br>

### 해결
> - 문제의 근본 원인은 윗 사진의 1번을 리스폰스로 받기 위해 리퀘스트를 보낼 때 </br> 제가 아래 그림에 해당하는 data payload를 누락했기 때문입니다. 
> - 리퀘스트를 보낼 때 페이로드에 주황색은 중요한게 아니라 생각해 넣지 않았고, 빨간색 줄은 생략했습니다.
> - 따라서 처음 리스폰스를 받을 시 쿠키값들이 올 리 없었고 </br>그걸 다음 리퀘스트들에 set 하지 않았기 때문에 발생한 문제였습니다.
</br>
<img src="https://user-images.githubusercontent.com/82270774/147832983-ae41b33c-387b-421c-b5a0-d961b036b35f.PNG" width="29%"/>
</br>
</br>

> - 결국 다음과 같이 제대로 payload를 리퀘스트에 넣어준 뒤, 리스폰스로 받은 쿠키값들을 차례로 세팅해주자 </br> 성공적으로 저의 강의 정보가 담긴 이클래스 html을 리스폰스로 받을 수 있었습니다.   
</br>
<img src="https://user-images.githubusercontent.com/82270774/147834405-c151d5f1-b38b-4446-ace4-3abc22cd2efb.PNG" width="55%"/>
</br>
</div>
</details>




<details>
<summary><b>5.5 전화번호 목록 자음별 섹션 문제</b></summary>
</br>

### 문제상황 
> - RecyclerView에 학내 부서 전화번호 목록 나열 시, 자음별 섹션을 만들고자 할 때 
> - 부서 전화번호 테이블에 모든 데이터를 정렬해서 넣고, 자음이 바뀌는 첫 row에 consonant 컬럼에 다음과 같이 자음값을 넣어서 해결했습니다. 

</br>
<img src="https://user-images.githubusercontent.com/82270774/147812459-6096ff8a-8b41-46e7-a243-7861ed03e24e.PNG" width="50%"/>
</br>

> - 하지만, 이럴 경우 데이터 수정이 잦은 테이블일 시 자음 컬럼을 넣고 수정하는데 많은 비용을 초래할 수 있다 판단했습니다. 
> - 또한 검색 기능 사용시 자음 섹션이 뒤죽박죽이 되었습니다.  

</br>

### 해결

> - 안드로이드단에서 직접 부서명의 초성을 코드로 추출해서 
> - 이전 아이템의 부서명의 초성과 비교해 차이가 있으면 
> - 해당 RecyclerView 아이템의 자음섹션 xml상의 뷰를 Gone에서 Visible로 변경하고 자음 섹션을 보여줬습니다.   


</br>
<img src="https://user-images.githubusercontent.com/82270774/147814873-a1832b32-885b-446f-8bca-1fc418642980.PNG" width="50%"/>
</br>


> - 다만, 여기서 백엔드 테이블의 자음 컬럼을 아예 삭제하고, 안드로이드에서 초성 추출한걸 활용하는 것이 더 효율적이었을 것 같습니다. 



</details>






<details>
</br>
<summary><b>5.6 뷰 집합 반복으로 인한 XML 가독성 문제</b></summary>
<div markdown="1">  

</br>

### 문제상황
> - 예를들면 아래 사진 처럼 <아이콘, 제목>이 계속 반복되는 경우 </br>
> - 6개만 돼도 TextView와 ImageView를 2개씩, 총 12개를 XML 코드에 배치해서 가독성이 좋지 않았습니다.</br>
</br>
<img src="https://user-images.githubusercontent.com/82270774/147805324-683a9e16-53e4-44c3-96a1-946ca482b8ae.PNG" width="25%"/>

</br>

### 해결
> * 따라서 TextView와 ImageView를 커스텀뷰로 묶어서 XML 상에서 코드를 최소화했습니다. 
</br>
<img src="https://user-images.githubusercontent.com/82270774/147805653-60fde975-5a3e-437f-83af-71405f21063c.PNG" width="40%"/>

</br>

### 개선된 코드 
- :pushpin: [커스텀뷰 코드 확인](https://github.com/millionswillnd/JNUCenter/blob/5dd519ef0bca2f86652bdf3540081dde87648d9a/app/src/main/java/com/jiib/jnucenter/mvvm/feature/main/CustomIconTitle.kt#L13)
</div>
</details>







<details>
</br>
<summary><b>5.7 room database 테이블 자동증분 문제 </b></summary>
<div markdown="1">  

</br>

### 문제상황
> - 다음과 같이 room db 테이블 pk에 자동증분을 걸어놨으나 실제 데이터 INSERT 시 작동하지 않았습니다</br> 
</br>
<img src="https://user-images.githubusercontent.com/82270774/147835427-617799e4-7d32-4bf8-98f9-40acf636b4b7.PNG" width="28%"/>
</br>

### 해결
> - room은 auto generate를 걸은 값이 null일 때만, "값을 지정하지 않았구나. auto generate 해야겠다" </br> 라고 인식합니다. 따라서 제가 임의로 어떤 값을 지정하지 않고 </br> null을 지정해주자 문제가 해결됐습니다.  

</div>
</details>
