package org.qwerris.filmsreviews.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.qwerris.filmsreviews.dto.CreateReviewDto;
import org.qwerris.filmsreviews.dto.FilmDto;
import org.qwerris.filmsreviews.dto.UserDto;
import org.qwerris.filmsreviews.service.FilmService;
import org.qwerris.filmsreviews.service.ReviewService;
import org.qwerris.filmsreviews.utils.HibernateUtil;
import org.qwerris.filmsreviews.utils.JspHelper;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/film")
public class FilmServlet extends HttpServlet {
    private final FilmService filmService = FilmService.getInstance();
    private final ReviewService reviewService = ReviewService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Optional<FilmDto> optionalFilmDto = filmService.getFilmById(Integer.parseInt(req.getParameter("id")));
        session.getTransaction().commit();
        req.setAttribute("film", optionalFilmDto.orElse(null));
        req.getRequestDispatcher(JspHelper.getJspPath("film")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        reviewService.save(CreateReviewDto.builder()
                .user((UserDto) req.getSession().getAttribute("user"))
                .filmId(Integer.parseInt(req.getParameter("id")))
                .score(Integer.parseInt(req.getParameter("score")))
                .text(req.getParameter("text"))
                .build());
        session.getTransaction().commit();
        resp.sendRedirect("/film?id=" + req.getParameter("id"));
    }
}
