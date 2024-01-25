package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.Достать историю
        //2.Положить в память
        //3.Отдать страницу с историей
        req.setAttribute("history", Calculator.history);
        getServletContext().getRequestDispatcher("/history-page.jsp").forward(req, resp);
    }
}
