package org.qwerris.filmsreviews.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.qwerris.filmsreviews.service.FilmService;
import org.qwerris.filmsreviews.utils.HibernateUtil;
import org.qwerris.filmsreviews.utils.JspHelper;

import java.io.IOException;

@WebServlet("/films")
public class FilmsServlet extends HttpServlet {
    private final FilmService filmService = FilmService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        req.setAttribute("films", filmService.getPreviewFilms());
        session.getTransaction().commit();
        req.getRequestDispatcher(JspHelper.getJspPath("films")).forward(req, resp);
    }
}
