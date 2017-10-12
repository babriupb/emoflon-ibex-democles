package org.emoflon.ibex.tgg.runtime.engine.csp.nativeOps.operations;

import java.util.Arrays;
import java.util.List;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.runtime.engine.csp.nativeOps.TGGAttributeConstraintModule;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.runtime.InternalDataFrameProvider;
import org.gervarro.democles.runtime.RemappedDataFrame;
import org.gervarro.democles.specification.ConstraintType;

public class AttributeConstraintNativeOperation extends TGGAttributeNativeOperation {

	private static final String EQ_STRING = "eq_string";


	@Override
	public InternalDataFrameProvider getDataFrame(RemappedDataFrame frame, Adornment adornment) {
		if(adornment.get(0) == Adornment.BOUND && adornment.get(1) == Adornment.BOUND) {
			final Object src = frame.getValue(0);
			final Object trg = frame.getValue(1);
			if (trg.equals(src)) {
				return frame;
			}
		}
		
		if(adornment.get(0) == Adornment.BOUND && adornment.get(1) == Adornment.FREE) {
			final Object src = frame.getValue(0);
			final Object trg = src;
			frame = createDataFrame(frame);
			frame.setValue(1, trg);
			return frame;
		}
		
		if(adornment.get(0) == Adornment.FREE && adornment.get(1) == Adornment.BOUND) {
			final Object trg = frame.getValue(1);
			final Object src = trg;
			frame = createDataFrame(frame);
			frame.setValue(0, src);
			return frame;
		}
		
		if(adornment.get(0) == Adornment.FREE && adornment.get(1) == Adornment.FREE) {
			Object value = RuntimeTGGAttributeConstraint.generateValue(String.class.getName());
			final Object src = value;
			final Object trg = value;
			frame = createDataFrame(frame);
			frame.setValue(0, src);
			frame.setValue(1, trg);
			return frame;
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		return EQ_STRING;
	}

	@Override
	public ConstraintType getConstraintType() {
		return TGGAttributeConstraintModule.INSTANCE.getConstraintType(EQ_STRING);
	}


	@Override
	public List<Adornment> getAllowedAdornments() {
		return Arrays.asList(
				Adornment.create(Adornment.BOUND, Adornment.BOUND),
				Adornment.create(Adornment.BOUND, Adornment.FREE),
				Adornment.create(Adornment.FREE, Adornment.BOUND),
				Adornment.create(Adornment.FREE, Adornment.FREE));
	}
}