package com.amstolbov.web;

import com.amstolbov.ResumeTestData;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.*;
import com.amstolbov.storage.ArrayStorage;
import com.amstolbov.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private final Storage resumeStorage = new ArrayStorage();

    @Override
    public void init() throws ServletException {
        super.init();
        resumeStorage.save(ResumeTestData.getResume("UUID1", "full name 1",
                ResumeTestData.ALL_CONTACTS, ResumeTestData.ALL_SECTIONS));
        resumeStorage.save(ResumeTestData.getResume("UUID2", "full name 2",
                ResumeTestData.ALL_CONTACTS, ResumeTestData.ALL_SECTIONS));
        resumeStorage.save(ResumeTestData.getResume("UUID3", "full name 3",
                ResumeTestData.ONLY_PHONE, ResumeTestData.ALL_SECTIONS));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = resumeStorage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addSection(type, createSection(type, value));
            } else {
                r.getSections().remove(type);
            }
        }
        resumeStorage.update(r);
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", resumeStorage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action){
            case "delete":
                resumeStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = resumeStorage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    private AbstractSection createSection(SectionType sectionType, String description) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new SimpleTextSection(description);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(description.split("\n"));
            default:
                throw new StorageException("", "");
        }
    }

}
