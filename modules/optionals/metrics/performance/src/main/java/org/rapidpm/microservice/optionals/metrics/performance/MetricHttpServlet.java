package org.rapidpm.microservice.optionals.metrics.performance;

import org.rapidpm.proxybuilder.staticgenerated.annotations.StaticMetricsProxy;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by svenruppert on 18.12.15.
 */
@StaticMetricsProxy
public abstract class MetricHttpServlet extends HttpServlet {

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doGet(req, resp);
  }

  @Override
  protected long getLastModified(final HttpServletRequest req) {
    return super.getLastModified(req);
  }

  @Override
  protected void doHead(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doHead(req, resp);
  }

  @Override
  protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }

  @Override
  protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doPut(req, resp);
  }

  @Override
  protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doDelete(req, resp);
  }

  @Override
  protected void doOptions(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doOptions(req, resp);
  }

  @Override
  protected void doTrace(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.doTrace(req, resp);
  }

  @Override
  protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    super.service(req, resp);
  }

  @Override
  public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
    super.service(req, res);
  }

  @Override
  public void destroy() {
    super.destroy();
  }

  @Override
  public String getInitParameter(final String name) {
    return super.getInitParameter(name);
  }

  @Override
  public Enumeration<String> getInitParameterNames() {
    return super.getInitParameterNames();
  }

  @Override
  public ServletConfig getServletConfig() {
    return super.getServletConfig();
  }

  @Override
  public ServletContext getServletContext() {
    return super.getServletContext();
  }

  @Override
  public String getServletInfo() {
    return super.getServletInfo();
  }

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);
  }

  @Override
  public void init() throws ServletException {
    super.init();
  }

  @Override
  public void log(final String msg) {
    super.log(msg);
  }

  @Override
  public void log(final String message, final Throwable t) {
    super.log(message, t);
  }

  @Override
  public String getServletName() {
    return super.getServletName();
  }
}
