package iNTuition.ternary;

import org.python.core.PyInstance;
import org.python.util.PythonInterpreter;

public class Interpreter {
	PythonInterpreter interpreter = null;

	public Interpreter() {
		PythonInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);

		this.interpreter = new PythonInterpreter();
	}

	void execfile(final String fileName) {
		this.interpreter.execfile(fileName);
	}

	PyInstance createClass(final String className, final String opt1,final String key) {
		return (PyInstance) this.interpreter.eval(className + "(" + opt1+","+key + ")");
	}
}
