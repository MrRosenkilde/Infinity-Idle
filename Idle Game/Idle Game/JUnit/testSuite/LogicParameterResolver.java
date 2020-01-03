package testSuite;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import domain.State;
import logic.Logic;

public class LogicParameterResolver implements ParameterResolver{

	@Override
	public Object resolveParameter(ParameterContext arg0, ExtensionContext arg1) throws ParameterResolutionException {
		return new Logic(new State());
	}

	@Override
	public boolean supportsParameter(ParameterContext arg0, ExtensionContext arg1) throws ParameterResolutionException {
		return arg0.getParameter().getType() == Logic.class;
	}

}
