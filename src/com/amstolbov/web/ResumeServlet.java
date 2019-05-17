package com.amstolbov.web;

        import com.amstolbov.ResumeTestData;
        import com.amstolbov.exception.NotExistStorageException;
        import com.amstolbov.model.Resume;
        import com.amstolbov.storage.ArrayStorage;
        import com.amstolbov.storage.Storage;

        import javax.servlet.ServletException;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private final Storage resumes = new ArrayStorage();

    public ResumeServlet() {
        resumes.save(ResumeTestData.getResume("UUID1",
                "full name 1",
                ResumeTestData.ALL_CONTACTS,
                ResumeTestData.WITHOUT_ORGANIZATION));
        resumes.save(ResumeTestData.getResume("UUID2",
                "full name 2",
                ResumeTestData.ALL_CONTACTS,
                ResumeTestData.WITHOUT_ORGANIZATION));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-type", "text/html; charset=UTF-8");
        response.getWriter().write(getHtmlBody(request.getParameter("name")));
    }

    private String getHtmlBody(String uuid) {
        String resp = "Hello resumes";
        if (uuid != null) {
            try {
                resp = getResumeTable(resumes.get(uuid));
            } catch (NotExistStorageException e) {
                resp = "Resumes " + uuid + (" not found");
            }
        }
        return resp;
    }

    private String getResumeTable(Resume resume) {
        return "<table border=\" 1 \">" +
                "<tr>" +
                "<td> UUID </td>" +
                "<td> full name</td>" +
                "</tr>" +
                "<tr>" +
                "<td> " + resume.getUuid() + " </td>" +
                "<td> " + resume.getFullName() + " </td>" +
                "</tr>" +
                "</table>";
    }
}
