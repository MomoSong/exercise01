package org.zerock.member.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class LoginDTO {

	String email, pw, name, hp, addr;
	int age, money, grade;
	boolean login;
	Date lastLog;
	MultipartFile photo;
	
	
	
	public boolean getLogin() {
		return login;
	}
	public void setLogin(int login) {
		if(login == 1) {
			this.login = true;			
		}else {
			this.login = false;
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		switch(grade) {
			case 1: 
				this.grade = grade;
				break;
			case 9:
				this.grade = grade;
				break;
			default:
				this.grade = 0;
		}
	}
	public Date getLastLog() {
		return lastLog;
	}
	public void setLastLog(Date lastLog) {
		this.lastLog = lastLog;
	}
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", pw=" + pw + ", name=" + name + ", hp=" + hp + ", addr=" + addr + ", age="
				+ age + ", money=" + money + ", grade=" + grade + ", login=" + login + ", lastLog=" + lastLog
				+ ", photo=" + photo + "]";
	}
}
