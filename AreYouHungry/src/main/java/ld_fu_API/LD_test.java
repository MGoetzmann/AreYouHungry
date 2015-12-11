package ld_fu_API;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.kit.aifb.datafu.Origin;
import edu.kit.aifb.datafu.Program;
import edu.kit.aifb.datafu.engine.EvaluateProgram;
import edu.kit.aifb.datafu.io.origins.FileOrigin;
import edu.kit.aifb.datafu.parser.ProgramConsumer;
import edu.kit.aifb.datafu.parser.notation3.Notation3Parser;
import edu.kit.aifb.datafu.parser.notation3.ParseException;
import edu.kit.aifb.datafu.planning.EvaluateProgramConfig;
import edu.kit.aifb.datafu.planning.EvaluateProgramGenerator;

/**
 * Servlet implementation class LD_test
 */
public class LD_test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LD_test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String qname = request.getParameter("qname");
		System.out.println(qname);
		File file = new  File("C:/Users/Martin/Documents/linked-data-fu-0.9.8/examples/euclidean-distance.n3");
		
		InputStream pis = new FileInputStream(file);
		Origin pbase = new FileOrigin(file,null,null);

		Notation3Parser n3p = new Notation3Parser(pis);
		ProgramConsumer pc = new ProgramConsumer(pbase);
		try {
			n3p.parse(pc, pbase);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("geht ned");
		}
		
		pis.close();
		
		Program program = new Program(pbase);
		//Program program = new Program(pc);
		
		EvaluateProgramConfig config = new EvaluateProgramConfig();
		EvaluateProgramGenerator epg = new EvaluateProgramGenerator(program, config);
		System.out.println("prestart");
		EvaluateProgram ep = epg.getEvaluateProgram();
		System.out.println("start");
		ep.start();
		try {
			ep.awaitIdleAndFinish();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ep.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
