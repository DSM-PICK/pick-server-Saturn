package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.domains.service.AuthService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.utils.form.AccessTokenReissuanceResponseForm;
import com.dsm.pick.utils.form.LoginResponseForm;
import com.dsm.pick.utils.form.TeacherRequestForm;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthService authService = new MockAuthService(null, null);
    private AuthController authController = new AuthController(authService, new MockJwtService());

    @Test
    void login_success() {
        String teacherId = "aaa";
        String teacherPw = "bbb";
        TeacherRequestForm teacherResponseForm = new TeacherRequestForm(teacherId, teacherPw);

        LoginResponseForm actual = authController.login(teacherResponseForm);

        String expectedAccessToken = "987654321" + teacherId;
        String expectedRefreshToken = "123456789" + teacherId;

        assertEquals(expectedAccessToken, actual.getAccessToken());
        assertEquals(expectedRefreshToken, actual.getRefreshToken());
    }

    @Test
    void login_password_mismatch_return_null() {
        String teacherId = "aaa";
        String teacherPw = "null";
        TeacherRequestForm teacherResponseForm = new TeacherRequestForm(teacherId, teacherPw);

        LoginResponseForm actual = authController.login(teacherResponseForm);

        assertNull(actual);
    }

    @Test
    void login_id_mismatch_return_null() {
        String teacherId = "null";
        String teacherPw = "bbb";
        TeacherRequestForm teacherResponseForm = new TeacherRequestForm(teacherId, teacherPw);

        LoginResponseForm actual = authController.login(teacherResponseForm);

        assertNull(actual);
    }

    @Test
    void isUsable() {
        HttpServletRequest request = new MockHttpServletRequest();
        authController.isUsableToken(request);
    }

    @Test
    void accessTokenReissuance() {
        HttpServletRequest request = new MockHttpServletRequest();
        AccessTokenReissuanceResponseForm result = authController.accessTokenReissuance(request);
    }

    static class MockAuthService extends AuthService {

        private Map<String, Teacher> teachers = new HashMap<>();

        public MockAuthService(TeacherRepository userRepository, JwtService jwtService) {
            super(userRepository);

            Teacher teacher = new Teacher();
            teacher.setId("aaa");
            teacher.setPw("bbb");
            teacher.setName("ccc");
            teachers.put(teacher.getId(), teacher);
        }

        @Override
        public String encodingPassword(String original) {
            return original;
        }

        @Override
        public boolean checkIdAndPassword(Teacher teacher) {
            Teacher findTeacher = teachers.get(teacher.getId());
            teachers.remove(teacher.getId());
            if(findTeacher == null) return false;
            else if(!findTeacher.getPw().equals(teacher.getPw())) return false;
            teachers.put(teacher.getId(), findTeacher);
            return true;
        }
    }

    static class MockHttpServletRequest implements HttpServletRequest {

        @Override
        public String getAuthType() {
            return null;
        }

        @Override
        public Cookie[] getCookies() {
            return new Cookie[0];
        }

        @Override
        public long getDateHeader(String name) {
            return 0;
        }

        @Override
        public String getHeader(String name) {
            return "123456789aaa";
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return null;
        }

        @Override
        public int getIntHeader(String name) {
            return 0;
        }

        @Override
        public String getMethod() {
            return null;
        }

        @Override
        public String getPathInfo() {
            return null;
        }

        @Override
        public String getPathTranslated() {
            return null;
        }

        @Override
        public String getContextPath() {
            return null;
        }

        @Override
        public String getQueryString() {
            return null;
        }

        @Override
        public String getRemoteUser() {
            return null;
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public String getRequestedSessionId() {
            return null;
        }

        @Override
        public String getRequestURI() {
            return null;
        }

        @Override
        public StringBuffer getRequestURL() {
            return null;
        }

        @Override
        public String getServletPath() {
            return null;
        }

        @Override
        public HttpSession getSession(boolean create) {
            return null;
        }

        @Override
        public HttpSession getSession() {
            return null;
        }

        @Override
        public String changeSessionId() {
            return null;
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            return false;
        }

        @Override
        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            return false;
        }

        @Override
        public void login(String username, String password) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            return null;
        }

        @Override
        public Part getPart(String name) throws IOException, ServletException {
            return null;
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

        }

        @Override
        public int getContentLength() {
            return 0;
        }

        @Override
        public long getContentLengthLong() {
            return 0;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public String getParameter(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return null;
        }

        @Override
        public String[] getParameterValues(String name) {
            return new String[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return null;
        }

        @Override
        public String getProtocol() {
            return null;
        }

        @Override
        public String getScheme() {
            return null;
        }

        @Override
        public String getServerName() {
            return null;
        }

        @Override
        public int getServerPort() {
            return 0;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return null;
        }

        @Override
        public String getRemoteAddr() {
            return null;
        }

        @Override
        public String getRemoteHost() {
            return null;
        }

        @Override
        public void setAttribute(String name, Object o) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return null;
        }

        @Override
        public String getRealPath(String path) {
            return null;
        }

        @Override
        public int getRemotePort() {
            return 0;
        }

        @Override
        public String getLocalName() {
            return null;
        }

        @Override
        public String getLocalAddr() {
            return null;
        }

        @Override
        public int getLocalPort() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            return null;
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            return null;
        }

        @Override
        public boolean isAsyncStarted() {
            return false;
        }

        @Override
        public boolean isAsyncSupported() {
            return false;
        }

        @Override
        public AsyncContext getAsyncContext() {
            return null;
        }

        @Override
        public DispatcherType getDispatcherType() {
            return null;
        }
    }

    static class MockJwtService extends JwtService {

        private Date date = new Date();
        private String teacherId = "aaa";

        @Override
        public String createAccessToken(String teacherId) {
            this.teacherId = teacherId;
            return "987654321" + teacherId;
        }

        @Override
        public String createRefreshToken(String teacherId) {
            this.teacherId = teacherId;
            return "123456789" + teacherId;
        }

        @Override
        public String getTeacherId(String token) {
            return token.substring(9);
        }

        @Override
        public Date getExpiration(String token) {
            return date;
        }

        @Override
        public boolean isValid(String token) {
            return token.substring(9).equals(teacherId);
        }

        @Override
        public boolean isNotTimeOut(String token) {
            return true;
        }
    }
}