package com.example.movieapp;

//사용자 계정 정보 모델 클래스

public class UserAccount {
    private String name;     // Firebase Uid (고유 토큰정보)
    private String email;     // 이메일 아이디

    /**
     * firebase realtime database를 쓸 때 모델클래스를 이용해서 갖고와야할 때 빈 생성자를 만들어주어야 함
     * -> 안 그러면 database 조회시 오류
     */

    public UserAccount(){ }

    public UserAccount(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String emailId){
        this.email = email;
    }

    public String toString() {
        return "User{" +
                "userName='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
