package org.qwerris.filmsreviews.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.qwerris.filmsreviews.service.FilmService;
import org.qwerris.filmsreviews.utils.JspHelper;

import java.io.IOException;

@WebServlet("/films")
public class FilmsServlet extends HttpServlet {
    private final FilmService filmService = FilmService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("films", filmService.getPreviewFilms());
        req.getRequestDispatcher(JspHelper.getJspPath("films")).forward(req, resp);
    }
}
