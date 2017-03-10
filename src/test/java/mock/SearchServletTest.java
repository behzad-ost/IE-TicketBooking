package mock;

import controller.SearchServlet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;


public class SearchServletTest {
    SearchServlet search;

    @Before
    public void setSearch() throws Exception {
        search = new SearchServlet();
    }

    @Test
    public void testSearch_NoResult() throws ServletException, IOException {
        HttpServletResponseMock resp = new HttpServletResponseMock();
        HttpServletRequestMock req = new HttpServletRequestMock();

        req.addParameter("source-code", "THR");
        req.addParameter("dest-code", "MHD");
        req.addParameter("date", "01Feb");
        req.addParameter("adults", "1");
        req.addParameter("children", "1");
        req.addParameter("infants", "1");

        search.doPost(req, resp);

        assertEquals(resp.getStatus(),404);
    }
    @Test
    public void testSearch_1_Result() throws ServletException, IOException {
        HttpServletResponseMock resp = new HttpServletResponseMock();
        HttpServletRequestMock req = new HttpServletRequestMock();

        req.addParameter("source-code", "MHD");
        req.addParameter("dest-code", "THR");
        req.addParameter("date", "06Feb");
        req.addParameter("adults", "1");
        req.addParameter("children", "1");
        req.addParameter("infants", "1");

        search.doPost(req, resp);

        assertEquals(req.getAttribute("flights"),1);

    }
    @Test
    public void testSearch_n_Result() throws ServletException, IOException {
        HttpServletResponseMock resp = new HttpServletResponseMock();
        HttpServletRequestMock req = new HttpServletRequestMock();

        req.addParameter("source-code", "THR");
        req.addParameter("dest-code", "MHD");
        req.addParameter("date", "05Feb");
        req.addParameter("adults", "1");
        req.addParameter("children", "1");
        req.addParameter("infants", "1");

        search.doPost(req, resp);

        assertNotEquals(req.getAttribute("available"),0);

    }

    @Test
    public void testSearch_No_Space() throws ServletException, IOException {
        HttpServletResponseMock resp = new HttpServletResponseMock();
        HttpServletRequestMock req = new HttpServletRequestMock();

        req.addParameter("source-code", "THR");
        req.addParameter("dest-code", "IFN");
        req.addParameter("date", "07Feb");
        req.addParameter("adults", "10");
        req.addParameter("children", "5");
        req.addParameter("infants", "5");

        search.doPost(req, resp);

        assertEquals(req.getAttribute("available"),0);

    }
}
