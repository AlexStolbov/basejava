package com.amstolbov.web;

import com.amstolbov.Config;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.*;
import com.amstolbov.storage.PathStorage;
import com.amstolbov.storage.Storage;
import com.amstolbov.storage.serializers.JsonStreamSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private final Storage resumeStorage = new PathStorage(Config.get().getParam(Config.ParamType.STORAGE_DIR),
            new JsonStreamSerializer());

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = resumeStorage.get(uuid);
        r.setFullName(fullName);
        addContacts(r, request);
        addSectionsFromParameter(r, request);
        addOrganizations(r, request);
        resumeStorage.update(r);
        response.sendRedirect("resume");
    }

    private void addContacts(Resume r, HttpServletRequest request) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
    }

    private void addSectionsFromParameter(Resume r, HttpServletRequest request) {
        for (SectionType type : SectionType.values()) {
            if (type != SectionType.EXPERIENCE && type != SectionType.EDUCATION) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    r.addSection(type, createSection(type, value));
                } else {
                    r.getSections().remove(type);
                }
            }
        }
    }

    private void addOrganizations(Resume r, HttpServletRequest request) {
        addOrganization(SectionType.EXPERIENCE, r, request);
        addOrganization(SectionType.EDUCATION, r, request);
    }

    private void addOrganization(SectionType type, Resume r, HttpServletRequest request) {
        OrganizationSection orgResume = (OrganizationSection) r.getSections().get(type);
        orgResume.getOrganization().clear();
        String typeName = type.name();
        String[] orgs = request.getParameterValues(typeName +"_Name");
        String[] urls = request.getParameterValues(typeName +"_Url");
        for (int i = 0; i < orgs.length; i++) {
            String name = orgs[i];
            if (name != "") {
                Organization newOrg = new Organization(orgs[i], urls[i]);
                String[] positions = request.getParameterValues(typeName + "_" + i + "_Position");
                String[] dateStart = request.getParameterValues(typeName + "_" + i + "_dateStart");
                String[] dateFinish = request.getParameterValues(typeName + "_" + i + "_dateFinish");
                String[] descr = request.getParameterValues(typeName + "_" + i + "_posDescr");
                if (positions != null) {
                    for (int e = 0; e < positions.length; e++) {
                        newOrg.addExperience(new Organization.Experience(
                                LocalDate.parse(dateStart[i])
                                , LocalDate.parse(dateFinish[i])
                                , positions[i]
                                , descr[i]));
                    }
                }
                orgResume.addOrganization(newOrg);
            }
        }
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
        switch (action) {
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

}
