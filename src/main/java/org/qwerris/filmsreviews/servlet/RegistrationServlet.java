package org.qwerris.filmsreviews.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.qwerris.filmsreviews.dto.CreateUserDto;
import org.qwerris.filmsreviews.entity.Gender;
import org.qwerris.filmsreviews.exceptions.ValidationException;
import org.qwerris.filmsreviews.service.UserService;
import org.qwerris.filmsreviews.utils.JspHelper;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getJspPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            userService.createUser(createUserDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
