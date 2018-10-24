package net.daw.control;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.JsonHelper;
import net.daw.service.FacturaService;
import net.daw.service.ProductoService;
import net.daw.service.TipoproductoService;
import net.daw.service.TipousuarioService;
import net.daw.service.UsuarioService;

/**
 * Servlet implementation class json
 */
public class json extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public json() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        String strJson = "";
        String strOb = request.getParameter("ob");
        String strOp = request.getParameter("op");
        JsonHelper json = new JsonHelper();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
        }

        if (strOp != null && strOb != null) {
            if (!strOp.equalsIgnoreCase("") && !strOb.equalsIgnoreCase("")) {
                if (strOb.equalsIgnoreCase("tipousuario")) {
                    if (strOp.equalsIgnoreCase("get")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();

//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("getpage")) {
                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getpage();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("remove")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.remove();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getcount")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getcount();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("create")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.create();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("update")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.update();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (strOb.equalsIgnoreCase("usuario")) {
                    if (strOp.equalsIgnoreCase("get")) {

                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();

//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("getpage")) {
                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getpage();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("remove")) {

                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.remove();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getcount")) {

                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.getcount();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("create")) {

                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.create();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("update")) {

                        UsuarioService oService = new UsuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.update();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                if (strOb.equalsIgnoreCase("factura")) {
                    if (strOp.equalsIgnoreCase("get")) {

                        FacturaService oService = new FacturaService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();

//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("getpage")) {
                        FacturaService oService = new FacturaService(request);
                        try {
                            ReplyBean oReplyBean = oService.getpage();
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("remove")) {

                        FacturaService oService = new FacturaService(request);
                        try {
                            ReplyBean oReplyBean = oService.remove();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("getcount")) {

                        FacturaService oService = new FacturaService(request);
                        try {
                            ReplyBean oReplyBean = oService.getcount();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOp.equalsIgnoreCase("create")) {

                        FacturaService oService = new FacturaService(request);
                        try {
                            ReplyBean oReplyBean = oService.create();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (strOp.equalsIgnoreCase("update")) {

                        FacturaService oService = new FacturaService(request);
                        try {
                            ReplyBean oReplyBean = oService.update();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (strOb.equalsIgnoreCase("producto")) {
                        if (strOp.equalsIgnoreCase("get")) {

                            ProductoService oService = new ProductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.get();

//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("getpage")) {
                            ProductoService oService = new ProductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.getpage();
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("remove")) {

                            ProductoService oService = new ProductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.remove();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (strOp.equalsIgnoreCase("getcount")) {

                            ProductoService oService = new ProductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.getcount();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (strOp.equalsIgnoreCase("create")) {

                            ProductoService oService = new ProductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.create();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("update")) {

                            ProductoService oService = new ProductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.update();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    if (strOb.equalsIgnoreCase("tipousuario")) {
                        if (strOp.equalsIgnoreCase("get")) {

                            TipousuarioService oService = new TipousuarioService(request);
                            try {
                                ReplyBean oReplyBean = oService.get();

//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("getpage")) {
                            TipousuarioService oService = new TipousuarioService(request);
                            try {
                                ReplyBean oReplyBean = oService.getpage();
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("remove")) {

                            TipousuarioService oService = new TipousuarioService(request);
                            try {
                                ReplyBean oReplyBean = oService.remove();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (strOp.equalsIgnoreCase("getcount")) {

                            TipousuarioService oService = new TipousuarioService(request);
                            try {
                                ReplyBean oReplyBean = oService.getcount();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (strOp.equalsIgnoreCase("create")) {

                            TipousuarioService oService = new TipousuarioService(request);
                            try {
                                ReplyBean oReplyBean = oService.create();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("update")) {

                            TipousuarioService oService = new TipousuarioService(request);
                            try {
                                ReplyBean oReplyBean = oService.update();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    if (strOb.equalsIgnoreCase("tipoproducto")) {
                        if (strOp.equalsIgnoreCase("get")) {

                            TipoproductoService oService = new TipoproductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.get();

//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("getpage")) {
                            TipoproductoService oService = new TipoproductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.getpage();
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("remove")) {

                            TipoproductoService oService = new TipoproductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.remove();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (strOp.equalsIgnoreCase("getcount")) {

                            TipoproductoService oService = new TipoproductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.getcount();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";
                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (strOp.equalsIgnoreCase("create")) {

                            TipoproductoService oService = new TipoproductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.create();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if (strOp.equalsIgnoreCase("update")) {

                            TipoproductoService oService = new TipoproductoService(request);
                            try {
                                ReplyBean oReplyBean = oService.update();
//							strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":" + oReplyBean.getJson()
//									+ "}";

                                strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    if (strOb.equalsIgnoreCase("usuario")) {
                        if (strOp.equalsIgnoreCase("connect")) {

                            try {
                                ConnectionInterface oConnectionPool = ConnectionFactory
                                        .getConnection(ConnectionConstants.connectionPool);
                                Connection oConnection = oConnectionPool.newConnection();
                                // servir la petición utilizando oConnection
                                oConnectionPool.disposeConnection();

                                response.setStatus(200);
//							strJson = "{\"status\":200,\"msg\":\"Connection OK\"}";
                                strJson = json.strJson(200, "Connection OK");

                            } catch (Exception ex) {
                                response.setStatus(500);
//							strJson = "{\"status\":500,\"msg\":\"Bad Connection: "
//									+ EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())) + "\"}";
                                strJson = json.strJson(500, "Bad Connection: "
                                        + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
                            }

                        }

                        // http://localhost:8081/authentication/json?op=login&user=nombre&pass=password
                        // http://localhost:8081/authentication/json?op=check
                        // http://localhost:8081/authentication/json?op=logout
                        response.setContentType("application/json;charset=UTF-8");
                        HttpSession oSession = request.getSession();

                        if (strOp.equalsIgnoreCase("login")) {
                            String strUser = request.getParameter("user");
                            String strPass = request.getParameter("pass");
                            if (strUser.equals("rafa") && strPass.equals("thebest")) {
                                oSession.setAttribute("daw", strUser);
                                response.setStatus(200);
//							strJson = "{\"status\":200,\"msg\":\"" + strUser + "\"}";
                                strJson = json.strJson(200, strUser);
                            } else {
                                response.setStatus(401);
//							strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                                strJson = json.strJson(401, "Authentication error");
                            }
                        }
                        if (strOp.equalsIgnoreCase("logout")) {
                            oSession.invalidate();
                            response.setStatus(200);
//						strJson = "{\"status\":200,\"msg\":\"Session is closed\"}";
                            strJson = json.strJson(200, "Session is closed");
                        }
                        if (strOp.equalsIgnoreCase("check")) {
                            String strUserName = (String) oSession.getAttribute("daw");
                            if (strUserName != null) {
                                response.setStatus(200);
//							strJson = "{\"status\":200,\"msg\":\"" + strUserName + "\"}";
                                strJson = json.strJson(200, "Session is closed");
                            } else {
                                response.setStatus(401);
//							strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                                strJson = json.strJson(401, "Authentication error");
                            }
                        }
                        if (strOp.equalsIgnoreCase("getsecret")) {
                            String strUserName = (String) oSession.getAttribute("daw");
                            if (strUserName != null) {
                                response.setStatus(200);
//							strJson = "{\"status\":200,\"msg\":\"985739847598\"}";
                                strJson = json.strJson(200, "985739847598");
                            } else {
                                response.setStatus(401);
//							strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                                strJson = json.strJson(401, "Authentication error");
                            }
                        }
                    }
                }

            } else {
                response.setStatus(500);
//				strJson = "{\"status\":500,\"msg\":\"operation or object empty\"}";
                strJson = json.strJson(500, "operation or object empty");
            }
        } else {
            response.setStatus(500);
//			strJson = "{\"status\":500,\"msg\":\"operation or object can't be null\"}";
            strJson = json.strJson(500, "operation or object can't be null");
        }

        response.getWriter().append(strJson).close();
    }
}
