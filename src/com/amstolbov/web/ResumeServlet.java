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
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private final Storage resumeStorage = new ArrayStorage();

    public ResumeServlet() {
        resumeStorage.save(ResumeTestData.getResume("UUID1",
                "full name 1",
                ResumeTestData.ALL_CONTACTS,
                ResumeTestData.WITHOUT_ORGANIZATION));
        resumeStorage.save(ResumeTestData.getResume("UUID2",
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

    private String getHtmlBody(String param) {
        List<Resume> all;
        if (param != null) {
            try {
                all = Arrays.asList(resumeStorage.get(param));
            } catch (NotExistStorageException e) {
                return "Resumes " + param + (" not found");
            }
        } else {
            all = resumeStorage.getAllSorted();
        }
        return getResumeTable(all);
    }

    private String getResumeTable(List<Resume> all) {
        String res = "<table border=\" 1 \">" +
                "<tr>" +
                "<td> ID </td>" +
                "<td> Full name</td>" +
                "</tr>";
        for (Resume resume : all) {
            res = res + getRow(resume);
        }
        res = res +  "</table>";
        return res;
    }

    private String getRow(Resume resume) {
        return "<tr>" +
                "<td> " + resume.getUuid() + " </td>" +
                "<td> " + resume.getFullName() + " </td>" +
                "</tr>";
    }
}
