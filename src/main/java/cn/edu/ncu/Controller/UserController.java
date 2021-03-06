package cn.edu.ncu.Controller;

import cn.edu.ncu.Entity.User;
import cn.edu.ncu.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/testUser")
    private void testUser(HttpServletRequest request, HttpServletResponse response ) throws Exception{
        List<User> list = userService.findAll();
        HttpSession session = request.getSession();
        String uid = request.getParameter("uid");
        String upassword = request.getParameter("upassword");
        for(User user:list){
            if(user.getUid().equals(uid)&&user.getUpassword().equals(upassword)){
                session.setAttribute("userId",uid);
                response.sendRedirect("../main.jsp");
            }
        }
    }
    /**
     *  *查找个人信息
     *
     *
     * @param request
     * @param response
     * @return 跳转到personal.jsp
     */
    @RequestMapping("/getByUId")
    public String getByUId(HttpServletRequest request,HttpServletResponse response){
        System.out.println("根据id查找");

        HttpSession session = request.getSession(false);
        String uid = (String) session.getAttribute("userId");
        User user = userService.getByUId(uid);

        session.setAttribute("PersonalData",user);

        return "personal";

    }

    /**
     * 修改用户信息
     * @param user
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/userEdit")
    public void userEdit(User user, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userService.userEdit(user);

        response.sendRedirect(request.getContextPath()+"/user/getByUId");

    }

    /**
     * 跳转到修改个人信息页面
     * @return personalEdit
     */
    @RequestMapping("/jumpToUserEdit")
    public String jumpToUserEdit(){
        return "personalEdit";
    }
}
