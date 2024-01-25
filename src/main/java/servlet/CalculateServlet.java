package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/calculate-servlet")
public class CalculateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String equation = req.getParameter("equation");
        if(equation.isBlank()){
            equation = "No input";
        }
        String result = Calculator.calculate(equation);
        Calculator.history.add(equation + "|" + result.replace("Your result = ", ""));
        req.setAttribute("result", result);
        getServletContext().getRequestDispatcher("/result-page.jsp").forward(req, resp);
    }
}
