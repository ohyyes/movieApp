package com.example.movieapp;

//사용자 계정 정보 모델 클래스

public class UserAccount {
    //    private String profile; //프로필 이미지
    private String name;     // 닉네임
    private String email;     // 이메일 아이디
    private String pwd;
    //    private String mbti;    //mbti
    private String idToken; //Firebase Uid (고유 토큰정보)
    /**
     * firebase realtime database를 쓸 때 모델클래스를 이용해서 갖고와야할 때 빈 생성자를 만들어주어야 함
     * -> 안 그러면 database 조회시 오류
     */

    public UserAccount(){ }

    public UserAccount(String email, String pwd, String name){
        this.email = email;
        this.pwd = pwd;
        this.name = name;
//        this.mbti = mbti;
//        this.profile = profile;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String emailId){
        this.email = email;
    }

    public String getPwd(){
        return pwd;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

//    public String getMbti(){
//        return mbti;
//    }
//
//    public void setMbti(String mbti){
//        this.mbti = mbti;
//    }
//
//    public String getProfile() {
//        return profile;
//    }
//
//    public void setProfile(String profile) {
//        this.profile = profile;
//    }

    public String getIdToken(){
        return idToken;
    }

    public void setIdToken(String idToken){
        this.idToken = idToken;
    }

    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
