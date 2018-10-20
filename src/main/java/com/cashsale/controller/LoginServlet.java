package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Customer;
import com.cashsale.bean.Result;
import com.cashsale.filter.CommonUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:50
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

//        String user = request.getParameter("user");
//        Customer c = new Gson().fromJson(user,Customer.class);
//        String userName = c.getUsername();
//        String password = c.getPassword();

        BufferedReader br = request.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }
        System.out.println(user);
        Customer c = new Gson().fromJson(user,Customer.class);
        String userName = c.getUsername();
        String password = c.getPassword();

        PrintWriter writer = response.getWriter();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM all_user WHERE user_name=? AND pass_word=?");
            pstmt.setString(1,userName);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String token = CommonUtils.createJWT(userName,30*60*1000);
                writer.print(JSONObject.toJSON(new Result<String>(1, token, "登录成功")));
            } else {
                writer.print(JSONObject.toJSON(new Result<String>(-1, null, "登录失败,用户名或密码错误")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
